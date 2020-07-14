package app.web.controllers;

import app.validations.anotations.PageTitle;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admins")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {

    @GetMapping("home-page")
    @PageTitle("Admin Home Page")
    public String adminHomePage(){
        return "admins/admin-home";
    }
}
