package app.web.controllers;

import app.models.entity.User;
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
  /*  @PreAuthorize("isAnonymous()")*/
    @PageTitle("Index")
    public String index() {
        System.out.println();
        User loggedUser = contractService.currentUser();
        if (loggedUser!=null){
            return "home";
        }else {
            return "index";
        }
    }

  /*  @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Home")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.setViewName("home");
        return modelAndView;
    }*/
}
