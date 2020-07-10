package app.services;

import app.exceptions.UserAlreadyExistException;
import app.models.service.UserServiceModel;
import app.models.view.UserDetailsViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
    UserServiceModel registerNewUserAccount(UserServiceModel map) throws UserAlreadyExistException;

    UserServiceModel findByEmail(String email);
    UserServiceModel findByName(String email);

    UserDetailsViewModel getUserDetailsByUsername(String username);
}
