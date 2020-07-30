package app.web.controllers.view;

import app.models.binding.CourseCreateBindingModel;
import app.models.service.CourseServiceModel;
import app.services.CourseService;
import app.services.TopicService;
import app.services.UserService;
import app.validations.anotations.PageTitle;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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
import java.io.IOException;
import java.security.Principal;

import static app.constants.GlobalConstants.BINDING_RESULT;

@Controller
@RequestMapping("/courses")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CourseController {
    private TopicService topicService;
    private CourseService courseService;
    private UserService userService;
    private ModelMapper modelMapper;

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
                                      RedirectAttributes redirectAttributes,
                                      Principal principal)  {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("courseCreateBindingModel", courseCreateBindingModel);
            redirectAttributes.addFlashAttribute(String.format(BINDING_RESULT + "courseCreateBindingModel"), bindingResult);
            return "redirect:/courses/create";
        }

        CourseServiceModel courseServiceModel = this.modelMapper.map(courseCreateBindingModel, CourseServiceModel.class);

        try {
            this.courseService.createCourse(principal.getName(),courseServiceModel);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("imageSizeException",e.getMessage());
            return "redirect:/courses/create";
        }

        return "redirect:/courses/teacher-courses";
    }
    @GetMapping("/teacher-courses")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PageTitle("Teacher Course")
    public String teacherCourses(Model model) {

        return "courses/teacher-courses";
    }



}
