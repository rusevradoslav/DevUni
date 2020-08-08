package app.services;

import app.error.FileStorageException;
import app.models.service.AssignmentServiceModel;
import app.models.service.LectureServiceModel;
import app.models.service.UserServiceModel;

import java.util.List;

public interface AssignmentService {
    AssignmentServiceModel uploadUserAssignmentSolution(LectureServiceModel lectureServiceModel, UserServiceModel userServiceModel, AssignmentServiceModel assignmentServiceModel) throws FileStorageException;

    List<AssignmentServiceModel> findAllSubmittedAssignments(LectureServiceModel lecture);

    String findFileForAssignmentById(String assignmentId);

    AssignmentServiceModel getAssignmentById(String assignmentId);

    AssignmentServiceModel checkAssignment(AssignmentServiceModel assignmentServiceModel);
}
