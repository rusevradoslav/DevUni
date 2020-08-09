package app.services.impl;

import app.error.CourseNotFoundException;
import app.models.entity.Course;
import app.models.entity.Difficulty;
import app.models.entity.Topic;
import app.models.entity.User;
import app.models.service.CourseServiceModel;
import app.models.service.LectureServiceModel;
import app.models.service.TopicServiceModel;
import app.models.service.UserServiceModel;
import app.repositories.CourseRepository;
import app.services.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserService userService;
    private final TopicService topicService;
    private final CloudinaryService cloudinaryService;
    private final LectureService lectureService;
    private final ModelMapper modelMapper;


    @Override
    public CourseServiceModel createCourse(String username, CourseServiceModel courseServiceModel) {
        Course course = this.modelMapper.map(courseServiceModel, Course.class);

        User user = this.modelMapper.map(this.userService.findByName(username), User.class);
        Set<Course> createdCourses = user.getCreatedCourses();
        createdCourses.add(course);
        user.setCreatedCourses(createdCourses);

        Topic topic = this.topicService.findTopicByName(courseServiceModel.getTopic());
        List<Course> coursesInTopic = topic.getCourses();
        coursesInTopic.add(course);
        topic.setCourses(coursesInTopic);


        LocalDateTime startingOn = courseServiceModel.getStartedOn();
        LocalDateTime endingOn = startingOn.plus(courseServiceModel.getDurationWeeks() * 7, ChronoUnit.DAYS);

        course.setAuthor(user);
        course.setTopic(topic);
        course.setDifficulty(Difficulty.valueOf(courseServiceModel.getDifficulty()));
        course.setEndedON(endingOn);
        course.setGraduatedStudents(new ArrayList<>());

        this.courseRepository.saveAndFlush(course);
        Course newlyCreatedCourse = this.courseRepository.findById(course.getId()).orElse(null);
        return this.modelMapper.map(newlyCreatedCourse, CourseServiceModel.class);
    }

    @Override
    public List<CourseServiceModel> findAllCoursesByAuthorId(String id) {

        return courseRepository.findAllCoursesByAuthorId(id)
                .stream()
                .map(course -> this.modelMapper.map(course, CourseServiceModel.class))
                .collect(Collectors.toList());


    }

    @Override
    public List<CourseServiceModel> findRecentCourses() {
        return this.courseRepository.findRecentCourses().stream().limit(3)
                .map(course -> this.modelMapper.map(course, CourseServiceModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<CourseServiceModel> getAllCourses() {

        return this.courseRepository
                .findAll()
                .stream()
                .map(course -> {
                    UserServiceModel userServiceModel = this.modelMapper.map(course.getAuthor(), UserServiceModel.class);
                    TopicServiceModel topicServiceModel = this.modelMapper.map(course.getTopic(), TopicServiceModel.class);
                    CourseServiceModel courseServiceModel = this.modelMapper.map(course, CourseServiceModel.class);
                    courseServiceModel.setAuthor(userServiceModel);
                    courseServiceModel.setTopic(topicServiceModel.getName());
                    return courseServiceModel;
                }).collect(Collectors.toList());
    }

    @Override
    public CourseServiceModel enableCourse(String id) {
        this.courseRepository.changeCourseStatusToTrue(id);
        Course course = this.courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException("User with given id was not found"));
        return this.modelMapper.map(course, CourseServiceModel.class);
    }

    @Override
    public CourseServiceModel disableCourse(String id) {
        this.courseRepository.changeCourseStatusToFalse(id);
        Course course = this.courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException("User with given id was not found"));
        return this.modelMapper.map(course, CourseServiceModel.class);
    }

    @Override
    public List<CourseServiceModel> findAllCoursesWithStatusTrue() {
        return this.courseRepository.findAllCoursesWithStatusTrue()
                .stream()
                .filter(course -> !course.getStartedOn().isBefore(LocalDateTime.now()))
                .map(course -> this.modelMapper.map(course, CourseServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public List<CourseServiceModel> findAllCoursesInTopic(String id) {
        return this.courseRepository.findAllCoursesInTopic(id)
                .stream()
                .filter(course -> !course.getStartedOn().isBefore(LocalDateTime.now()))
                .map(course -> this.modelMapper.map(course, CourseServiceModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public CourseServiceModel findCourseById(String id) {
        Course course = this.courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException("Course with given id was not found"));
        UserServiceModel userServiceModel = this.modelMapper.map(course.getAuthor(), UserServiceModel.class);
        TopicServiceModel topicServiceModel = this.modelMapper.map(course.getTopic(), TopicServiceModel.class);
        CourseServiceModel courseServiceModel = this.modelMapper.map(course, CourseServiceModel.class);
        courseServiceModel.setAuthor(userServiceModel);
        courseServiceModel.setTopic(topicServiceModel.getName());
        return courseServiceModel;
    }

    @Override
    public CourseServiceModel enrollCourse(CourseServiceModel courseServiceModel, UserServiceModel userServiceModel) {

        Course course = courseRepository.findById(courseServiceModel.getId())
                .orElseThrow(() -> new CourseNotFoundException("Course with given id does not exist"));

        User user = this.modelMapper.map(userServiceModel, User.class);

        List<User> studentInCourse = course.getEnrolledStudents();
        studentInCourse.add(user);
        course.setEnrolledStudents(studentInCourse);

        Course updatedCourse = this.courseRepository.save(course);

        return this.modelMapper.map(updatedCourse, CourseServiceModel.class);
    }

    @Override
    public boolean checkIfCourseContainStudent(CourseServiceModel courseServiceModel, UserServiceModel user) {
        for (UserServiceModel enrolledStudent : courseServiceModel.getEnrolledStudents()) {
            if (enrolledStudent.getUsername().equals(user.getUsername())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean checkIfUserIsGraduated(CourseServiceModel courseServiceModel, UserServiceModel user) {

        for (UserServiceModel enrolledStudent : courseServiceModel.getGraduatedStudents()) {
            if (enrolledStudent.getUsername().equals(user.getUsername())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<LectureServiceModel> findAllLecturesForCourse(String id) {

        Course course = this.courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course with given id does not exist"));

        return course.getLectures()
                .stream()
                .map(lecture -> this.modelMapper.map(lecture, LectureServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public Course findById(String id) {
        return this.courseRepository.findById(id).orElseThrow(()-> new CourseNotFoundException());
    }

    @Override
    public Page<Course> findCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }


}
