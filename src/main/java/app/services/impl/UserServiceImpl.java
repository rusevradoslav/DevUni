package app.services.impl;

import app.error.InvalidEmailException;
import app.error.UserAlreadyExistException;
import app.error.UserNotFoundException;
import app.models.entity.AboutMe;
import app.models.entity.Role;
import app.models.entity.User;
import app.models.service.AboutMeServiceModel;
import app.models.service.CourseServiceModel;
import app.models.service.RoleServiceModel;
import app.models.service.UserServiceModel;
import app.models.view.AboutMeViewModel;
import app.models.view.UserDetailsViewModel;
import app.repositories.UserRepository;
import app.services.CloudinaryService;
import app.services.RoleService;
import app.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.RoleNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static app.constants.GlobalConstants.DEFAULT_PROFILE_PICTURE;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserServiceModel registerNewUserAccount(UserServiceModel userServiceModel) throws UserAlreadyExistException, RoleNotFoundException {
        Set<RoleServiceModel> authorities = new HashSet<>();
        long count = this.userRepository.count();
        if (count == 0) {
            authorities.add(this.roleService.findByAuthority("ROLE_ROOT_ADMIN"));
        } else {
            authorities.add(this.roleService.findByAuthority("ROLE_STUDENT"));
        }
        userServiceModel.setAuthorities(authorities);
        userServiceModel.setRegistrationDate(LocalDateTime.now().withNano(0));

        return registerNewUser(userServiceModel);
    }

    @Override
    public UserServiceModel createNewAdminAccount(UserServiceModel adminUser) throws UserAlreadyExistException, RoleNotFoundException {

        Set<RoleServiceModel> authorities = new HashSet<>();
        authorities.add(this.roleService.findByAuthority("ROLE_ADMIN"));
        adminUser.setAuthorities(authorities);
        adminUser.setRegistrationDate(LocalDateTime.now());
        return registerNewUser(adminUser);
    }

    @Override
    public UserServiceModel findById(String id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with given id was not found !"));
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel findByEmail(String email) {

        User user = this.userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new InvalidEmailException("User with that email address does't exist !"));

        return this.modelMapper.map(user, UserServiceModel.class);

    }

    @Override
    public UserServiceModel findByName(String username) {
        User user = this.userRepository.findFirstByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with given username does not exist! "));


        AboutMe aboutMe = user.getAboutMe();
        if (aboutMe != null) {
            AboutMeServiceModel aboutMeServiceModel = this.modelMapper.map(aboutMe, AboutMeServiceModel.class);
            UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
            userServiceModel.setAboutMeServiceModel(aboutMeServiceModel);
            return userServiceModel;
        } else {
            return this.modelMapper.map(user, UserServiceModel.class);

        }


    }

    @Override
    public UserDetailsViewModel getUserDetailsByUsername(String username) {
        User user = this.userRepository.findFirstByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with given username does not exist! "));
        UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(user, UserDetailsViewModel.class);
        setFullNameAndRegistrationDate(user, userDetailsViewModel);
        return userDetailsViewModel;
    }

    @Override
    public UserServiceModel sentTeacherRequest(UserServiceModel userServiceModel) {
        this.userRepository.changeTeacherRequestToTrue(userServiceModel.getId());
        User user = userRepository.findById(userServiceModel.getId()).orElseThrow(() -> new UserNotFoundException("User with given id was not found !"));
        return this.modelMapper.map(user, UserServiceModel.class);

    }

    @Override
    public UserServiceModel confirmTeacherRequest(UserServiceModel userServiceModel) throws RoleNotFoundException {
        Role role = this.roleService.findAuthorityByName("ROLE_TEACHER");
        this.userRepository.changeRole(role.getId(), userServiceModel.getId());
        this.userRepository.changeTeacherRequestToFalse(userServiceModel.getId());
        User user = userRepository.findById(userServiceModel.getId()).orElseThrow(() -> new UserNotFoundException("User with given id was not found !"));
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel cancelTeacherRequest(UserServiceModel userServiceModel) {
        this.userRepository.changeTeacherRequestToFalse(userServiceModel.getId());
        User user = userRepository.findById(userServiceModel.getId()).orElseThrow(() -> new UserNotFoundException("User with given id was not found !"));
        return this.modelMapper.map(user, UserServiceModel.class);

    }

    @Override
    public void setAboutMeToTheTeacher(AboutMe aboutMe, User user) {
        this.userRepository.updateTeacherAboutMe(aboutMe.getId(), user.getId());
    }

    @Override
    public UserServiceModel changePassword(UserServiceModel userServiceModel, String newPassword) {
        String newEncodedPassword = bCryptPasswordEncoder.encode(newPassword);
        this.userRepository.changePassword(userServiceModel.getId(), newEncodedPassword);
        User user = userRepository.findById(userServiceModel.getId()).orElseThrow(() -> new UserNotFoundException("User with given id was not found !"));
        return this.modelMapper.map(user, UserServiceModel.class);

    }

    @Override
    public UserServiceModel changeEmail(UserServiceModel userServiceModel, String newEmail) {
        if (emailExist(newEmail)) {
            throw new UserAlreadyExistException(
                    "There is an account with that email address: "
                            + newEmail);
        }
        this.userRepository.changeEmail(userServiceModel.getId(), newEmail);

        User user = userRepository.findById(userServiceModel.getId()).orElseThrow(() -> new UserNotFoundException("User with given id was not found !"));
        return this.modelMapper.map(user, UserServiceModel.class);

    }

    @Override
    public void addProfilePicture(UserServiceModel user, MultipartFile profilePicture) throws IOException {
        String imageUrl = this.cloudinaryService.uploadImage(profilePicture);
        this.userRepository.updatePhoto(user.getId(), imageUrl);
    }

    @Override
    public List<UserDetailsViewModel> findAllAdmins() {

        return this.userRepository.findAllAdmins().stream().map(user -> {
            UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(user, UserDetailsViewModel.class);
            setFullNameAndRegistrationDate(user, userDetailsViewModel);
            return userDetailsViewModel;
        }).collect(Collectors.toList());


    }

    @Override
    public List<UserDetailsViewModel> findAllStudents() {

        return this.userRepository.findAllStudents().stream().map(user -> {
            UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(user, UserDetailsViewModel.class);
            setFullNameAndRegistrationDate(user, userDetailsViewModel);
            return userDetailsViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserDetailsViewModel> findAllStudentsWithRequests() {

        return this.userRepository.findAllStudentsWithRequests().stream().map(user -> {
            UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(user, UserDetailsViewModel.class);
            setFullNameAndRegistrationDate(user, userDetailsViewModel);
            return userDetailsViewModel;
        }).collect(Collectors.toList());

    }

    @Override
    public List<UserDetailsViewModel> findAllTeachers() {
        return this.userRepository.findAllTeachers().stream().map(user -> {
            UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(user, UserDetailsViewModel.class);
            setFullNameAndRegistrationDate(user, userDetailsViewModel);
            AboutMeViewModel aboutMeViewModel;
            if (user.getAboutMe() == null) {
                aboutMeViewModel = this.modelMapper.map(new AboutMe("", ""), AboutMeViewModel.class);
            } else {
                aboutMeViewModel = this.modelMapper.map(user.getAboutMe(), AboutMeViewModel.class);
            }
            userDetailsViewModel.setAboutMeViewModel(aboutMeViewModel);


            return userDetailsViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public UserDetailsViewModel findTeacher(String teacherId) {
        User user = this.userRepository.findById(teacherId).orElseThrow(() -> new UserNotFoundException("User with given id does not exist"));

        UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(user, UserDetailsViewModel.class);
        setFullNameAndRegistrationDate(user, userDetailsViewModel);

        AboutMeViewModel aboutMeViewModel;
        if (user.getAboutMe() == null) {
            aboutMeViewModel = this.modelMapper.map(new AboutMe("", ""), AboutMeViewModel.class);
        } else {
            aboutMeViewModel = this.modelMapper.map(user.getAboutMe(), AboutMeViewModel.class);
        }
        userDetailsViewModel.setAboutMeViewModel(aboutMeViewModel);


        return userDetailsViewModel;


    }

    @Override
    public UserServiceModel blockUser(UserServiceModel userServiceModel) {
        this.userRepository.blockUser(userServiceModel.getId());
        User user = userRepository.findById(userServiceModel.getId()).orElseThrow(() -> new UserNotFoundException("User with given id was not found !"));
        return this.modelMapper.map(user, UserServiceModel.class);

    }

    @Override
    public UserServiceModel activateUser(UserServiceModel userServiceModel) {

        this.userRepository.activateUser(userServiceModel.getId());
        User user = userRepository.findById(userServiceModel.getId()).orElseThrow(() -> new UserNotFoundException("User with given id was not found !"));
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel changeRoleToStudent(UserServiceModel userServiceModel) throws RoleNotFoundException {
        Role role = this.roleService.findAuthorityByName("ROLE_STUDENT");
        this.userRepository.changeRole(role.getId(), userServiceModel.getId());
        User user = userRepository.findById(userServiceModel.getId()).orElseThrow(() -> new UserNotFoundException("User with given id was not found !"));
        return this.modelMapper.map(user, UserServiceModel.class);

    }

    @Override
    public UserServiceModel changeRoleToTeacher(UserServiceModel userServiceModel) throws RoleNotFoundException {
        Role role = this.roleService.findAuthorityByName("ROLE_TEACHER");
        this.userRepository.changeRole(role.getId(), userServiceModel.getId());
        User user = userRepository.findById(userServiceModel.getId()).orElseThrow(() -> new UserNotFoundException("User with given id was not found !"));
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel changeRoleToAdmin(UserServiceModel userServiceModel) throws RoleNotFoundException {
        Role role = this.roleService.findAuthorityByName("ROLE_ADMIN");
        this.userRepository.changeRole(role.getId(), userServiceModel.getId());
        User user = userRepository.findById(userServiceModel.getId()).orElseThrow(() -> new UserNotFoundException("User with given id was not found !"));
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public List<CourseServiceModel> findAllCoursesByUserId(String id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with given id was not found !"));

        return user.getEnrolledCourses()
                .stream()
                .map(course -> this.modelMapper.map(course, CourseServiceModel.class))
                .collect(Collectors.toList());
    }

    private UserServiceModel registerNewUser(UserServiceModel userServiceModel) {
        throwExceptionIfUserExist(userServiceModel.getUsername(), userServiceModel.getEmail());

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        user.setProfilePicture(DEFAULT_PROFILE_PICTURE);
        user.setStatus(true);


        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    private void throwExceptionIfUserExist(String username, String email) {
        User userWithUsername = this.userRepository.findFirstByUsername(username).orElse(null);
        if (userWithUsername != null) {
            throw new UserAlreadyExistException(
                    "There is an account with that username : "
                            + username);
        }
        User userWithEmail = this.userRepository.findFirstByEmail(email).orElse(null);

        if (userWithEmail != null) {
            throw new UserAlreadyExistException(
                    "There is an account with that email address: "
                            + email);
        }
    }

    private boolean emailExist(String email) {
        return this.userRepository.findFirstByEmail(email).orElse(null) != null;
    }

    private void setFullNameAndRegistrationDate(User user, UserDetailsViewModel userDetailsViewModel) {

        userDetailsViewModel.setFullName(String.format("%s %s", user.getFirstName(), user.getLastName()));
        LocalDateTime reg = user.getRegistrationDate();
        String date = reg.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        userDetailsViewModel.setRegistrationDate(date);
    }
}
