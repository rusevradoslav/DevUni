package app.services;

import app.error.InvalidEmailException;
import app.error.UserAlreadyExistException;
import app.models.entity.AboutMe;
import app.models.entity.User;
import app.models.service.UserServiceModel;
import app.models.view.UserDetailsViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.RoleNotFoundException;
import java.io.IOException;
import java.util.List;

public interface UserService extends UserDetailsService{
    void registerNewUserAccount(UserServiceModel map) throws UserAlreadyExistException, RoleNotFoundException;

    UserServiceModel findByEmail(String email) throws InvalidEmailException;

    UserServiceModel findByName(String username);

    UserDetailsViewModel getUserDetailsByUsername(String username);

    void changePassword(UserServiceModel user, String newPassword);

    void changeEmail(UserServiceModel user, String newEmail);

    void addProfilePicture(UserServiceModel user, MultipartFile profilePicture) throws IOException;

    List<UserDetailsViewModel> findAllAdmins();

    List<UserDetailsViewModel> findAllStudents();

    List<UserDetailsViewModel> findAllTeachers();

    List<UserDetailsViewModel> findAllStudentsWithRequests();

    UserServiceModel findById(String id);

    void blockUser(UserServiceModel userServiceModel);

    void activateUser(UserServiceModel userServiceModel);

    void changeRoleToStudent(UserServiceModel userServiceModel) throws RoleNotFoundException;

    void changeRoleToTeacher(UserServiceModel userServiceModel) throws RoleNotFoundException;

    void changeRoleToAdmin(UserServiceModel user) throws RoleNotFoundException;

    void createNewAdminAccount(UserServiceModel map) throws UserAlreadyExistException, RoleNotFoundException;

    void sentTeacherRequest(UserServiceModel userServiceModel);

    void confirmTeacherRequest(UserServiceModel userServiceModel) throws RoleNotFoundException;

    void cancelTeacherRequest(UserServiceModel userServiceModel);

    void setAboutMeToTheTeacher(AboutMe aboutMe, User user);


}
