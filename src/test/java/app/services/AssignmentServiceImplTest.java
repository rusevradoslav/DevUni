package app.services;

import app.error.FileStorageException;
import app.models.entity.*;
import app.models.service.AssignmentServiceModel;
import app.models.service.CourseServiceModel;
import app.models.service.LectureServiceModel;
import app.models.service.UserServiceModel;
import app.repositories.AssignmentRepository;
import app.services.impl.AssignmentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static app.models.entity.Difficulty.ADVANCE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AssignmentServiceImplTest {
    private static final String LECTURE_VALID_ID = "valid_Lecture_Id";
    private static final String LECTURE_VALID_TITLE = "Spring Security";
    private static final String LECTURE_VALID_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis nisi lectus, elementum in accumsan nec, porttitor vitae lacus. Nunc tempor sodales sem vitae porttitor. Nullam id iaculis sapien. Nunc non porttitor sapien, sed elementum sem. Suspendisse elementum hendrerit neque, vitae luctus ex interdum sit amet. Morbi eu congue arcu, sed fringilla dolor. Donec pharetra sem et turpis consectetur quis.";
    private static final String LECTURE_VALID_VIDEO_URL = "https://www.youtube.com/watch?v=xRE12Y-PFQs";

    private static final String VALID_COURSE_ID = "validCourseId";
    private static final String VALID_CUSTOM_FILE_ID = "VALID_CUSTOM_FILE_ID";
    private static final String VALID_COURSE_TITLE = "Java Master Class";
    private static final String VALID_SHORT_DESCRIPTION = "Curabitur cursus elementum nibh quis dignissim. Mauris tristique, tortor vel auctor elementum, lorem urna sed.";
    private static final String VALID_COURSE_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis nisi lectus, elementum in accumsan nec, porttitor vitae lacus. Nunc tempor sodales sem vitae porttitor. Nullam id iaculis sapien. Nunc non porttitor sapien, sed elementum sem. Suspendisse elementum hendrerit neque, vitae luctus ex interdum sit amet. Morbi eu congue arcu, sed fringilla dolor. Donec pharetra sem et turpis consectetur quis.";
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

    private static final String VALID_ASSIGNMENT_DESCRIPTION = "Description: radorusev Spring Security file";
    private static final String VALID_ASSIGNMENT_ID = "validId";

    private final String VALID_FILE_NAME = "validFileName.txt";
    private final String VALID_FILE_TYPE = "validFileType";
    private final byte[] VALID_DATA = new byte[]{11, 11};

    private User user;
    private Lecture lecture;
    private CustomFile dbFile;
    private Assignment assignment;
    private AssignmentServiceModel assignmentServiceModel;
    private UserServiceModel userServiceModel;
    private LectureServiceModel lectureServiceModel;

    @Mock
    private AssignmentRepository assignmentRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private CustomFileService customFileService;
    @Mock
    private UserService userService;

    @InjectMocks
    private AssignmentServiceImpl assignmentService;
    private Course course;


    @Before
    public void setUp() {
        ModelMapper actualMapper = new ModelMapper();
        dbFile = new CustomFile();
        dbFile.setId("VALID_CUSTOM_FILE_ID");
        dbFile.setData(new byte[]{11, 11});
        ;
        lecture = new Lecture();
        lecture.setId(LECTURE_VALID_ID);
        lecture.setTitle(LECTURE_VALID_TITLE);
        lecture.setDescription(LECTURE_VALID_DESCRIPTION);
        lecture.setLectureVideoUrl(LECTURE_VALID_VIDEO_URL);
        lecture.setResources(dbFile);
        lecture.setCourse(getCourse());

        user = new User();
        user.setId(VALID_AUTHOR_ID);
        user.setUsername(VALID_AUTHOR_USERNAME);
        user.setFirstName("RADOSLAV");
        user.setLastName("RUSEV");
        user.setEmail("radorusevcrypto@gmail.com");
        user.setPassword("VALID_PASSWORD");
        user.setRegistrationDate(LocalDateTime.now());

        assignment = new Assignment();
        assignment.setId(VALID_ASSIGNMENT_ID);
        assignment.setLecture(lecture);
        assignment.setUser(user);
        assignment.setFile(dbFile);
        assignment.setDescription(VALID_ASSIGNMENT_DESCRIPTION);
        assignment.setChecked(true);

        when(modelMapper.map(any(AssignmentServiceModel.class), eq(Assignment.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], Assignment.class));

        when(modelMapper.map(any(Assignment.class), eq(AssignmentServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], AssignmentServiceModel.class));


        when(modelMapper.map(any(LectureServiceModel.class), eq(Lecture.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], Lecture.class));

        when(modelMapper.map(any(UserServiceModel.class), eq(User.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], User.class));

        when(modelMapper.map(any(CourseServiceModel.class), eq(Course.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], Course.class));

        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        when(multipartFile.getName()).thenReturn("file");
        assignmentServiceModel = this.modelMapper.map(assignment, AssignmentServiceModel.class);
        assignmentServiceModel.setFile(multipartFile);
        assignmentServiceModel.setChecked(true);
        userServiceModel = actualMapper.map(user, UserServiceModel.class);
        lectureServiceModel = actualMapper.map(lecture, LectureServiceModel.class);

    }

    @Test
    public void uploadUserAssignmentSolution_shouldUploadNewAssigment() throws FileStorageException {
        //Arrange
        /* when(assignmentRepository.findFirstByDescription(VALID_ASSIGNMENT_DESCRIPTION)).thenReturn(null);*/
        when(this.assignmentRepository.save(Mockito.any(Assignment.class)))
                .thenReturn(assignment);

        //Act

        AssignmentServiceModel aServiceModel = assignmentService
                .uploadUserAssignmentSolution(lectureServiceModel, userServiceModel, assignmentServiceModel);

        String actual = VALID_ASSIGNMENT_DESCRIPTION;
        String expected = aServiceModel.getDescription();

        assertEquals(actual, expected);


    }

    @Test
    public void findFileForAssignmentById_shouldReturnCorrectFileName() {
        when(assignmentRepository.findById(VALID_ASSIGNMENT_ID)).thenReturn(Optional.of(assignment));

        String fileForAssignmentById = assignmentService.findFileForAssignmentById(assignment.getId());

        String actual = VALID_CUSTOM_FILE_ID;
        String expected = fileForAssignmentById;
        assertEquals(actual, expected);
    }

    @Test
    public void getAssignmentById_shouldReturnCorrectFileName() {
        when(assignmentRepository.findById(VALID_ASSIGNMENT_ID)).thenReturn(Optional.of(assignment));

        AssignmentServiceModel assignmentById = assignmentService.getAssignmentById(assignment.getId());

        String actual = VALID_ASSIGNMENT_ID;
        String expected = assignmentById.getId();
        assertEquals(actual, expected);
    }

    @Test
    public void findAllSubmittedAssignments_shouldReturnCorrectFileName() {
        when(assignmentRepository.findAllByLecture_Id(LECTURE_VALID_ID)).thenReturn(List.of(assignment));

        List<AssignmentServiceModel> allSubmittedAssignments = assignmentService.findAllSubmittedAssignments(lectureServiceModel);

        int actual = actual = 1;
        int expected = allSubmittedAssignments.size();
        assertEquals(actual, expected);
    }

    @Test
    public void checkAssignment_shouldCheckedAssignment() {

        course.setLectures(List.of(lecture));
        when(assignmentRepository.findById(VALID_ASSIGNMENT_ID)).thenReturn(Optional.of(assignment));
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);
        when(userService.findById(user.getId())).thenReturn(userServiceModel);

        AssignmentServiceModel assignment = assignmentService.checkAssignment(assignmentServiceModel);

        String actual = VALID_ASSIGNMENT_DESCRIPTION;
        String expected = assignment.getDescription();
        assertEquals(actual, expected);
    }

    @Test
    public void checkAssignment_shouldCheckedAssignmentAndCallUpdateMethodInUserService() {

        course.setLectures(List.of(lecture));
        when(assignmentRepository.findById(VALID_ASSIGNMENT_ID)).thenReturn(Optional.of(assignment));
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);
        when(assignmentRepository.findAllAssignmentsByUserIdAndCourseId(user.getId(), course.getId())).thenReturn(List.of(assignment));
        when(userService.findById(user.getId())).thenReturn(userServiceModel);

        AssignmentServiceModel assignment = assignmentService.checkAssignment(assignmentServiceModel);

        String actual = VALID_ASSIGNMENT_DESCRIPTION;
        String expected = assignment.getDescription();
        assertEquals(actual, expected);
    }

    @Test
    public void getCheckedAssignmentsByUser_shouldReturnAllCheckedAssignments() {
        //Arrange
        when(assignmentRepository.findAssignmentsByUserId(userServiceModel.getId())).thenReturn(List.of(assignment));
        //Act
        List<AssignmentServiceModel> checkedAssignmentsByUser = this.assignmentService.getCheckedAssignmentsByUser(userServiceModel);

        //Assert
        String actual = VALID_ASSIGNMENT_DESCRIPTION;
        String expected = checkedAssignmentsByUser.stream().findFirst().get().getDescription();

        assertEquals(actual, expected);
    }


    private Course getCourse() {
        course = new Course();
        course.setId(VALID_COURSE_ID);
        course.setTitle(VALID_COURSE_TITLE);
        course.setShortDescription(VALID_SHORT_DESCRIPTION);
        course.setDescription(VALID_COURSE_DESCRIPTION);
        course.setCoursePhoto(VALID_COURSE_PHOTO);
        course.setPassPercentage(VALID_PASS_PERCENTAGE);
        course.setDurationWeeks(VALID_DURATION_WEEKS);
        course.setStatus(VALID_STATUS);
        course.setCourseRating(VALID_COURSE_RATING);
        course.setDifficulty(VALID_COURSE_DIFFICULTY);
        course.setStartedOn(LocalDateTime.now().plusDays(6));
        course.setEndedON(LocalDateTime.now().plusDays(12));
        course.setLectures(new ArrayList<>());
        course.setEnrolledStudents(new ArrayList<>());
        ;
        course.setAuthor(user);
        Topic topic = new Topic();
        topic.setId(VALID_TOPIC_ID);
        topic.setName(VALID_TOPIC_NAME);
        topic.setCourses(new ArrayList<>());
        course.setTopic(topic);
        return course;
    }

}