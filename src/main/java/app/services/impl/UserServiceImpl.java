package app.services.impl;

import app.error.InvalidEmailException;
import app.error.UserAlreadyExistException;
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
import java.util.Set;

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
            authorities.add(this.roleService.findByAuthority("ROLE_ADMIN"));
        } else if (count == 1) {
            authorities.add(this.roleService.findByAuthority("ROLE_TEACHER"));
        } else {
            authorities.add(this.roleService.findByAuthority("ROLE_STUDENT"));
        }
        userServiceModel.setAuthorities(authorities);
        userServiceModel.setRegistrationDate(LocalDateTime.now());
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


        this.userRepository.saveAndFlush(user);
    }

    @Override
    public UserServiceModel findByEmail(String email) {
        System.out.println();
        User user = this.userRepository.findFirstByEmail(email).orElse(null);
        if (user == null) {
            throw new InvalidEmailException("User with that email address does't exist !");
        } else {
            return this.modelMapper.map(user, UserServiceModel.class);
        }
    }

    @Override
    public UserServiceModel findByName(String username) {
        System.out.println();
        User user = this.userRepository.findFirstByUsername(username).orElse(null);
        if (user == null) {
            return null;
        } else {
            return this.modelMapper.map(user, UserServiceModel.class);
        }
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
    public void changePassword(UserServiceModel userServiceModel, String newPassword) {
        System.out.println();
        User user = this.modelMapper.map(userServiceModel, User.class);
        String encodeNewPass = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(encodeNewPass);

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void changeEmail(UserServiceModel userServiceModel, String newEmail) {
        if (emailExist(newEmail)) {
            throw new UserAlreadyExistException(
                    "There is an account with that email address: "
                            + newEmail);
        }
        userServiceModel.setEmail(newEmail);
        this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
    }

    @Override
    public void addProfilePicture(UserServiceModel user, MultipartFile profilePicture) throws IOException {
        System.out.println();
        user.setProfilePicture(this.cloudinaryService.uploadImage(profilePicture));
        System.out.println();
        this.userRepository.saveAndFlush(this.modelMapper.map(user, User.class));
    }


    private boolean usernameExist(String username) {
        return this.userRepository.findFirstByUsername(username).orElse(null) != null;
    }

    private boolean emailExist(String email) {
        return this.userRepository.findFirstByEmail(email).orElse(null) != null;
    }
}
