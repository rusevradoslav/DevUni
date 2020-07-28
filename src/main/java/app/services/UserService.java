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
    UserServiceModel registerNewUserAccount(UserServiceModel map) throws UserAlreadyExistException, RoleNotFoundException;

    UserServiceModel findByEmail(String email) throws InvalidEmailException;

    UserServiceModel findByName(String username);

    UserDetailsViewModel getUserDetailsByUsername(String username);

    UserServiceModel changePassword(UserServiceModel user, String newPassword);

    UserServiceModel changeEmail(UserServiceModel user, String newEmail);

    void addProfilePicture(UserServiceModel user, MultipartFile profilePicture) throws IOException;

    List<UserDetailsViewModel> findAllAdmins();

    List<UserDetailsViewModel> findAllStudents();

    List<UserDetailsViewModel> findAllTeachers();

    List<UserDetailsViewModel> findAllStudentsWithRequests();

    UserServiceModel findById(String id);

    UserServiceModel blockUser(UserServiceModel userServiceModel);

    UserServiceModel activateUser(UserServiceModel userServiceModel);

    UserServiceModel changeRoleToStudent(UserServiceModel userServiceModel) throws RoleNotFoundException;

    UserServiceModel changeRoleToTeacher(UserServiceModel userServiceModel) throws RoleNotFoundException;

    UserServiceModel changeRoleToAdmin(UserServiceModel user) throws RoleNotFoundException;

    UserServiceModel createNewAdminAccount(UserServiceModel map) throws UserAlreadyExistException, RoleNotFoundException;

    UserServiceModel sentTeacherRequest(UserServiceModel userServiceModel);

    UserServiceModel confirmTeacherRequest(UserServiceModel userServiceModel) throws RoleNotFoundException;

    UserServiceModel cancelTeacherRequest(UserServiceModel userServiceModel);

    void setAboutMeToTheTeacher(AboutMe aboutMe, User user);


}
