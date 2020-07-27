package app.web.controllers;

import app.models.view.UserDetailsViewModel;
import app.services.UserService;
import app.validations.anotations.PageTitle;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/admins")
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminRestController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @GetMapping("/all-admins-rest")
    @PreAuthorize("hasAnyRole('ROLE_ROOT_ADMIN','ROLE_ADMIN')")
    @PageTitle("All Admins")
    public List<UserDetailsViewModel> allAdmins() {
        return userService.findAllAdmins();
    }
}