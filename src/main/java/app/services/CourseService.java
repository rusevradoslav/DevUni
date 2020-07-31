package app.services;

import app.models.service.CourseServiceModel;

import java.util.List;

public interface CourseService {

   CourseServiceModel createCourse(String username, CourseServiceModel courseServiceModel) ;

    List<CourseServiceModel> findAllCoursesByAuthorId(String id);

    List<CourseServiceModel> findRecentCourses();

    List<CourseServiceModel> getAllCourses();
}
