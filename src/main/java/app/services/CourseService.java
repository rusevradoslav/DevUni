package app.services;

import app.models.service.CourseServiceModel;

import java.io.IOException;

public interface CourseService {

   CourseServiceModel createCourse(String username, CourseServiceModel courseServiceModel) throws IOException;
}
