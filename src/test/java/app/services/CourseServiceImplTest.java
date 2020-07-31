package app.services;

import app.models.entity.Course;
import app.models.entity.Difficulty;
import app.models.entity.Topic;
import app.models.entity.User;
import app.models.service.CourseServiceModel;
import app.models.service.TopicServiceModel;
import app.models.service.UserServiceModel;
import app.repositories.CourseRepository;
import app.services.impl.CourseServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static app.models.entity.Difficulty.ADVANCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CourseServiceImplTest {
    private static final String VALID_ID = "validId";
    private static final String VALID_TITLE = "Java Master Class";
    private static final String VALID_SHORT_DESCRIPTION = "Curabitur cursus elementum nibh quis dignissim. Mauris tristique, tortor vel auctor elementum, lorem urna sed.";
    private static final String VALID_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis nisi lectus, elementum in accumsan nec, porttitor vitae lacus. Nunc tempor sodales sem vitae porttitor. Nullam id iaculis sapien. Nunc non porttitor sapien, sed elementum sem. Suspendisse elementum hendrerit neque, vitae luctus ex interdum sit amet. Morbi eu congue arcu, sed fringilla dolor. Donec pharetra sem et turpis consectetur quis.";
    private static final String VALID_COURSE_PHOTO = "http://res.cloudinary.com/devuni/image/upload/v1596146535/fw3l6nm3rrddebrucxfj.jpg";
    private static final double VALID_PASS_PERCENTAGE = 100.0;
    private static final int VALID_DURATION_WEEKS = 10;
    private static final boolean VALID_STATUS = false;
    private static final int VALID_COURSE_RATING = 10;
    private static final Difficulty VALID_COURSE_DIFFICULTY = ADVANCE;
    private static final String VALID_AUTHOR_ID = "validUserId";
    private static final String VALID_AUTHOR_USERNAME = "rusevrado";
    private static final String VALID_TOPIC_ID = "topicId";
    private static final String VALID_TOPIC_NAME = "Java";

    private Course course;
    private CourseServiceModel courseServiceModel;
    private User author;
    private UserServiceModel userServiceModel;
    private Topic topic;
    private TopicServiceModel topicServiceModel;

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private UserService userService;
    @Mock
    private TopicService topicService;
    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CourseServiceImpl courseService;


    @Before
    public void setUp() {
        ModelMapper actualMapper = new ModelMapper();

        when(modelMapper.map(any(UserServiceModel.class), eq(User.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], User.class));

        when(modelMapper.map(any(User.class), eq(UserServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], UserServiceModel.class));

        when(modelMapper.map(any(Course.class), eq(CourseServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], CourseServiceModel.class));

        when(modelMapper.map(any(CourseServiceModel.class), eq(Course.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], Course.class));

        course = getCourse();
        courseServiceModel = getCourseServiceModel(actualMapper);
    }

    @Test
    public void createCourse() throws IOException {
        System.out.println();
        //Arrange
        when(userService.findByName(VALID_AUTHOR_USERNAME)).thenReturn(userServiceModel);
        when(topicService.findTopicByName(VALID_TOPIC_NAME)).thenReturn(topic);
        when(courseRepository.findById(VALID_ID)).thenReturn(Optional.of(course));

        //Act
        CourseServiceModel courseServiceModel = courseService.createCourse(VALID_AUTHOR_USERNAME, this.courseServiceModel);

        String actual = VALID_ID;
        String expected = courseServiceModel.getId();

        assertEquals(actual, expected);

    }

    private CourseServiceModel getCourseServiceModel(ModelMapper actualMapper) {
        CourseServiceModel courseServiceModel = actualMapper.map(course, CourseServiceModel.class);
        userServiceModel =actualMapper.map(author, UserServiceModel.class);
        topicServiceModel = actualMapper.map(topic, TopicServiceModel.class);
        courseServiceModel.setAuthor(userServiceModel);
        courseServiceModel.setTopic(topicServiceModel.getName());
        courseServiceModel.setStartedOn(LocalDateTime.now());
        return courseServiceModel;
    }

    private Course getCourse() {
        Course course = new Course();
        course.setId(VALID_ID);
        course.setTitle(VALID_TITLE);
        course.setShortDescription(VALID_SHORT_DESCRIPTION);
        course.setDescription(VALID_DESCRIPTION);
        course.setCoursePhoto(VALID_COURSE_PHOTO);
        course.setPassPercentage(VALID_PASS_PERCENTAGE);
        course.setDurationWeeks(VALID_DURATION_WEEKS);
        course.setStatus(VALID_STATUS);
        course.setCourseRating(VALID_COURSE_RATING);
        course.setDifficulty(VALID_COURSE_DIFFICULTY);
        author = new User();
        author.setId(VALID_AUTHOR_ID);
        author.setUsername(VALID_AUTHOR_USERNAME);
        author.setCreatedCourses(new HashSet<>());
        course.setAuthor(author);
        topic = new Topic();
        topic.setId(VALID_TOPIC_ID);
        topic.setName(VALID_TOPIC_NAME);
        topic.setCourses(new ArrayList<>());
        course.setTopic(topic);
        return course;
    }
}