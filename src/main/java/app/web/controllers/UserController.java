package app.web.controllers;

import app.exceptions.UserAlreadyExistException;
import app.models.binding.UserDetailsBindingModel;
import app.models.binding.UserRegisterBindingModel;
import app.models.service.UserServiceModel;
import app.services.UserService;
import app.validations.anotations.PageTitle;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

import static app.constants.GlobalConstants.BINDING_RESULT;

@Controller
@RequestMapping("/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/login")
    @PageTitle("Login")
    public String login() {

        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            return "redirect:/home";
        }
        return "login";
    }


    @GetMapping("/register")
    @PageTitle("Register")
    public String register(Model model) {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            return "redirect:/home";
        }
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }
        return "register";
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Register")
    public String registerConfirm(@Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute(String.format(BINDING_RESULT + "userRegisterBindingModel"), bindingResult);
            return "redirect:/users/register";
        }
        try {
            userService.registerNewUserAccount(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        } catch (UserAlreadyExistException e) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("exceptionMessage", e.getMessage());
            return "redirect:/users/register";
        }

        return "redirect:/users/login";
    }

    @GetMapping("/user-details")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("User Details")
    public String userDetails(Principal principal, Model model) {
        System.out.println();

        model.addAttribute("userDetailsViewModel", this.userService.getUserDetailsByUsername(principal.getName()));
        if (!model.containsAttribute("userDetailsBindingModel")) {
            model.addAttribute("userDetailsBindingModel", new UserDetailsBindingModel());
        }
        return "user-details";
    }

    @PostMapping("user-details")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("User Details")
    public String userDetailsConfirm(@Valid @ModelAttribute("userDetailsBindingModel") UserDetailsBindingModel userDetailsBindingModel,
                                     RedirectAttributes redirectAttributes,
                                     Principal principal) {
       /* System.out.println();
        UserServiceModel user = this.userService.findByName(principal.getName());

        if (!userDetailsBindingModel.getNewEmail().isEmpty()) {
            try {
                userService.changeUserEmail(user, userDetailsBindingModel.getNewEmail());
            } catch (InvalidEmailException e) {
                redirectAttributes.addFlashAttribute("userDetailsBindingModel",userDetailsBindingModel);
                redirectAttributes.addFlashAttribute("invalidEmail",true);
                redirectAttributes.addFlashAttribute("message",e.getMessage());
                return "redirect:/users/user-details";
            }
        }
        if (user.getPassword().equals(userDetailsBindingModel.getPassword())){

        }else {
            redirectAttributes.addFlashAttribute("invalidUserPassword", true);
            return "redirect:/users/user-details";
        }

        return "redirect:/home";
    }*/
        return "redirect:/home";
    }
}

