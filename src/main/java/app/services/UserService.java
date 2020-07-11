package app.services;

import app.exceptions.InvalidEmailException;
import app.exceptions.UserAlreadyExistException;
import app.models.service.UserServiceModel;
import app.models.view.UserDetailsViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
    void registerNewUserAccount(UserServiceModel map) throws UserAlreadyExistException;

    UserServiceModel findByEmail(String email) throws InvalidEmailException;

    UserServiceModel findByName(String username);

    UserDetailsViewModel getUserDetailsByUsername(String username);

    void changePassword(UserServiceModel user, String newPassword);


    void changeEmail(UserServiceModel user, String newEmail);
}
