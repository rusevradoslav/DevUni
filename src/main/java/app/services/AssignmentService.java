package app.services;

import app.error.FileStorageException;
import app.models.service.AssignmentServiceModel;
import app.models.service.LectureServiceModel;
import app.models.service.UserServiceModel;

public interface AssignmentService {
    AssignmentServiceModel uploadUserAssignmentSolution(LectureServiceModel lectureServiceModel, UserServiceModel userServiceModel, AssignmentServiceModel assignmentServiceModel) throws FileStorageException;
}
