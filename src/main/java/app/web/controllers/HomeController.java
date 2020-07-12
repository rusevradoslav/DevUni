package app.web.controllers;

import app.models.service.UserServiceModel;
import app.models.view.UserDetailsViewModel;
import app.services.ContractService;
import app.services.UserService;
import app.validations.anotations.PageTitle;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HomeController {
    private final ContractService contractService;
    private final UserService userService;

    @GetMapping("/")
    @PageTitle("Index")
    public String index() {

        return getCorrectURL();
    }

    @GetMapping("/home")
    @PageTitle("Home")
    public String home(Model model, Principal principal) {
        UserDetailsViewModel userDetailsViewModel = this.userService.getUserDetailsByUsername(principal.getName());
        model.addAttribute("profilePicture",userDetailsViewModel.getProfilePicture());
        return getCorrectURL();

    }

    private String getCorrectURL() {

        UserServiceModel loggedUser = contractService.currentUser();
        if (loggedUser != null) {
            return "home";
        } else {
            return "index";
        }
    }
}
