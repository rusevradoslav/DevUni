package app.web.controllers.view;

import app.models.entity.*;
import app.models.service.*;
import app.repositories.*;
import app.services.impl.CourseServiceImpl;
import app.services.impl.LectureServiceImpl;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static app.models.entity.Difficulty.ADVANCE;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class LectureControllerTest {
    private static String VALID_KNOWLEDGE_LEVEL = "Java Master";
    private static String VALID_ABOUT_ME_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
            " Donec id arcu eu lacus semper gravida vulputate ut " +
            "erat. Mauris ultricies nisl id justo nisi.";


    private String VALID_COURSE_ID;
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

    private String VALID_ID ;
    private static final String VALID_TITLE = "Spring Security";
    private static final String VALID_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis nisi lectus, elementum in accumsan nec, porttitor vitae lacus. Nunc tempor sodales sem vitae porttitor. Nullam id iaculis sapien. Nunc non porttitor sapien, sed elementum sem. Suspendisse elementum hendrerit neque, vitae luctus ex interdum sit amet. Morbi eu congue arcu, sed fringilla dolor. Donec pharetra sem et turpis consectetur quis.";
    private static final String VALID_VIDEO_URL = "https://www.youtube.com/watch?v=xRE12Y-PFQs";

    private static final String VALID_USER_ID = "validId";
    private static final String VALID_USERNAME = "rusevrado";
    private static final String VALID_PASSWORD = "1234";
    private static final String VALID_NEW_PASSWORD = "12345";
    private static final String VALID_FIRST_NAME = "Radoslav";
    private static final String VALID_LAST_NAME = "Rusev";
    private static final String VALID_EMAIL = "radorusevcrypto@gmail.com";
    private static final String VALID_IMAGE_URL = "[random_url]";

    private User author;
    private Topic topic;
    private Lecture lecture;
    private CustomFile customFile;
    private Course course;
    private LectureServiceModel lectureServiceModel;
    private AboutMeServiceModel aboutMeServiceModel;
    private UserServiceModel userServiceModel;
    private CourseServiceModel courseServiceModel;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomFileRepository customFileRepository;
    @Autowired
    private AboutMeRepository aboutMeRepository;


    private MockMvc mockMvc;

    @MockBean
    private LectureServiceImpl lectureService;
    @MockBean
    private CourseServiceImpl courseService;


    @Autowired
    private LectureController lectureController;


    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        ModelMapper modelMapper = new ModelMapper();
        lectureController = new LectureController(courseService, lectureService, modelMapper);


        CustomFile dbFile = new CustomFile();
        dbFile.setId(VALID_CUSTOM_FILE_ID);
        dbFile.setData(new byte[]{11, 11});
        customFile = customFileRepository.save(dbFile);



        lecture = new Lecture();
        lecture.setId(VALID_ID);
        lecture.setTitle(VALID_TITLE);
        lecture.setDescription(VALID_DESCRIPTION);
        lecture.setLectureVideoUrl(VALID_VIDEO_URL);
        lecture.setResources(customFile);


        lecture = lectureRepository.save(lecture);
        VALID_ID= lecture.getId();

        User user = getUser();
        User newUser = userRepository.save(user);
        userServiceModel = modelMapper.map(newUser, UserServiceModel.class);
        course = getCourse();
        course.setEnrolledStudents(List.of(user));

        Course newCourse = courseRepository.save(course);

        courseServiceModel = modelMapper.map(newCourse, CourseServiceModel.class);
        courseServiceModel.setEnrolledStudents(List.of(userServiceModel));

        VALID_COURSE_ID = this.course.getId();


        lectureServiceModel = modelMapper.map(lecture, LectureServiceModel.class);
        AssignmentServiceModel assignmentServiceModel = Mockito.mock(AssignmentServiceModel.class);
        lectureServiceModel.setStudentsAssignmentSolutions(List.of(assignmentServiceModel));


        AboutMe aboutMe = new AboutMe();
        aboutMe.setSelfDescription(VALID_ABOUT_ME_DESCRIPTION);
        aboutMe.setKnowledgeLevel(VALID_KNOWLEDGE_LEVEL);
        AboutMe save = aboutMeRepository.save(aboutMe);
        aboutMeServiceModel = modelMapper.map(save, AboutMeServiceModel.class);
        author.setAboutMe(aboutMe);

        this.userServiceModel = modelMapper.map(author, UserServiceModel.class);
        this.userServiceModel.setAboutMeServiceModel(aboutMeServiceModel);


    }

    @After
    public void tearDown() {
        this.courseRepository.deleteAll();
        this.lectureRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "TEACHER")
    public void teacherCheckAssignmentCourses() throws Exception {

        MockHttpSession session = new MockHttpSession();
        when(courseService.findAllLecturesForCourse(VALID_COURSE_ID)).thenReturn(List.of(lectureServiceModel));

        session.setAttribute("user", userServiceModel);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/lectures/course-check-lectures/{id}", VALID_COURSE_ID)
                .session(session);
        this.mockMvc.perform(builder
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(view().name("courses/course-all-lectures-table"));

    }
    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "TEACHER")
    public void watchLectureVideo() throws Exception {

        MockHttpSession session = new MockHttpSession();
        when(lectureService.findLectureById(VALID_ID)).thenReturn(lectureServiceModel);

        session.setAttribute("user", userServiceModel);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/lectures/lectureVideo/{id}",VALID_ID )
                .session(session);
        System.out.println();
        this.mockMvc.perform(builder
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(view().name("lectures/lecture-video"));

    }

    private Course getCourse() {
        Course course = new Course();
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
        author = getUser();
        userRepository.save(author);
        author.setCreatedCourses(new HashSet<>());
        course.setEnrolledStudents(new ArrayList<>());
        Role role = new Role("ROLE_TEACHER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        author.setAuthorities(roles);
        course.setAuthor(author);
        topic = new Topic();
        topic.setId(VALID_TOPIC_ID);
        topic.setName(VALID_TOPIC_NAME);
        topic.setCourses(new ArrayList<>());
        course.setTopic(topic);
        return course;
    }

    private User getUser() {
        User user = new User();
        user.setFirstName(VALID_FIRST_NAME);
        user.setLastName(VALID_LAST_NAME);
        user.setUsername(VALID_USERNAME);
        user.setEmail(VALID_EMAIL);
        user.setPassword(VALID_PASSWORD);
        Set<Role> roles = new HashSet<>();
        Role role = new Role("ROLE_ADMIN");
        roles.add(role);
        user.setAuthorities(roles);
        user.setRegistrationDate(LocalDateTime.now());
        user.setProfilePicture(VALID_IMAGE_URL);
        return user;
    }

}