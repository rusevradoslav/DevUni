package app.services;

import app.exceptions.UserAlreadyExistException;
import app.models.entity.User;
import app.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
    UserServiceModel registerNewUserAccount(UserServiceModel map) throws UserAlreadyExistException;

    User findByEmail(String email);
    User findByName(String email);
}
