package app.web.controllers;

import app.models.service.UserServiceModel;
import app.services.ContractService;
import app.services.UserService;
import app.validations.anotations.PageTitle;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String home(Model model) {
        System.out.println();
        UserServiceModel loggedUser = contractService.currentUser();
        model.addAttribute("profilePicture",loggedUser.getProfilePicture());
        return "home";

    }


}
