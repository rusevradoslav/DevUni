package app.web.controllers.view;

        import app.error.UserAlreadyExistException;
        import app.models.binding.AdminCreateBindingModel;
        import app.models.service.UserServiceModel;
        import app.models.view.UserDetailsViewModel;
        import app.services.CourseService;
        import app.services.UserService;
        import app.validations.anotations.PageTitle;
        import lombok.AllArgsConstructor;
        import org.modelmapper.ModelMapper;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.access.prepost.PreAuthorize;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.validation.BindingResult;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.servlet.mvc.support.RedirectAttributes;

        import javax.management.relation.RoleNotFoundException;
        import javax.servlet.http.HttpSession;
        import javax.validation.Valid;
        import java.security.Principal;
        import java.util.List;

        import static app.constants.GlobalConstants.BINDING_RESULT;

@Controller
@RequestMapping("/admins")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {
    private final UserService userService;
    private final CourseService courseService;

    private final ModelMapper modelMapper;


    @GetMapping("/home-page")
    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN','ROLE_ADMIN')")
    @PageTitle("Admin Home Page")
    public String adminHomePage(Model model, HttpSession httpSession, Principal principal) {


        UserServiceModel loggedUser = this.userService.findByName(principal.getName());
        httpSession.setAttribute("avatarImg", loggedUser.getProfilePicture());
        List<UserDetailsViewModel> allAdmins = this.userService.findAllAdmins();
        System.out.println();
        model.addAttribute("allAdmins", allAdmins);
        return "admins/admin-home";
    }

    @GetMapping("/all-admins")
    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN','ROLE_ADMIN')")
    @PageTitle("All Admins")
    public String allAdmins() {
        return "admins/root-all-admins";
    }

    @GetMapping("/block-admin/{id}")
    public String blockAdminProfile(@PathVariable("id") String id) {
        UserServiceModel userServiceModel = this.userService.findById(id);
        this.userService.blockUser(userServiceModel);
        return "redirect:/admins/all-admins";
    }

    @GetMapping("/activate-admin/{id}")
    public String activateAdminProfile(@PathVariable("id") String id) {
        UserServiceModel userServiceModel = this.userService.findById(id);
        this.userService.activateUser(userServiceModel);
        return "redirect:/admins/all-admins";
    }

    @GetMapping("/demote-admin-student/{id}")
    public String demoteAdminToStudent(@PathVariable("id") String id) throws RoleNotFoundException {
        UserServiceModel userServiceModel = this.userService.findById(id);
        this.userService.changeRoleToStudent(userServiceModel);
        return "redirect:/admins/all-admins";

    }

    @GetMapping("/demote-admin-teacher/{id}")
    public String demoteAdminToTeacher(@PathVariable("id") String id) throws RoleNotFoundException {
        UserServiceModel userServiceModel = this.userService.findById(id);
        this.userService.changeRoleToTeacher(userServiceModel);
        return "redirect:/admins/all-admins";
    }


    @GetMapping("/all-students")
    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN','ROLE_ADMIN')")
    @PageTitle("All Students")
    public String allStudents() {
        return "admins/admin-all-students";
    }

    @GetMapping("/block-student/{id}")
    public String blockStudentProfile(@PathVariable("id") String id) {
        UserServiceModel userServiceModel = this.userService.findById(id);
        this.userService.blockUser(userServiceModel);
        return "redirect:/admins/all-students";
    }

    @GetMapping("/activate-student/{id}")
    public String activateStudentProfile(@PathVariable("id") String id) {
        UserServiceModel userServiceModel = this.userService.findById(id);
        this.userService.activateUser(userServiceModel);
        return "redirect:/admins/all-students";
    }

    @GetMapping("/promote-student-teacher/{id}")
    public String promoteStudentToTeacher(@PathVariable("id") String id) throws RoleNotFoundException {
        UserServiceModel user = this.userService.findById(id);
        this.userService.changeRoleToTeacher(user);
        return "redirect:/admins/all-students";
    }

    @GetMapping("/promote-student-admin/{id}")
    public String promoteStudentToAdmin(@PathVariable("id") String id) throws RoleNotFoundException {
        UserServiceModel user = this.userService.findById(id);
        this.userService.changeRoleToAdmin(user);
        return "redirect:/admins/all-students";
    }

    @GetMapping("/all-teachers")
    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN','ROLE_ADMIN')")
    @PageTitle("All Teachers")
    public String allTeachers() {
        return "admins/admin-all-teachers";
    }

    @GetMapping("/block-teacher/{id}")
    public String blockTeacherProfile(@PathVariable("id") String id) {
        UserServiceModel userServiceModel = this.userService.findById(id);
        this.userService.blockUser(userServiceModel);
        return "redirect:/admins/all-teachers";
    }

    @GetMapping("/activate-teacher/{id}")
    public String activateTeacherProfile(@PathVariable("id") String id) {
        UserServiceModel userServiceModel = this.userService.findById(id);
        this.userService.activateUser(userServiceModel);
        return "redirect:/admins/all-teachers";
    }

    @GetMapping("/demote-teacher-student/{id}")
    public String demoteTeacherToStudent(@PathVariable("id") String id) throws RoleNotFoundException {
        UserServiceModel userServiceModel = this.userService.findById(id);
        this.userService.changeRoleToStudent(userServiceModel);
        return "redirect:/admins/all-teachers";
    }

    @GetMapping("/promote-teacher-admin/{id}")
    public String promoteTeacherToAdmin(@PathVariable("id") String id) throws RoleNotFoundException {
        UserServiceModel user = this.userService.findById(id);
        this.userService.changeRoleToAdmin(user);
        return "redirect:/admins/all-teachers";
    }


    @GetMapping("/all-teacher-requests")
    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN','ROLE_ADMIN')")
    @PageTitle("All Teacher Requests")
    public String allTeacherRequests() {

        return "admins/admin-all-teacher-requests";
    }

    @GetMapping("/confirm-teacher-request/{id}")
    public String confirmTeacherRequest(@PathVariable("id") String id) throws RoleNotFoundException {
        UserServiceModel userServiceModel = this.userService.findById(id);
        this.userService.confirmTeacherRequest(userServiceModel);
        return "redirect:/admins/all-teacher-requests";
    }

    @GetMapping("/cancel-teacher-request/{id}")
    public String cancelTeacherRequest(@PathVariable("id") String id) {
        UserServiceModel userServiceModel = this.userService.findById(id);
        this.userService.cancelTeacherRequest(userServiceModel);
        return "redirect:/admins/all-teacher-requests";
    }


    @GetMapping("/create-admin")
    @PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
    @PageTitle("Create Admin")
    public String createAdmin(Model model) {
        if (!model.containsAttribute("adminCreateBindingModel")) {
            model.addAttribute("adminCreateBindingModel", new AdminCreateBindingModel());
        }
        return "admins/create-admin";
    }

    @PostMapping("/create-admin")
    @PreAuthorize("hasRole('ROLE_ROOT_ADMIN')")
    @PageTitle("Create Admin")
    public String createAdminConfirm(@Valid @ModelAttribute("adminCreateBindingModel") AdminCreateBindingModel adminCreateBindingModel,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes
    ) {
        System.out.println();
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("adminCreateBindingModel", adminCreateBindingModel);
            redirectAttributes.addFlashAttribute(String.format(BINDING_RESULT + "adminCreateBindingModel"), bindingResult);
            return "redirect:/admins/create-admin";
        }
        try {
            this.userService.createNewAdminAccount(this.modelMapper.map(adminCreateBindingModel, UserServiceModel.class));
        } catch (UserAlreadyExistException | RoleNotFoundException e) {
            redirectAttributes.addFlashAttribute("adminCreateBindingModel", adminCreateBindingModel);
            redirectAttributes.addFlashAttribute("exceptionMessage", e.getMessage());
            return "redirect:/admins/create-admin";
        }
        return "redirect:/admins/all-admins";
    }

    @GetMapping("/all-courses-details")
    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN','ROLE_ADMIN')")
    @PageTitle("All Courses")
    public String allCourses(Model model) {
        model.addAttribute("allCourses", this.courseService.getAllCourses());
        return "admins/admin-all-courses";
    }

    @GetMapping("/enable-course/{id}")
    public String enableCourse(@PathVariable("id") String id) throws RoleNotFoundException {
        this.courseService.enableCourse(id);
        return "redirect:/admins/all-courses-details";
    }

    @GetMapping("/disable-course/{id}")
    public String disableCourse(@PathVariable("id") String id) {
        this.courseService.disableCourse(id);
        return "redirect:/admins/all-courses-details";
    }

}
