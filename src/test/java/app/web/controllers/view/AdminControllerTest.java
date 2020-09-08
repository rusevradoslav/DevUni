package app.web.controllers.view;

import app.models.entity.*;
import app.models.service.*;
import app.models.view.AboutMeViewModel;
import app.models.view.UserDetailsViewModel;
import app.repositories.*;
import app.services.impl.CourseServiceImpl;
import app.services.impl.RoleServiceImpl;
import app.services.impl.TopicServiceImpl;
import app.services.impl.UserServiceImpl;
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
import org.springframework.mock.web.MockHttpSession;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class AdminControllerTest {
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
    private Role admin;
    private Role teacher;
    private Role student;
    private Role root_admin;
    private AboutMe aboutMe;
    private CustomFile dbFile;
    private CustomFile customFile;
    private Lecture lecture;
    private Course course;
    private User user;
    private User author;
    private Topic topic;
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

    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private CourseServiceImpl courseService;
    @MockBean
    private TopicServiceImpl topicService;
    @MockBean
    private RoleServiceImpl roleService;

    private ModelMapper modelMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        admin = roleRepository.save(new Role("ROLE_ADMIN"));
        teacher = roleRepository.save(new Role("ROLE_TEACHER"));
        student = roleRepository.save(new Role("ROLE_STUDENT"));
        root_admin = roleRepository.save(new Role("ROLE_ROOT_ADMIN"));
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


        user = userRepository.save(getUser());
        VALID_USER_ID = user.getId();
        userServiceModel = modelMapper.map(user, UserServiceModel.class);
        course = getCourse();
        course.setEnrolledStudents(List.of(user));
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

        roleServiceModel = this.modelMapper.map(admin, RoleServiceModel.class);
        this.userServiceModel = modelMapper.map(user, UserServiceModel.class);
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
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ADMIN")
    public void adminHomePage() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);
        UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(userServiceModel, UserDetailsViewModel.class);
        AboutMeViewModel aboutMeViewModel = this.modelMapper.map(aboutMe, AboutMeViewModel.class);
        userDetailsViewModel.setAboutMeViewModel(aboutMeViewModel);
        when(this.userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        when(this.userService.findAllAdmins()).thenReturn(List.of(userDetailsViewModel));

        this.mockMvc.perform(get("/admins/home-page")
                .session(session)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admins/admin-home"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ADMIN")
    public void allAdmins() throws Exception {
        this.mockMvc.perform(get("/admins/all-admins")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admins/root-all-admins"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ROOT_ADMIN")
    public void blockAdminProfile() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(this.userService.findById(VALID_USER_ID)).thenReturn(userServiceModel);
        userServiceModel.setStatus(false);
        when(this.userService.blockUser(userServiceModel)).thenReturn(userServiceModel);

        this.mockMvc.perform(get("/admins/block-admin/{id}", VALID_USER_ID)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/admins/all-admins"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ROOT_ADMIN")
    public void activateAdminProfile() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(this.userService.findById(VALID_USER_ID)).thenReturn(userServiceModel);

        when(this.userService.activateUser(userServiceModel)).thenReturn(userServiceModel);

        this.mockMvc.perform(get("/admins/activate-admin/{id}", VALID_USER_ID)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/admins/all-admins"));
    }


    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ROOT_ADMIN")
    public void demoteAdminToStudent() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(this.userService.findById(VALID_USER_ID)).thenReturn(userServiceModel);

        RoleServiceModel roleServiceModel = this.modelMapper.map(root_admin, RoleServiceModel.class);
        this.userServiceModel.setAuthorities(Set.of(roleServiceModel));
        when(this.userService.changeRoleToStudent(userServiceModel)).thenReturn(userServiceModel);

        this.mockMvc.perform(get("/admins/demote-admin-student/{id}", VALID_USER_ID)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/admins/all-admins"));
    }


    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ROOT_ADMIN")
    public void demoteAdminToTeacher() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(this.userService.findById(VALID_USER_ID)).thenReturn(userServiceModel);

        RoleServiceModel roleServiceModel = this.modelMapper.map(teacher, RoleServiceModel.class);
        this.userServiceModel.setAuthorities(Set.of(roleServiceModel));
        when(this.userService.changeRoleToTeacher(userServiceModel)).thenReturn(userServiceModel);

        this.mockMvc.perform(get("/admins/demote-admin-teacher/{id}", VALID_USER_ID)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/admins/all-admins"));
    }

 /*    @Test
   void allStudents() {
    }

    @Test
    void blockStudentProfile() {
    }

    @Test
    void activateStudentProfile() {
    }

    @Test
    void promoteStudentToTeacher() {
    }

    @Test
    void promoteStudentToAdmin() {
    }

    @Test
    void allTeachers() {
    }

    @Test
    void blockTeacherProfile() {
    }

    @Test
    void activateTeacherProfile() {
    }

    @Test
    void demoteTeacherToStudent() {
    }

    @Test
    void promoteTeacherToAdmin() {
    }

    @Test
    void allTeacherRequests() {
    }

    @Test
    void confirmTeacherRequest() {
    }

    @Test
    void cancelTeacherRequest() {
    }

    @Test
    void createAdmin() {
    }

    @Test
    void createAdminConfirm() {
    }

    @Test
    void allCourses() {
    }

    @Test
    void enableCourse() {
    }

    @Test
    void disableCourse() {
    }*/

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
        author = userRepository.save(getAuthor());
        author.setCreatedCourses(new HashSet<>());
        course.setEnrolledStudents(new ArrayList<>());
        Role role = admin;
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        author.setAuthorities(roles);
        course.setAuthor(author);
        topic = topicRepository.findFirstByName(VALID_TOPIC_NAME).orElse(null);
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
        Role role = admin;
        roles.add(role);
        user.setAuthorities(roles);
        user.setRegistrationDate(LocalDateTime.now());
        user.setProfilePicture(VALID_IMAGE_URL);
        return user;
    }

    private User getAuthor() {
        User user = new User();
        user.setFirstName(VALID_FIRST_NAME);
        user.setLastName(VALID_LAST_NAME);
        user.setUsername("validAuthor");
        user.setEmail("validauthoremail@gmail.com");
        user.setPassword(VALID_PASSWORD);
        Set<Role> roles = new HashSet<>();
        Role role = teacher;
        roles.add(role);
        user.setAuthorities(roles);
        user.setRegistrationDate(LocalDateTime.now());
        user.setProfilePicture(VALID_IMAGE_URL);
        return user;
    }
}