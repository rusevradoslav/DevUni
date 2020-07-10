package app.services.impl;

import app.exceptions.InvalidEmailException;
import app.exceptions.UserAlreadyExistException;
import app.models.entity.User;
import app.models.service.RoleServiceModel;
import app.models.service.UserServiceModel;
import app.models.view.UserDetailsViewModel;
import app.repositories.UserRepository;
import app.services.RoleService;
import app.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
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
            authorities.add(this.roleService.findByAuthority("ADMIN"));
        } else if (count == 1) {
            authorities.add(this.roleService.findByAuthority("TEACHER"));
        } else {
            authorities.add(this.roleService.findByAuthority("STUDENT"));
        }
        userServiceModel.setAuthorities(authorities);
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
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public UserServiceModel findByEmail(String email) {
        return this.modelMapper.map(this.userRepository.findFirstByEmail(email).orElse(null), UserServiceModel.class);
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
        return userDetailsViewModel;
    }

    @Override
    public void changePassword(String newPassword) {

    }

    @Override
    public void changeUserEmail(UserServiceModel user, String newEmail) throws InvalidEmailException {

        System.out.println();

        user.setEmail(newEmail);

        User user1 = this.modelMapper.map(user, User.class);

        try {
            this.userRepository.saveAndFlush(user1);
        } catch (ConstraintViolationException exception) {
            throw new InvalidEmailException("Invalid email");
        }


    }

    private boolean usernameExist(String username) {
        return this.userRepository.findFirstByUsername(username).orElse(null) != null;
    }

    private boolean emailExist(String email) {
        return this.userRepository.findFirstByEmail(email).orElse(null) != null;
    }
}
