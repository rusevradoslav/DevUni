package app.web.controllers.view;

import app.error.FileStorageException;
import app.models.binding.AssignmentSolutionAddBindingModel;
import app.models.service.AssignmentServiceModel;
import app.models.service.LectureServiceModel;
import app.models.service.UserServiceModel;
import app.services.AssignmentService;
import app.services.LectureService;
import app.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("/assignments")
public class AssigmentController {

    private final AssignmentService assignmentService;
    private final LectureService lectureService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN,ROLE_STUDENT')")
    @PostMapping("/upload/userAssignment/{id}")
    public String uploadUserAssignmentSolution(@PathVariable("id") String lectureId,
                                               @ModelAttribute("solutionAddBindingModel") AssignmentSolutionAddBindingModel solutionAddBindingModel,
                                               RedirectAttributes redirectAttributes,
                                               Principal principal) throws FileStorageException {

        LectureServiceModel lectureServiceModel = lectureService.findLectureById(lectureId);
        UserServiceModel userServiceModel = userService.findByName(principal.getName());
        AssignmentServiceModel assignmentServiceModel = this.modelMapper.map(solutionAddBindingModel, AssignmentServiceModel.class);
        String courseId = lectureServiceModel.getCourse().getId();


        assignmentService.uploadUserAssignmentSolution(lectureServiceModel, userServiceModel, assignmentServiceModel);


        return "redirect:/courses/courseDetails/" + courseId;

    }
}
