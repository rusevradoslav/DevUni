package app.web.controllers.view;

import app.models.binding.CourseCreateBindingModel;
import app.services.CourseService;
import app.services.TopicService;
import app.validations.anotations.PageTitle;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/courses")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CourseController {
    private TopicService topicService;
    private CourseService courseService;

    @GetMapping("/create")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PageTitle("Create Course")
    public String createCourse(Model model) {

        if (!model.containsAttribute("courseCreateBindingModel")) {
            model.addAttribute("courseCreateBindingModel", new CourseCreateBindingModel());
        }
        model.addAttribute("topicNames", this.topicService.getAllTopicNames());

        return "courses/create-course";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PageTitle("Create Course")
    public String createCourseConfirm(@Valid @ModelAttribute("courseCreateBindingModel") CourseCreateBindingModel courseCreateBindingModel,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {
        //TODO
        System.out.println();
        return "courses/create-course";
    }


}
