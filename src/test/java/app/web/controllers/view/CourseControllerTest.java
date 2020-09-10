package app.web.controllers.view;

import app.models.entity.*;
import app.models.service.*;
import app.models.view.AboutMeViewModel;
import app.models.view.UserDetailsViewModel;
import app.repositories.*;
import app.services.impl.*;
import com.google.common.net.MediaType;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class CourseControllerTest {
    private static final String TOPIC_C_SHARP = "C#";
    private static final String TOPIC_JAVA = "Java";
    private static final String TOPIC_JAVASCRIPT = "JavaScript";
    private static final String TOPIC_PYTHON = "Python";
    private static final String TOPIC_PHP = "Php";
    private static final String TOPIC_CPP = "C++";


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
    private static final String VALID_AUTHOR_USERNAME = "rusevrado";
    private static final String VALID_TOPIC_NAME = "Java";

    private String VALID_ID;
    private static final String VALID_TITLE = "Spring Security";
    private static final String VALID_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis et ullamcorper massa. Morbi accumsan quis lacus eu bibendum. Donec elementum erat curae.";
    private static final String VALID_VIDEO_URL = "https://www.youtube.com/watch?v=xRE12Y-PFQs";

    private String VALID_USER_ID;
    private static final String VALID_USERNAME = "rusevrado";
    private static final String VALID_PASSWORD = "1234";
    private static final String VALID_FIRST_NAME = "Radoslav";
    private static final String VALID_LAST_NAME = "Rusev";
    private static final String VALID_EMAIL = "radorusevcrypto@gmail.com";

    private static final String VALID_IMAGE_URL = "[random_url]";
    private Role teacher;
    private Role student;
    private Role root_admin;
    private AboutMe aboutMe;
    private CustomFile dbFile;
    private CustomFile customFile;
    private Lecture lecture;
    private Course course;
    private User author;

    private Topic cSharp;
    private Topic java;
    private Topic javaScript;
    private Topic python;
    private Topic php;
    private Topic cpp;
    private UserServiceModel userServiceModel;
    private CourseServiceModel courseServiceModel;
    private LectureServiceModel lectureServiceModel;
    private AboutMeServiceModel aboutMeServiceModel;
    private RoleServiceModel roleServiceModel;
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
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private RoleRepository roleRepository;
    private MockMvc mockMvc;

    private ModelMapper modelMapper;

    @MockBean
    private LectureServiceImpl lectureService;
    @MockBean
    private CourseServiceImpl courseService;
    @MockBean
    private TopicServiceImpl topicService;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private CloudinaryServiceImpl cloudinaryService;

    @BeforeEach
    public void setUp() {
        cSharp = this.topicRepository.save(new Topic(TOPIC_C_SHARP));
        java = this.topicRepository.save(new Topic(TOPIC_JAVA));
        javaScript = this.topicRepository.save(new Topic(TOPIC_JAVASCRIPT));
        python = this.topicRepository.save(new Topic(TOPIC_PYTHON));
        php = this.topicRepository.save(new Topic(TOPIC_PHP));
        cpp = this.topicRepository.save(new Topic(TOPIC_CPP));
        teacher = roleRepository.findFirstByAuthority("ROLE_TEACHER").orElse(null);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        modelMapper = new ModelMapper();


        dbFile = new CustomFile();
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
        VALID_ID = lecture.getId();

        author = this.userRepository.save(getAuthor());
        VALID_USER_ID = this.author.getId();
        userServiceModel = modelMapper.map(this.author, UserServiceModel.class);
        course = getCourse();
        course.setEnrolledStudents(List.of(this.author));
        course = courseRepository.save(course);

        courseServiceModel = modelMapper.map(course, CourseServiceModel.class);
        courseServiceModel.setEnrolledStudents(List.of(userServiceModel));

        VALID_COURSE_ID = this.course.getId();


        lectureServiceModel = modelMapper.map(lecture, LectureServiceModel.class);
        lectureServiceModel.setCourse(courseServiceModel);
        AssignmentServiceModel assignmentServiceModel = Mockito.mock(AssignmentServiceModel.class);
        lectureServiceModel.setStudentsAssignmentSolutions(List.of(assignmentServiceModel));


        aboutMe = new AboutMe();
        aboutMe.setSelfDescription(VALID_ABOUT_ME_DESCRIPTION);
        aboutMe.setKnowledgeLevel(VALID_KNOWLEDGE_LEVEL);
        aboutMe = aboutMeRepository.save(aboutMe);
        aboutMeServiceModel = modelMapper.map(aboutMe, AboutMeServiceModel.class);
        author.setAboutMe(aboutMe);

        roleServiceModel = this.modelMapper.map(teacher, RoleServiceModel.class);
        this.userServiceModel = modelMapper.map(this.author, UserServiceModel.class);
        this.userServiceModel.setAboutMeServiceModel(aboutMeServiceModel);
        this.userServiceModel.setAuthorities(Set.of(roleServiceModel));

    }

    @AfterEach
    void tearDown() {
        this.lectureRepository.deleteAll();
        this.courseRepository.deleteAll();
        this.userRepository.deleteAll();
        this.customFileRepository.deleteAll();
        this.aboutMeRepository.deleteAll();
        this.topicRepository.deleteAll();
        this.roleRepository.deleteAll();
    }


    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "TEACHER")
    public void createCourse() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(this.topicService.getAllTopicNames()).thenReturn(List.of(TOPIC_C_SHARP, TOPIC_JAVA, TOPIC_JAVASCRIPT, TOPIC_PYTHON, TOPIC_PHP, TOPIC_CPP));

        this.mockMvc.perform(get("/courses/create")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("courses/create-course"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "TEACHER")
    public void createCourseConfirm() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        MockMultipartFile coursePicture = new MockMultipartFile("coursePicture",
                "hello.png",
                String.valueOf(MediaType.ANY_IMAGE_TYPE),
                "Hello, World!".getBytes());

        when(cloudinaryService.uploadImage(coursePicture)).thenReturn(VALID_COURSE_PHOTO);
        when(courseService.createCourse(userServiceModel.getUsername(), courseServiceModel)).thenReturn(courseServiceModel);

        this.mockMvc.perform(multipart("/courses/create")

                .file(coursePicture)
                .param("title", VALID_TITLE)
                .param("shortDescription", VALID_SHORT_DESCRIPTION)
                .param("description", VALID_DESCRIPTION)
                .param("passPercentage", String.valueOf(VALID_PASS_PERCENTAGE))
                .param("startedOn", String.valueOf(LocalDateTime.now()))
                .param("durationWeeks", String.valueOf(VALID_DURATION_WEEKS))
                .param("topic", VALID_TOPIC_NAME)
                .param("difficulty", String.valueOf(VALID_COURSE_DIFFICULTY))
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/courses/create"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "TEACHER")
    public void teacherCourses() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);
        Pageable pageable = PageRequest.of(0, 4);
        Page<Course> page = new PageImpl<>(List.of(course), pageable, 4);

        when(this.userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        when(courseService.findCoursesByAuthorIdPage(userServiceModel.getId(), pageable)).thenReturn(page);

        this.mockMvc.perform(get("/courses/teacher-courses")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("courses/teacher-courses"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "TEACHER")
    void teacherCheckAssignmentCourses() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        when(courseService.findAllCoursesByAuthorId(userServiceModel.getId())).thenReturn(List.of(courseServiceModel));
        this.mockMvc.perform(get("/courses/teacher-check-courses")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("courses/teacher-all-courses-table"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "STUDENT")
    void studentCourses() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        Pageable pageable = PageRequest.of(0, 4);
        Page<Course> page = new PageImpl<>(List.of(course), pageable, 4);

        when(this.userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        when(courseService.findAllEnrolledCoursesPage(userServiceModel.getId(), pageable)).thenReturn(page);
        this.mockMvc.perform(get("/courses/enrolledCourses")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("courses/student-enrolled-courses"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "STUDENT")
    public void completedCourses() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        Pageable pageable = PageRequest.of(0, 4);
        Page<Course> page = new PageImpl<>(List.of(course), pageable, 4);

        when(this.userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        when(courseService.findAllCompletedCourses(userServiceModel.getId(), pageable)).thenReturn(page);
        this.mockMvc.perform(get("/courses/completedCourses")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("courses/student-completed-courses"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "STUDENT")
    public void allCourses() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        Pageable pageable = PageRequest.of(0, 4);
        Page<Course> page = new PageImpl<>(List.of(course), pageable, 4);

        when(courseService.findAllCoursesPage(pageable)).thenReturn(page);
        this.mockMvc.perform(get("/courses/allCourses")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("courses/all-courses"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "STUDENT")
    public void allCoursesWithValidSearchParam() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        Pageable pageable = PageRequest.of(0, 4);
        Page<Course> page = new PageImpl<>(List.of(course), pageable, 4);

        when(courseService.findAllBySearch("Java", pageable)).thenReturn(page);
        this.mockMvc.perform(get("/courses/allCourses?search=Java")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("courses/all-courses"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "STUDENT")
    public void allCoursesInTopic() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        Pageable pageable = PageRequest.of(0, 4);
        Page<Course> page = new PageImpl<>(List.of(course), pageable, 4);

        when(courseService.findAllCoursesInTopicPage(java.getId(), pageable)).thenReturn(page);
        this.mockMvc.perform(get("/courses/allCoursesInTopic/{id}", java.getId())
                .session(session)
                .with(csrf()))
                .andExpect(view().name("courses/all-courses-by-topic"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "TEACHER")
    public void courseDetails() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);
        UserServiceModel author = courseServiceModel.getAuthor();
        AboutMeViewModel aboutMeViewModel = this.modelMapper.map(aboutMeServiceModel, AboutMeViewModel.class);
        UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(author, UserDetailsViewModel.class);
        System.out.println();
        userDetailsViewModel.setAboutMeViewModel(aboutMeViewModel);

        when(courseService.findCourseById(VALID_COURSE_ID)).thenReturn(courseServiceModel);
        when(userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        when(userService.findTeacher(courseServiceModel.getAuthor().getId())).thenReturn(userDetailsViewModel);

        this.mockMvc.perform(get("/courses/courseDetails/{id}", VALID_COURSE_ID)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("courses/course-details"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "TEACHER")
    void enrollCourse() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(courseService.findCourseById(VALID_COURSE_ID)).thenReturn(courseServiceModel);
        when(userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        when(courseService.enrollCourse(courseServiceModel,userServiceModel)).thenReturn(courseServiceModel);

        this.mockMvc.perform(get("/courses/enrollCourse/{id}", VALID_COURSE_ID)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/courses/courseDetails/" + VALID_COURSE_ID));
    }

    private Course getCourse() {
        Course course = new Course();
        course.setTitle(VALID_COURSE_TITLE);
        course.setShortDescription(VALID_DESCRIPTION);
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
        author.setCreatedCourses(new HashSet<>());
        course.setEnrolledStudents(new ArrayList<>());
        author.setAuthorities(Set.of(teacher));
        course.setAuthor(author);
        topicRepository.findFirstByName(VALID_TOPIC_NAME).orElse(null);
        return course;
    }

    private User getAuthor() {
        User user = new User();
        user.setFirstName(VALID_FIRST_NAME);
        user.setLastName(VALID_LAST_NAME);
        user.setUsername("validAuthor");
        user.setEmail("validauthoremail@gmail.com");
        user.setPassword(VALID_PASSWORD);
        user.setAuthorities(Set.of(teacher));
        user.setRegistrationDate(LocalDateTime.now());
        user.setProfilePicture(VALID_IMAGE_URL);
        return user;
    }
}