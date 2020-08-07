package app.web.controllers.view;

import app.error.FileStorageException;
import app.models.binding.AssignmentCheckBindingModel;
import app.models.binding.AssignmentSolutionAddBindingModel;
import app.models.service.AssignmentServiceModel;
import app.models.service.LectureServiceModel;
import app.models.service.UserServiceModel;
import app.services.AssignmentService;
import app.services.LectureService;
import app.services.UserService;
import app.validations.anotations.PageTitle;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

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


        this.assignmentService.uploadUserAssignmentSolution(lectureServiceModel, userServiceModel, assignmentServiceModel);


        return "redirect:/courses/courseDetails/" + courseId;

    }

    @GetMapping("/check-assignment/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PageTitle("Check Assignment")
    public String checkAssignment(@PathVariable("id") String lectureId, Model model) {

        if (!model.containsAttribute("assignmentCheckBindingModel")) {
            model.addAttribute("assignmentCheckBindingModel", new AssignmentCheckBindingModel());
        }
        System.out.println();
        LectureServiceModel lecture = lectureService.findLectureById(lectureId);
        List<AssignmentServiceModel> allSubmittedAssignments = this.assignmentService.findAllSubmittedAssignments(lecture);
        model.addAttribute("allSubmittedAssignments", allSubmittedAssignments);
        return "assignments/teacher-check-assignment";
    }
    @PostMapping("/check-assignment/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PageTitle("Check Assignment")
    public String checkAssignmentConfirm(@PathVariable("id") String assignmentId, Model model, AssignmentCheckBindingModel assignmentCheckBindingModel) {
        AssignmentServiceModel assignmentServiceModel = assignmentService.getAssignmentById(assignmentId);
        String courseId = assignmentServiceModel.getLecture().getCourse().getId();
        assignmentServiceModel.setGradePercentage(assignmentCheckBindingModel.getGradePercentage());
        assignmentService.checkAssignment(assignmentServiceModel);
        System.out.println();
        return "redirect:/lectures/course-check-lectures/"+courseId;
    }





}
