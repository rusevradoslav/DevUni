package app.web.controllers.view;

import app.models.service.UserServiceModel;
import app.services.CourseService;
import app.services.TopicService;
import app.services.UserService;
import app.validations.anotations.PageTitle;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HomeController {
    private final UserService userService;
    private final CourseService courseService;
    private final TopicService topicService;


    @GetMapping("/")
    @PageTitle("Index")
    public String index(Model model,Principal principal) {



        if (principal != null) {
            return "home";
        } else {
            model.addAttribute("teachers", this.userService.findAllTeachers());
            return "index";
        }
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Home")
    public String home(Model model, HttpSession httpSession, Principal principal) {
        UserServiceModel currentUser = this.userService.findByName(principal.getName());
        httpSession.setAttribute("user", currentUser);
        httpSession.setAttribute("fullName", String.format("%s %s",currentUser.getFirstName(),currentUser.getLastName()));
        httpSession.setAttribute("avatarImg", currentUser.getProfilePicture());
        httpSession.setAttribute("allTopics",this.topicService.findAllTopics());
        httpSession.setAttribute("top3RecentCourses",this.courseService.findRecentCourses());
        model.addAttribute("teachers", this.userService.findAllTeachers());

        return "home";

    }

    @GetMapping("/about")
    @PageTitle("About")
    public String about() {

        return "about";
    }

}
