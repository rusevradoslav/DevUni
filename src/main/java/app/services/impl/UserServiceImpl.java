package app.services.impl;

import app.exceptions.UserAlreadyExistException;
import app.models.entity.User;
import app.models.service.RoleServiceModel;
import app.models.service.UserServiceModel;
import app.repositories.UserRepository;
import app.services.RoleService;
import app.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public UserServiceModel registerNewUserAccount(UserServiceModel userServiceModel) throws UserAlreadyExistException {
        System.out.println();
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
                            + userServiceModel.getEmail());
        }
        User user = this.modelMapper.map(userServiceModel, User.class);
        this.userRepository.saveAndFlush(user);
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    private boolean usernameExist(String username) {
        return this.userRepository.findFirstByUsername(username).orElse(null) != null;
    }

    private boolean emailExist(String email) {
        return this.userRepository.findFirstByEmail(email).orElse(null) != null;
    }
}
