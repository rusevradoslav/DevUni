package app.services;

import app.exceptions.InvalidEmailException;
import app.exceptions.UserAlreadyExistException;
import app.models.service.UserServiceModel;
import app.models.view.UserDetailsViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
    void registerNewUserAccount(UserServiceModel map) throws UserAlreadyExistException;

    UserServiceModel findByEmail(String email);

    UserServiceModel findByName(String username);

    UserDetailsViewModel getUserDetailsByUsername(String username);

    void changePassword(String newPassword);


    void changeUserEmail(UserServiceModel user, String newEmail) throws InvalidEmailException;
}
