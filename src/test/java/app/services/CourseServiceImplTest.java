package app.services;

import app.error.CourseNotFoundException;
import app.models.entity.*;
import app.models.service.CourseServiceModel;
import app.models.service.LectureServiceModel;
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
import java.time.temporal.ChronoUnit;
import java.util.*;

import static app.models.entity.Difficulty.ADVANCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceImplTest {
    private static final String VALID_ID = "validId";
    private static final String VALID_NEW_ID = "validId";
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
    private Course course2;
    private CourseServiceModel courseServiceModel2;
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
        when(modelMapper.map(any(TopicServiceModel.class), eq(Topic.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], Topic.class));

        when(modelMapper.map(any(Topic.class), eq(TopicServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], TopicServiceModel.class));

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

        course = getCourse1();
        course2 = getCourse2();
        courseServiceModel = getCourseServiceModel(actualMapper);
        courseServiceModel2 = getCourseServiceModel(actualMapper);
    }

    @Test
    public void createCourse() throws IOException {

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

    @Test
    public void findAllCoursesByAuthorId_shouldReturnCoursesByAuthorID() {
        //Arrange
        when(courseRepository.findAllCoursesByAuthorId(VALID_AUTHOR_ID)).thenReturn(Arrays.asList(course));

        //Act
        List<CourseServiceModel> allCoursesByAuthorId = this.courseService.findAllCoursesByAuthorId(VALID_AUTHOR_ID);

        int actual = 1;
        int expected = allCoursesByAuthorId.size();
        assertEquals(actual, expected);
    }

    @Test
    public void findTopThreeRecentCourses_shouldReturnTopThreeRecentCourses() {
        //Arrange
        course2.setStartedOn(LocalDateTime.now().plus(VALID_DURATION_WEEKS * 7, ChronoUnit.DAYS));

        // findRecentCourses() method compare Courses by StartedON date
        when(courseRepository.findRecentCourses()).thenReturn(Arrays.asList(course2, course));
        //Act
        List<CourseServiceModel> recentCourses = this.courseService.findRecentCourses();

        //Assert
        assertEquals(recentCourses.get(0).getTitle(), course2.getTitle());
        assertEquals(recentCourses.get(1).getTitle(), course.getTitle());

    }

    @Test
    public void getAllCourses_ShouldReturnAllCourses() {
        //Arrange
        course2.setStartedOn(LocalDateTime.now().plus(VALID_DURATION_WEEKS * 7, ChronoUnit.DAYS));
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course, course2));
        //Act
        List<CourseServiceModel> allCourses = this.courseService.getAllCourses();
        //Assert
        assertEquals(allCourses.get(0).getTitle(), course.getTitle());
        assertEquals(allCourses.get(1).getTitle(), course2.getTitle());

    }

    @Test
    public void enableCourse_ShouldReturnCourseWithStatusTrue() {
        //Arrange
        course.setStatus(true);
        when(courseRepository.findById(VALID_ID)).thenReturn(Optional.of(course));
        //Act
        CourseServiceModel courseServiceModel = courseService.enableCourse(course.getId());
        //Assert
        assertEquals(true, courseServiceModel.isStatus());

    }

    @Test(expected = CourseNotFoundException.class)
    public void enableCourse_ShouldThrowExceptionWhenCourseDoesNotExist() {
        //Arrange
        course.setStatus(true);
        when(courseRepository.findById(VALID_ID)).thenReturn(Optional.empty());
        //Act
        CourseServiceModel courseServiceModel = courseService.enableCourse(course.getId());


    }

    @Test
    public void disableCourse_ShouldReturnCourseWithStatusFalse() {
        //Arrange
        course.setStatus(false);
        when(courseRepository.findById(VALID_ID)).thenReturn(Optional.of(course));
        //Act
        CourseServiceModel courseServiceModel = courseService.enableCourse(course.getId());
        //Assert
        assertEquals(false, courseServiceModel.isStatus());

    }

    @Test(expected = CourseNotFoundException.class)
    public void disableCourse_ShouldThrowExceptionWhenCourseDoesNotExist() {
        //Arrange
        course.setStatus(false);
        when(courseRepository.findById(VALID_ID)).thenReturn(Optional.empty());
        //Act
        CourseServiceModel courseServiceModel = courseService.disableCourse(course.getId());


    }

    @Test
    public void findAllCoursesWithStatusTrue_ShouldReturnAllCourses() {
        //Arrange
        course2.setStartedOn(LocalDateTime.now().plus(VALID_DURATION_WEEKS * 7, ChronoUnit.DAYS));
        course.setStatus(true);

        when(courseRepository.findAllCoursesWithStatusTrue()).thenReturn(Arrays.asList(course2));
        //Act
        List<CourseServiceModel> allCourses = this.courseService.findAllCoursesWithStatusTrue();
        //Assert
        assertEquals(allCourses.get(0).getTitle(), course2.getTitle());

    }

    @Test
    public void findAllCoursesInTopic_ShouldReturnAllCoursesInTopic() {
        //Arrange
        course.setStartedOn(LocalDateTime.now().plus(VALID_DURATION_WEEKS * 7, ChronoUnit.DAYS));
        when(courseRepository.findAllCoursesInTopic(VALID_TOPIC_ID)).thenReturn(Arrays.asList(course));
        //Act
        List<CourseServiceModel> allCourses = this.courseService.findAllCoursesInTopic(VALID_TOPIC_ID);

        String actual = VALID_TITLE;
        String expected = allCourses.get(0).getTitle();
        //Assert
        assertEquals(actual, expected);

    }

    @Test
    public void findCourseById_shouldReturnCourseIfExist() {
        //Arrange
        when(courseRepository.findById(VALID_ID)).thenReturn(Optional.of(course));

        //Act
        CourseServiceModel courseServiceModel = this.courseService.findCourseById(VALID_ID);

        String actual = VALID_ID;
        String expected = courseServiceModel.getId();

        assertEquals(actual, expected);
    }

    @Test(expected = CourseNotFoundException.class)
    public void findCourseById_shouldThrowExceptionIfCourseDoesNotExist() {
        //Arrange
        when(courseRepository.findById(VALID_ID)).thenReturn(Optional.empty());

        //Act
        CourseServiceModel courseServiceModel = this.courseService.findCourseById(VALID_ID);

    }

    @Test
    public void enrollCourse_shouldAddStudentToEnrolledStudentsList() {

        course.setEnrolledStudents(new ArrayList<>());
        //Arrange
        when(courseRepository.findById(VALID_ID)).thenReturn(Optional.of(course));
        when(courseRepository.save(course)).thenReturn(course);

        //Act
        CourseServiceModel courseServiceModel = this.courseService.enrollCourse(this.courseServiceModel, userServiceModel);

        //Assert
        String actual = userServiceModel.getUsername();
        String expected = courseServiceModel.getEnrolledStudents().get(0).getUsername();

        assertEquals(actual, expected);
    }

    @Test(expected = CourseNotFoundException.class)
    public void enrollCourse_shouldThrowCourseNotFoundException() {
        //Arrange
        when(courseRepository.findById(VALID_ID)).thenReturn(Optional.empty());

        //Act
        CourseServiceModel courseServiceModel = this.courseService.enrollCourse(this.courseServiceModel, userServiceModel);

    }

    @Test
    public void checkIfCourseContainStudent_shouldReturnTrueIfStudentExist() {
        //Arrange
        List<UserServiceModel> enrolledStudents = new ArrayList<>();
        enrolledStudents.add(userServiceModel);
        courseServiceModel.setEnrolledStudents(enrolledStudents);
        //Act
        boolean courseServiceModel = this.courseService.checkIfCourseContainStudent(this.courseServiceModel, userServiceModel);

        //Assert
        boolean actual = true;
        boolean expected = courseServiceModel;

        assertEquals(actual, expected);
    }

    @Test
    public void checkIfCourseContainStudent_shouldReturnFalseIfStudentDoesNotExist() {
        //Arrange
        List<UserServiceModel> enrolledStudents = new ArrayList<>();
        courseServiceModel.setEnrolledStudents(enrolledStudents);
        //Act
        boolean courseServiceModel = this.courseService.checkIfCourseContainStudent(this.courseServiceModel, userServiceModel);

        //Assert
        boolean actual = false;
        boolean expected = courseServiceModel;

        assertEquals(actual, expected);
    }

    @Test
    public void findAllLecturesForCourse_ShouldReturnAllLecturesInCourse() {
        //Arrange
        Lecture lecture = new Lecture();
        lecture.setId("validLectureId");
        lecture.setTitle("Spring Security");
        lecture.setDescription(VALID_DESCRIPTION);
        lecture.setLectureVideoUrl("https://www.youtube.com/watch?v=xRE12Y-PFQs");
        ArrayList<Lecture> lectures = new ArrayList<>();
        lectures.add(lecture);
        course.setLectures(lectures);
        when(courseRepository.findById(VALID_ID)).thenReturn(Optional.of(course));

        //Act
        List<LectureServiceModel> allLecturesForCourse = courseService.findAllLecturesForCourse(VALID_ID);

        //Assert
        int actual = 1;
        int expected = allLecturesForCourse.size();
        assertEquals(actual, expected);

    }
    @Test(expected = CourseNotFoundException.class)
    public void findAllLecturesForCourse_ShouldThrowExceptionIfCourseDoesNotExist() {
        //Arrange
        Lecture lecture = new Lecture();
        lecture.setId("validLectureId");
        lecture.setTitle("Spring Security");
        lecture.setDescription(VALID_DESCRIPTION);
        lecture.setLectureVideoUrl("https://www.youtube.com/watch?v=xRE12Y-PFQs");
        ArrayList<Lecture> lectures = new ArrayList<>();
        lectures.add(lecture);
        course.setLectures(lectures);
        when(courseRepository.findById(VALID_ID)).thenReturn(Optional.of(course));

        //Act
        List<LectureServiceModel> allLecturesForCourse = courseService.findAllLecturesForCourse(VALID_ID);

        //Assert
        int actual = 1;
        int expected = allLecturesForCourse.size();
        assertEquals(actual, expected);

    }

    private CourseServiceModel getCourseServiceModel(ModelMapper actualMapper) {
        CourseServiceModel courseServiceModel = actualMapper.map(course, CourseServiceModel.class);
        userServiceModel = actualMapper.map(author, UserServiceModel.class);
        topicServiceModel = actualMapper.map(topic, TopicServiceModel.class);
        courseServiceModel.setAuthor(userServiceModel);
        courseServiceModel.setTopic(topicServiceModel.getName());
        courseServiceModel.setStartedOn(LocalDateTime.now());
        return courseServiceModel;

    }

    private CourseServiceModel getCourseServiceModel2(ModelMapper actualMapper) {
        CourseServiceModel courseServiceModel = actualMapper.map(course2, CourseServiceModel.class);
        userServiceModel = actualMapper.map(author, UserServiceModel.class);
        topicServiceModel = actualMapper.map(topic, TopicServiceModel.class);
        courseServiceModel.setAuthor(userServiceModel);
        courseServiceModel.setTopic(topicServiceModel.getName());
        courseServiceModel.setStartedOn(LocalDateTime.now());
        return courseServiceModel;
    }


    private Course getCourse1() {
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

    private Course getCourse2() {
        Course course2 = new Course();
        course2.setId(VALID_NEW_ID);
        course2.setTitle(VALID_TITLE);
        course2.setShortDescription(VALID_SHORT_DESCRIPTION);
        course2.setDescription(VALID_DESCRIPTION);
        course2.setCoursePhoto(VALID_COURSE_PHOTO);
        course2.setPassPercentage(VALID_PASS_PERCENTAGE);
        course2.setDurationWeeks(VALID_DURATION_WEEKS);
        course2.setStatus(VALID_STATUS);
        course2.setCourseRating(VALID_COURSE_RATING);
        course2.setDifficulty(VALID_COURSE_DIFFICULTY);
        author = new User();
        author.setId(VALID_AUTHOR_ID);
        author.setUsername(VALID_AUTHOR_USERNAME);
        author.setCreatedCourses(new HashSet<>());
        course2.setAuthor(author);
        topic = new Topic();
        topic.setId(VALID_TOPIC_ID);
        topic.setName(VALID_TOPIC_NAME);
        topic.setCourses(new ArrayList<>());
        course2.setTopic(topic);
        return course2;
    }
}

