package app.web.controllers.view;

import app.error.FileStorageException;
import app.models.binding.LectureAddBindingModel;
import app.models.service.CourseServiceModel;
import app.models.service.LectureServiceModel;
import app.services.CourseService;
import app.services.LectureService;
import app.services.UserService;
import app.validations.anotations.PageTitle;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

import static app.constants.GlobalConstants.BINDING_RESULT;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/lectures")
public class LectureController {
    private CourseService courseService;
    private LectureService lectureService;
    private UserService userService;
    private ModelMapper modelMapper;

    @GetMapping("/createLecture/{courseId}")
    public String createLecture(@PathVariable("courseId") String courseId, Model model) {

        model.addAttribute("courseId", courseId);

        if (!model.containsAttribute("lectureAddBindingModel")) {
            model.addAttribute("lectureAddBindingModel", new LectureAddBindingModel());
        }
        return "lectures/create-lecture";
    }

    @PostMapping("/createLecture/{courseId}")
    public String createLectureConfirm(@PathVariable("courseId") String courseId,
                                       @Valid @ModelAttribute("lectureAddBindingModel") LectureAddBindingModel lectureAddBindingModel,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("lectureAddBindingModel", lectureAddBindingModel);
            redirectAttributes.addFlashAttribute(String.format(BINDING_RESULT + "lectureAddBindingModel"), bindingResult);
            return "redirect:/lectures/createLecture/" + courseId;
        }

        CourseServiceModel courseServiceModel = courseService.findCourseById(courseId);
        LectureServiceModel lectureServiceModel = this.modelMapper.map(lectureAddBindingModel, LectureServiceModel.class);
        try {
            this.lectureService.addLecture(courseServiceModel, lectureServiceModel);
        } catch (FileStorageException fileException) {
            redirectAttributes.addFlashAttribute("fileSizeException", fileException.getMessage());
            return "redirect:/lectures/createLecture/" + courseId;
        }

        return "redirect:/courses/courseDetails/" + courseId;
    }

    @GetMapping("/lectureVideo/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Lecture Video")
    public String watchLectureVideo(@PathVariable("id") String lectureId, Model model) {

        LectureServiceModel lecture = lectureService.findLectureById(lectureId);
        model.addAttribute("lecture", lecture);
        return "lectures/lecture-video";
    }

    @GetMapping("/course-check-lectures/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PageTitle("Course Lectures")
    public String teacherCheckAssignmentCourses(@PathVariable("id") String courseId, Model model) {

        List<LectureServiceModel> allLecturesForCourse = courseService.findAllLecturesForCourse(courseId);
        model.addAttribute("allLectures", allLecturesForCourse);
        return "courses/course-all-lectures-table";
    }
}
