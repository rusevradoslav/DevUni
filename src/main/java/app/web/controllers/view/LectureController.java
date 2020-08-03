package app.web.controllers.view;

import app.models.binding.LectureAddBindingModel;
import app.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static app.constants.GlobalConstants.BINDING_RESULT;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/lectures")
public class LectureController {
    private CourseService courseService;

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
        System.out.println();
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("lectureAddBindingModel", lectureAddBindingModel);
            redirectAttributes.addFlashAttribute(String.format(BINDING_RESULT + "lectureAddBindingModel"), bindingResult);
            return "redirect:/lectures/createLecture/" + courseId;
        }

        return "redirect:/courses/courseDetails/" + courseId;
    }

}
