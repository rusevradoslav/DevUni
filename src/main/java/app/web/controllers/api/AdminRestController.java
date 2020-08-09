package app.web.controllers.api;

import app.models.view.UserDetailsViewModel;
import app.services.UserService;
import app.validations.anotations.PageTitle;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/admins")
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN','ROLE_ADMIN')")
public class AdminRestController {

    private final UserService userService;

    @GetMapping("/all-admins-rest")
    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN','ROLE_ADMIN')")
    @PageTitle("All Admins")
    public List<UserDetailsViewModel> allAdmins() {
        List<UserDetailsViewModel> allAdmins = userService.findAllAdmins();
        return allAdmins; }

    @GetMapping("/all-students-rest")
    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN','ROLE_ADMIN')")
    @PageTitle("All Admins")
    public List<UserDetailsViewModel> allStudents() { return userService.findAllStudents(); }

    @GetMapping("/all-teachers-rest")
    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN','ROLE_ADMIN')")
    @PageTitle("All Teachers")
    public List<UserDetailsViewModel> allTeachers() {
        return userService.findAllTeachers();
    }

    @GetMapping("/all-teachers-requests-rest")
    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN','ROLE_ADMIN')")
    @PageTitle("All Teacher Requests")
    public List<UserDetailsViewModel> allTeacherRequests() { return userService.findAllStudentsWithRequests();
    }

}
