package app.web.controllers;

import app.models.service.UserServiceModel;
import app.services.ContractService;
import app.validations.anotations.PageTitle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ContractService contractService;

    public HomeController(ContractService contractService) {
        this.contractService = contractService;
    }


    @GetMapping("/")
    @PageTitle("Index")
    public String index() {

        return getCorrectURL();
    }

    @GetMapping("/home")
    @PageTitle("Home")
    public String home() {
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
