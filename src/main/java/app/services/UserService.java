package app.services;

import app.exceptions.UserAlreadyExistException;
import app.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
    UserServiceModel registerNewUserAccount(UserServiceModel map) throws UserAlreadyExistException;
}