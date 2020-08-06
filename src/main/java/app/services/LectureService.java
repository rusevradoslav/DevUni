package app.services;

import app.error.FileStorageException;
import app.models.service.CourseServiceModel;
import app.models.service.LectureServiceModel;

import java.util.List;

public interface LectureService {
    LectureServiceModel addLecture(CourseServiceModel courseServiceModel, LectureServiceModel lectureServiceModel) throws FileStorageException;



    String findLectureResourcesIDByLectureId(String lectureId);

    LectureServiceModel findLectureById(String lectureId);



    int findAllLecturesAssignmentCount(List<CourseServiceModel> courseServiceModelList);
}
