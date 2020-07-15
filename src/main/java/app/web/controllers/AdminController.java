package app.web.controllers;

import app.models.service.UserServiceModel;
import app.models.view.UserDetailsViewModel;
import app.services.ContractService;
import app.services.UserService;
import app.validations.anotations.PageTitle;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admins")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {
    private final UserService userService;
    private final ContractService contractService;


    @GetMapping("/home-page")
    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN','ROLE_ADMIN')")
    @PageTitle("Admin Home Page")
    public String adminHomePage(Model model, HttpSession httpSession) {
        UserServiceModel loggedUser = contractService.currentUser();
        httpSession.setAttribute("avatarImg", loggedUser.getProfilePicture());
        List<UserDetailsViewModel> allAdmins = this.userService.findAllAdmins();
        model.addAttribute("allAdmins", allAdmins);
        return "admins/admin-home";
    }

    @GetMapping("/all-admins")
    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN','ROLE_ADMIN')")
    @PageTitle("All Admins")
    public String allAdmins() {
        List<UserDetailsViewModel> allAdmins = this.userService.findAllAdmins();
        System.out.println();
        return "admins/root-all-admins";
    }
}
