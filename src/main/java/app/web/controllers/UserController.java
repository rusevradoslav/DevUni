package app.web.controllers;

import app.error.UserAlreadyExistException;
import app.models.binding.*;
import app.models.service.AboutMeServiceModel;
import app.models.service.UserServiceModel;
import app.services.AboutMeService;
import app.services.UserService;
import app.validations.anotations.PageTitle;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

import static app.constants.GlobalConstants.BINDING_RESULT;

@Controller
@RequestMapping("/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final AboutMeService aboutMeService;
    private final ModelMapper modelMapper;

    @GetMapping("/login")
    @PageTitle("Login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        Model model,
                        HttpServletRequest httpServletRequest) {
        if (error != null) {
            String exceptionMessage = getErrorMessage(httpServletRequest, "SPRING_SECURITY_LAST_EXCEPTION");
            model.addAttribute("exceptionMessage", exceptionMessage);
        }

        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            return "redirect:/home";
        }
        return "users/login";
    }

    private String getErrorMessage(HttpServletRequest httpServletRequest, String key) {

        Exception exception =
                (Exception) httpServletRequest.getSession().getAttribute(key);

        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        } else if (exception instanceof LockedException) {
            error = "Your account is locked";
        }
        return error;
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
        return "/users/register";
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

        model.addAttribute("userDetailsViewModel", this.userService.getUserDetailsByUsername(principal.getName()));

        if (!model.containsAttribute("userAddProfilePictureBindingModel")) {
            model.addAttribute("userAddProfilePictureBindingModel", new UserAddProfilePictureBindingModel());
        }
        return "users/user-details";
    }

    @PostMapping("/user-details")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("User Details")
    public String userDetails(@ModelAttribute UserAddProfilePictureBindingModel userAddProfilePictureBindingModel,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        UserServiceModel user = this.userService.findByName(principal.getName());

        try {
            this.userService.addProfilePicture(user, userAddProfilePictureBindingModel.getProfilePicture());

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("imageSizeException", e.getMessage());
            return "redirect:/users/user-details";
        }
        return "redirect:/home";
    }

    @GetMapping("change-email")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Change Email")
    public String changeEmail(Model model) {
        if (!model.containsAttribute("userChangeEmailBindingModel")) {
            model.addAttribute("userChangeEmailBindingModel", new UserChangeEmailBindingModel());
        }
        return "users/change-email";
    }

    @PostMapping("/change-email")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Change Email")
    public String changeEmailConfirm(@Valid @ModelAttribute("userChangeEmailBindingModel") UserChangeEmailBindingModel userChangeEmailBindingModel,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userChangeEmailBindingModel", userChangeEmailBindingModel);
            redirectAttributes.addFlashAttribute(String.format(BINDING_RESULT + "userChangeEmailBindingModel"), bindingResult);
            return "redirect:/users/change-email";
        }
        UserServiceModel user = this.userService.findByName(principal.getName());

        if (user.getEmail().equals(userChangeEmailBindingModel.getOldEmail())) {
            try {
                userService.changeEmail(user, userChangeEmailBindingModel.getNewEmail());
            } catch (UserAlreadyExistException e) {
                redirectAttributes.addFlashAttribute("userChangeEmailBindingModel", userChangeEmailBindingModel);
                redirectAttributes.addFlashAttribute("exceptionMessage", e.getMessage());
                return "redirect:/users/change-email";
            }
        } else {
            redirectAttributes.addFlashAttribute("userChangeEmailBindingModel", userChangeEmailBindingModel);
            redirectAttributes.addFlashAttribute("invalidOldEmail", true);
            return "redirect:/users/change-email";
        }


        return "redirect:/users/user-details";
    }

    @GetMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Change Password")
    public String changePassword(Model model) {
        if (!model.containsAttribute("userChangePasswordBindingModel")) {
            model.addAttribute("userChangePasswordBindingModel", new UserChangePasswordBindingModel());
        }
        return "users/change-password";
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Change Password")
    public String changePasswordConfirm(@Valid @ModelAttribute("userChangePasswordBindingModel") UserChangePasswordBindingModel userChangePasswordBindingModel,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes,
                                        Principal principal,
                                        BCryptPasswordEncoder bCryptPasswordEncoder) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userChangePasswordBindingModel", userChangePasswordBindingModel);
            redirectAttributes.addFlashAttribute(String.format(BINDING_RESULT + "userChangePasswordBindingModel"), bindingResult);
            return "redirect:/users/change-password";
        }
        UserServiceModel user = this.userService.findByName(principal.getName());


        if (bCryptPasswordEncoder.matches(userChangePasswordBindingModel.getOldPassword(), user.getPassword())) {
            userService.changePassword(user, userChangePasswordBindingModel.getNewPassword());
        } else {
            redirectAttributes.addFlashAttribute("userChangePasswordBindingModel", userChangePasswordBindingModel);
            redirectAttributes.addFlashAttribute("invalidOldPassword", true);
            return "redirect:/users/change-password";
        }
        return "redirect:/users/user-details";
    }


    @GetMapping("/teacher-request/{id}")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT')")
    public String sentTeacherRequest(@PathVariable("id") String id) {
        UserServiceModel userServiceModel = this.userService.findById(id);
        this.userService.sentTeacherRequest(userServiceModel);
        return "redirect:/users/user-details";
    }

    @GetMapping("/about-me")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PageTitle("About Me")
    public String writeAboutMe(Model model) {
        if (!model.containsAttribute("aboutMeAddBindingModel")) {
            model.addAttribute("aboutMeAddBindingModel", new AboutMeAddBindingModel());
        }
        return "users/about-me";
    }

    @PostMapping("/about-me")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String writeAboutMeConfirm(@Valid @ModelAttribute("aboutMeAddBindingModel") AboutMeAddBindingModel aboutMeAddBindingModel
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes
            , Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("aboutMeAddBindingModel", aboutMeAddBindingModel);
            redirectAttributes.addFlashAttribute(String.format(BINDING_RESULT + "aboutMeAddBindingModel"), bindingResult);
            return "redirect:/users/about-me";
        }
        UserServiceModel userServiceModel = this.userService.findByName(principal.getName());
        AboutMeServiceModel aboutMeServiceModel = this.modelMapper.map(aboutMeAddBindingModel, AboutMeServiceModel.class);

        this.aboutMeService.addTeacherAboutMe(userServiceModel,aboutMeServiceModel);
        return "redirect:/home";
    }
}


