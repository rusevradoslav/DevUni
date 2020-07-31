package app.services.impl;

import app.models.entity.Course;
import app.models.entity.Difficulty;
import app.models.entity.Topic;
import app.models.entity.User;
import app.models.service.CourseServiceModel;
import app.repositories.CourseRepository;
import app.services.CloudinaryService;
import app.services.CourseService;
import app.services.TopicService;
import app.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    private final ModelMapper modelMapper;


    @Override
    public CourseServiceModel createCourse(String username, CourseServiceModel courseServiceModel) {
        System.out.println();
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
}
