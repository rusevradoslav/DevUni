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

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HomeController {
    private final ContractService contractService;
    private final UserService userService;

    @GetMapping("/")
    @PageTitle("Index")
    public String index() {
        System.out.println();
        UserServiceModel loggedUser = contractService.currentUser();
        if (loggedUser != null) {
            return "redirect:/home";
        } else {
            return "index";
        }
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Home")
    public String home(Model model, HttpSession httpSession) {
        System.out.println();
        UserServiceModel currentUser = contractService.currentUser();
        httpSession.setAttribute("user",currentUser);
        httpSession.setAttribute("avatarImg", currentUser.getProfilePicture());

        List<UserDetailsViewModel> allTeachers = this.userService.findAllTeachers();
        model.addAttribute("teachers",allTeachers);
        return "home";

    }

    @GetMapping("/about")
    @PageTitle("About")
    public String about(){

        return "about";
    }

}
