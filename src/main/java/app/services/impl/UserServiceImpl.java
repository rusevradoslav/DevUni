package app.services.impl;

import app.error.InvalidEmailException;
import app.error.UserAlreadyExistException;
import app.models.entity.AboutMe;
import app.models.entity.Role;
import app.models.entity.User;
import app.models.service.AboutMeServiceModel;
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
import org.springframework.security.core.userdetails.UserDetails;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDetails user = this.userRepository.findFirstByEmail(email).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid user");
        }
        return user;
    }

    @Override
    public void registerNewUserAccount(UserServiceModel userServiceModel) throws UserAlreadyExistException, RoleNotFoundException {

        Set<RoleServiceModel> authorities = new HashSet<>();
        long count = this.userRepository.count();
        if (count == 0) {
            authorities.add(this.roleService.findByAuthority("ROLE_ROOT_ADMIN"));
        } else {
            authorities.add(this.roleService.findByAuthority("ROLE_STUDENT"));
        }
        userServiceModel.setAuthorities(authorities);
        userServiceModel.setRegistrationDate(LocalDateTime.now().withNano(0));
        registerNewUser(userServiceModel);
    }

    @Override
    public void createNewAdminAccount(UserServiceModel adminUser) throws UserAlreadyExistException, RoleNotFoundException {
        Set<RoleServiceModel> authorities = new HashSet<>();
        authorities.add(this.roleService.findByAuthority("ROLE_ADMIN"));
        adminUser.setAuthorities(authorities);
        adminUser.setRegistrationDate(LocalDateTime.now());
        registerNewUser(adminUser);
    }

    @Override
    public UserServiceModel findById(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with given id was not found !"));
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
        System.out.println();
        User user = this.userRepository.findFirstByUsername(username).orElse(null);
        if (user != null) {
            AboutMe aboutMe = user.getAboutMe();
            if (aboutMe != null) {
                AboutMeServiceModel aboutMeServiceModel = this.modelMapper.map(aboutMe, AboutMeServiceModel.class);
                UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
                userServiceModel.setAboutMeServiceModel(aboutMeServiceModel);
                return userServiceModel;
            } else {
                return this.modelMapper.map(user, UserServiceModel.class);

            }
        } else {
            return null;
        }

    }

    @Override
    public UserDetailsViewModel getUserDetailsByUsername(String username) {
        User user = this.userRepository.findFirstByUsername(username).orElse(null);
        UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(user, UserDetailsViewModel.class);
        userDetailsViewModel.setFullName(String.format("%s %s", user.getFirstName(), user.getLastName()));
        LocalDateTime reg = user.getRegistrationDate();
        String date = reg.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println();
        userDetailsViewModel.setRegistrationDate(date);
        return userDetailsViewModel;
    }

    @Override
    public void sentTeacherRequest(UserServiceModel userServiceModel) {
        this.userRepository.changeTeacherRequestToTrue(userServiceModel.getId());
    }

    @Override
    public void confirmTeacherRequest(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        Role role = this.roleService.findAuthorityByName("ROLE_TEACHER");
        this.userRepository.changeRole(role.getId(), user.getId());
        this.userRepository.changeTeacherRequestToFalse(user.getId());

    }

    @Override
    public void cancelTeacherRequest(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        this.userRepository.changeTeacherRequestToFalse(user.getId());
    }

    @Override
    public void setAboutMeToTheTeacher(AboutMe aboutMe, User user) {
        this.userRepository.updateTeacherAboutMe(aboutMe.getId(), user.getId());
    }

    @Override
    public void changePassword(UserServiceModel userServiceModel, String newPassword) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        String newEncodedPassword = bCryptPasswordEncoder.encode(newPassword);
        this.userRepository.changePassword(user.getId(), newEncodedPassword);

    }

    @Override
    public void changeEmail(UserServiceModel userServiceModel, String newEmail) {
        User user = this.modelMapper.map(userServiceModel, User.class);

        if (emailExist(newEmail)) {
            throw new UserAlreadyExistException(
                    "There is an account with that email address: "
                            + newEmail);
        }
        this.userRepository.changeEmail(user.getId(), newEmail);

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
            userDetailsViewModel.setFullName(String.format("%s %s", user.getFirstName(), user.getLastName()));
            LocalDateTime reg = user.getRegistrationDate();
            String date = reg.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            System.out.println();
            userDetailsViewModel.setRegistrationDate(date);
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
    public List<UserDetailsViewModel> findAllTeachers() {
        return this.userRepository.findAllTeachers().stream().map(user -> {
            UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(user, UserDetailsViewModel.class);
            userDetailsViewModel.setFullName(String.format("%s %s", user.getFirstName(), user.getLastName()));
            LocalDateTime reg = user.getRegistrationDate();
            String date = reg.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            System.out.println();
            userDetailsViewModel.setRegistrationDate(date);
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
    public List<UserDetailsViewModel> findAllStudentsWithRequests() {

        return this.userRepository.findAllStudentsWithRequests().stream().map(user -> {
            UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(user, UserDetailsViewModel.class);
            setFullNameAndRegistrationDate(user, userDetailsViewModel);
            return userDetailsViewModel;
        }).collect(Collectors.toList());

    }

    @Override
    public void blockUser(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        this.userRepository.blockUser(user.getId());

    }

    @Override
    public void activateUser(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        this.userRepository.activateUser(user.getId());
    }

    @Override
    public void changeRoleToStudent(UserServiceModel userServiceModel) {

        User user = this.modelMapper.map(userServiceModel, User.class);
        Role role = this.roleService.findAuthorityByName("ROLE_STUDENT");
        this.userRepository.changeRole(role.getId(), user.getId());

    }

    @Override
    public void changeRoleToTeacher(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        Role role = this.roleService.findAuthorityByName("ROLE_TEACHER");

        this.userRepository.changeRole(role.getId(), user.getId());
    }

    @Override
    public void changeRoleToAdmin(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        Role role = this.roleService.findAuthorityByName("ROLE_ADMIN");

        this.userRepository.changeRole(role.getId(), user.getId());
    }

    private void registerNewUser(UserServiceModel userServiceModel) {

        throwExceptionIfUserExist(userServiceModel.getUsername(), userServiceModel.getEmail());

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        user.setProfilePicture(DEFAULT_PROFILE_PICTURE);
        user.setStatus(true);

        this.userRepository.saveAndFlush(user);
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
             throw  new UserAlreadyExistException(
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
