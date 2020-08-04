package app.services;

import app.error.FileStorageException;
import app.models.service.CourseServiceModel;
import app.models.service.LectureServiceModel;

public interface LectureService {
    LectureServiceModel addLecture(CourseServiceModel courseServiceModel, LectureServiceModel lectureServiceModel) throws FileStorageException;



    String findLectureResourcesIDByLectureId(String lectureId);

    LectureServiceModel findLectureById(String lectureId);
}
