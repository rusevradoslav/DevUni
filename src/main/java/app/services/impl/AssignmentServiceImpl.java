package app.services.impl;

import app.error.AssignmentNotFoundException;
import app.error.FileStorageException;
import app.models.entity.*;
import app.models.service.AssignmentServiceModel;
import app.models.service.LectureServiceModel;
import app.models.service.UserServiceModel;
import app.repositories.AssignmentRepository;
import app.services.AssignmentService;
import app.services.CourseService;
import app.services.CustomFileService;
import app.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final ModelMapper modelMapper;
    private final CustomFileService customFileService;
    private final CourseService courseService;
    private final UserService userService;

    @Override
    public AssignmentServiceModel uploadUserAssignmentSolution(LectureServiceModel lectureServiceModel,
                                                               UserServiceModel userServiceModel,
                                                               AssignmentServiceModel assignmentServiceModel) throws FileStorageException {

        Lecture lecture = this.modelMapper.map(lectureServiceModel, Lecture.class);
        User user = this.modelMapper.map(userServiceModel, User.class);
        Assignment assignment = this.modelMapper.map(assignmentServiceModel, Assignment.class);
        assignment.setUser(user);
        assignment.setLecture(lecture);
        assignment.setDescription(String.format("Description: %s %s", user.getUsername(), lecture.getTitle()));
        assignment.setChecked(false);
        Assignment assignmentByDescription = assignmentRepository.findFirstByDescription(assignment.getDescription()).orElse(null);

        if (assignmentByDescription != null) {
            String oldAssignmentSubmission = assignmentByDescription.getFile().getId();

            MultipartFile assignmentSubmission = assignmentServiceModel.getFile();
            CustomFile customFile = customFileService.storeFile(assignmentSubmission);
            assignmentByDescription.setFile(customFile);
            Assignment newAssignment = this.assignmentRepository.save(assignmentByDescription);

            this.customFileService.deleteFile(oldAssignmentSubmission);

            return this.modelMapper.map(newAssignment, AssignmentServiceModel.class);
        }

        MultipartFile assignmentSubmission = assignmentServiceModel.getFile();
        CustomFile customFile = customFileService.storeFile(assignmentSubmission);
        assignment.setFile(customFile);

        Assignment newAssignment = this.assignmentRepository.save(assignment);


        return this.modelMapper.map(newAssignment, AssignmentServiceModel.class);
    }

    @Override
    public List<AssignmentServiceModel> findAllSubmittedAssignments(LectureServiceModel lecture) {
        return this.assignmentRepository
                .findAllByLecture_Id(lecture.getId())
                .stream()
                .map(assignment -> this.modelMapper.map(assignment, AssignmentServiceModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public String findAssignmentById(String assignmentId) {
        Assignment assignment = this.assignmentRepository.findById(assignmentId).orElseThrow(() -> new AssignmentNotFoundException("Assignment with given id was not found !"));
        return assignment.getFile().getId();
    }

    @Override
    public AssignmentServiceModel getAssignmentById(String assignmentId) {
        Assignment assignment = this.assignmentRepository.findById(assignmentId).orElseThrow(() -> new AssignmentNotFoundException("Assignment with given id was not found !"));
        return this.modelMapper.map(assignment, AssignmentServiceModel.class);
    }

    @Override
    public AssignmentServiceModel checkAssignment(AssignmentServiceModel assignmentServiceModel) {

        LectureServiceModel lectureServiceModel = assignmentServiceModel.getLecture();

        Course course = this.modelMapper.map(lectureServiceModel.getCourse(), Course.class);

        int lecturesCountInCourse = course.getLectures().size();

        UserServiceModel userServiceModel = this.userService.findById(assignmentServiceModel.getUser().getId());

        User user = this.modelMapper.map(userServiceModel, User.class);

        Assignment assignment = this.modelMapper.map(assignmentServiceModel, Assignment.class);
        assignment.setChecked(true);
        Assignment savedAssignment = this.assignmentRepository.save(assignment);


        List<Assignment> allAssignmentsByUserIdAndCourseId = assignmentRepository.findAllAssignmentsByUserIdAndCourseId(user.getId(), course.getId());
        double sum = 0;
        for (Assignment assignment1 : allAssignmentsByUserIdAndCourseId) {
            sum += assignment1.getGradePercentage();
        }

        double avgSum = sum / lecturesCountInCourse;
        if (avgSum >= course.getPassPercentage()){
            userService.updateUser(user.getId(),course);

        }




        return this.modelMapper.map(savedAssignment, AssignmentServiceModel.class);
    }


}
