package app.services.impl;

import app.error.InvalidEmailException;
import app.error.UserAlreadyExistException;
import app.models.entity.Role;
import app.models.entity.User;
import app.models.service.RoleServiceModel;
import app.models.service.UserServiceModel;
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

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
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
    public void registerNewUserAccount(UserServiceModel userServiceModel) throws UserAlreadyExistException {

        Set<RoleServiceModel> authorities = new HashSet<>();
        long count = this.userRepository.count();
        if (count == 0) {
            authorities.add(this.roleService.findByAuthority("ROLE_ROOT_ADMIN"));
        } else if (count == 1) {
            authorities.add(this.roleService.findByAuthority("ROLE_ADMIN"));
        } else if (count == 2) {
            authorities.add(this.roleService.findByAuthority("ROLE_TEACHER"));
        } else {
            authorities.add(this.roleService.findByAuthority("ROLE_STUDENT"));
        }
        userServiceModel.setAuthorities(authorities);
        userServiceModel.setRegistrationDate(LocalDateTime.now().withNano(0));
        System.out.println();
        registerNewUser(userServiceModel);
    }

    @Override
    public void createNewAdminAccount(UserServiceModel adminUser) throws UserAlreadyExistException {
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
        User user = this.userRepository.findFirstByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with given username was not found !"));

        return this.modelMapper.map(user, UserServiceModel.class);

    }

    @Override
    public UserDetailsViewModel getUserDetailsByUsername(String username) {
        User user = this.userRepository.findFirstByUsername(username).orElse(null);
        UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(user, UserDetailsViewModel.class);
        userDetailsViewModel.setFullName(String.format("%s %s", user.getFirstName(), user.getLastName()));
        userDetailsViewModel.setRegistrationDate(user.getRegistrationDate());
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
        user.setProfilePicture(this.cloudinaryService.uploadImage(profilePicture));
        this.userRepository.saveAndFlush(this.modelMapper.map(user, User.class));
    }

    @Override
    public List<UserDetailsViewModel> findAllAdmins() {

        return this.userRepository.findAllAdmins().stream().map(user -> {
            UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(user, UserDetailsViewModel.class);
            userDetailsViewModel.setFullName(String.format("%s %s", user.getFirstName(), user.getLastName()));
            userDetailsViewModel.setRegistrationDate(user.getRegistrationDate());
            return userDetailsViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserDetailsViewModel> findAllStudents() {

        return this.userRepository.findAllStudents().stream().map(user -> {
            UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(user, UserDetailsViewModel.class);
            userDetailsViewModel.setFullName(String.format("%s %s", user.getFirstName(), user.getLastName()));
            userDetailsViewModel.setRegistrationDate(user.getRegistrationDate());
            return userDetailsViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserDetailsViewModel> findAllTeachers() {
        return this.userRepository.findAllTeachers().stream().map(user -> {
            UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(user, UserDetailsViewModel.class);
            userDetailsViewModel.setFullName(String.format("%s %s", user.getFirstName(), user.getLastName()));
            userDetailsViewModel.setRegistrationDate(user.getRegistrationDate());
            return userDetailsViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public void blockUser(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setStatus(false);
        this.userRepository.saveAndFlush(user);

    }

    @Override
    public void activateUser(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setStatus(true);
        this.userRepository.saveAndFlush(user);
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


    private boolean usernameExist(String username) {
        return this.userRepository.findFirstByUsername(username).orElse(null) != null;
    }

    private boolean emailExist(String email) {
        return this.userRepository.findFirstByEmail(email).orElse(null) != null;
    }

    private void registerNewUser(UserServiceModel userServiceModel) {
        if (emailExist(userServiceModel.getEmail())) {
            throw new UserAlreadyExistException(
                    "There is an account with that email address: "
                            + userServiceModel.getEmail());
        }
        if (usernameExist(userServiceModel.getUsername())) {
            throw new UserAlreadyExistException(
                    "There is an account with that username address: "
                            + userServiceModel.getUsername());
        }
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        user.setProfilePicture(DEFAULT_PROFILE_PICTURE);
        user.setStatus(true);

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public List<UserDetailsViewModel> findAllStudentsWithRequests() {

        return this.userRepository.findAllStudentsWithRequests().stream().map(user -> {
            UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(user, UserDetailsViewModel.class);
            userDetailsViewModel.setFullName(String.format("%s %s", user.getFirstName(), user.getLastName()));
            userDetailsViewModel.setRegistrationDate(user.getRegistrationDate());
            return userDetailsViewModel;
        }).collect(Collectors.toList());

    }
}
