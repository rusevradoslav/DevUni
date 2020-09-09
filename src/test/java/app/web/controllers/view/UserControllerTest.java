package app.web.controllers.view;

import app.error.UserAlreadyExistException;
import app.models.entity.*;
import app.models.service.*;
import app.models.view.UserDetailsViewModel;
import app.repositories.*;
import app.services.impl.AboutMeServiceImpl;
import app.services.impl.CourseServiceImpl;
import app.services.impl.UserServiceImpl;
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserControllerTest {
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
    private static final String VALID_NEW_PASSWORD = "12345";
    private static final String VALID_FIRST_NAME = "Radoslav";
    private static final String VALID_LAST_NAME = "Rusev";
    private static final String VALID_EMAIL = "radorusevcrypto@gmail.com";
    private static final String VALID_NEW_EMAIL = "radorusevcryptoNEW@gmail.com";
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
    private AboutMeServiceImpl aboutMeService;
    private ModelMapper modelMapper;

    private MockMvc mockMvc;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setUp() {
        admin = roleRepository.findFirstByAuthority("ROLE_ADMIN").orElse(null);
        teacher = roleRepository.findFirstByAuthority("ROLE_TEACHER").orElse(null);
        student = roleRepository.findFirstByAuthority("ROLE_STUDENT").orElse(null);
        root_admin = roleRepository.findFirstByAuthority("ROLE_ROOT_ADMIN").orElse(null);
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
        this.roleRepository.deleteAll();
    }


    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ADMIN")
    public void login() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);


        this.mockMvc.perform(get("/users/login")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    public void loginError() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);


        this.mockMvc.perform(get("/users/login")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("users/login"));
    }

    @Test
    public void loginShouldReturnLoginPageWhenUserIsAnonymous() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);


        this.mockMvc.perform(get("/users/login")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("users/login"));
    }

    @Test
    public void register() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        this.mockMvc.perform(get("/users/register")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("/users/register"));

    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ADMIN")
    public void registerShouldRedirectToHome() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        this.mockMvc.perform(get("/users/register")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/home"));

    }

    @Test
    public void registerConfirm() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        this.mockMvc.perform(post("/users/register")
                .param("username", VALID_USERNAME)
                .param("firstName", VALID_FIRST_NAME)
                .param("lastName", VALID_LAST_NAME)
                .param("email", VALID_EMAIL)
                .param("password", VALID_PASSWORD)
                .param("confirmPassword", VALID_PASSWORD)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/users/login"));
    }

    @Test
    public void registerConfirmShouldRedirectToRegisterPage() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);


        this.mockMvc.perform(post("/users/register")
                .param("username", VALID_USERNAME)
                .param("firstName", VALID_FIRST_NAME)
                .param("lastName", VALID_LAST_NAME)
                .param("email", VALID_EMAIL)
                .param("password", "123")
                .param("confirmPassword", VALID_PASSWORD)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/users/register"));
    }

    @Test
    public void registerConfirmShouldRedirectToRegisterPageAndThrowUserAlreadyExistException() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(userService.registerNewUserAccount(userServiceModel)).thenThrow(UserAlreadyExistException.class);

        this.mockMvc.perform(post("/users/register")
                .param("username", VALID_USERNAME)
                .param("firstName", VALID_FIRST_NAME)
                .param("lastName", VALID_LAST_NAME)
                .param("email", VALID_EMAIL)
                .param("password", "123")
                .param("confirmPassword", VALID_PASSWORD)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/users/register"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ADMIN")
    public void userDetails() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        UserDetailsViewModel userDetailsViewModel = this.modelMapper.map(userServiceModel, UserDetailsViewModel.class);

        when(this.userService.getUserDetailsByUsername(VALID_USERNAME)).thenReturn(userDetailsViewModel);

        this.mockMvc.perform(get("/users/user-details")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("users/user-details"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ADMIN")
    public void userDetailsAddProfilePicture() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        MockMultipartFile profilePicture = new MockMultipartFile("file",
                "hello.txt",
                String.valueOf(MediaType.ANY_IMAGE_TYPE),
                "Hello, World!" .getBytes());
        when(userService.addProfilePicture(userServiceModel, profilePicture)).thenReturn(userServiceModel);

        this.mockMvc.perform(multipart("/users/user-details")
                .file(profilePicture)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ADMIN")
    public void userDetailsAddProfilePictureUserDetailsPage() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        MockMultipartFile profilePicture = new MockMultipartFile("file",
                "hello.zip",
                String.valueOf(MediaType.ZIP),
                "Hello, World!" .getBytes());
        when(userService.addProfilePicture(userServiceModel, profilePicture)).thenReturn(userServiceModel);

        this.mockMvc.perform(multipart("/users/user-details")
                .file(profilePicture)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ADMIN")
    public void userDetailsAddProfilePictureShouldReturnErrorPage() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        MockMultipartFile profilePicture = new MockMultipartFile("file",
                "hello.txt",
                String.valueOf(MediaType.ANY_IMAGE_TYPE),
                "Hello, World!" .getBytes());
        when(userService.addProfilePicture(userServiceModel, profilePicture)).thenReturn(userServiceModel);

        this.mockMvc.perform(multipart("/users/user-details")
                .param("profilePicture", "INVALID_INPUT_DATA")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("error"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ADMIN")
    public void changeEmail() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        this.mockMvc.perform(get("/users/change-email")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("users/change-email"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ADMIN")
    void changeEmailConfirm() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(this.userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        when(this.userService.changeEmail(userServiceModel, VALID_NEW_EMAIL)).thenReturn(userServiceModel);

        this.mockMvc.perform(post("/users/change-email")
                .param("oldEmail", VALID_EMAIL)
                .param("newEmail", VALID_NEW_EMAIL)
                .param("confirmNewEmail", VALID_NEW_EMAIL)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/users/user-details"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ADMIN")
    void changeEmailConfirmShouldThrowInvalidEmailExcetion() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(this.userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        when(this.userService.changeEmail(userServiceModel, VALID_NEW_EMAIL)).thenReturn(userServiceModel);

        this.mockMvc.perform(post("/users/change-email")
                .param("oldEmail", VALID_EMAIL)
                .param("newEmail", "InvalidEmail")
                .param("confirmNewEmail", "InvalidEmail")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/users/change-email"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ADMIN")
    void changeEmailConfirmShouldThrowInvalidOldEmailExcetion() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(this.userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        when(this.userService.changeEmail(userServiceModel, VALID_NEW_EMAIL)).thenReturn(userServiceModel);

        this.mockMvc.perform(post("/users/change-email")
                .param("oldEmail", VALID_NEW_EMAIL)
                .param("newEmail", VALID_NEW_EMAIL)
                .param("confirmNewEmail", VALID_NEW_EMAIL)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/users/change-email"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ADMIN")
    void changeEmailConfirmShouldThrowUserAlreadyExistException() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(this.userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        when(this.userService.changeEmail(userServiceModel, VALID_NEW_EMAIL)).thenThrow(UserAlreadyExistException.class);

        this.mockMvc.perform(post("/users/change-email")
                .param("oldEmail", VALID_EMAIL)
                .param("newEmail", VALID_NEW_EMAIL)
                .param("confirmNewEmail", VALID_NEW_EMAIL)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/users/change-email"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ADMIN")
    public void changePassword() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        this.mockMvc.perform(get("/users/change-password")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("users/change-password"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ADMIN")
    public void changePasswordConfirm() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(this.userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        when(this.userService.changePassword(userServiceModel, VALID_NEW_PASSWORD)).thenReturn(userServiceModel);


        this.mockMvc.perform(post("/users/change-password")
                .param("oldPassword", "1")
                .param("newPassword", "1")
                .param("confirmNewPassword", "2")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/users/change-password"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ADMIN")
    public void changePasswordConfirmShouldThrowInvalidOldPasswordException() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(this.userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        when(this.userService.changePassword(userServiceModel, VALID_NEW_PASSWORD)).thenReturn(userServiceModel);

        this.mockMvc.perform(post("/users/change-password")
                .param("oldPassword", VALID_PASSWORD)
                .param("newPassword", VALID_NEW_PASSWORD)
                .param("confirmNewPassword", VALID_NEW_PASSWORD)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/users/change-password"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "STUDENT")
    public void sentTeacherRequest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(this.userService.findById(VALID_USER_ID)).thenReturn(userServiceModel);

        this.mockMvc.perform(get("/users/teacher-request/{id}", VALID_USER_ID)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/users/user-details"));
    }


    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "TEACHER")
    public void writeAboutMe() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);


        this.mockMvc.perform(get("/users/about-me")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("users/about-me"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "TEACHER")
    public void writeAboutMeConfirm() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(this.userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        when(this.aboutMeService.addTeacherAboutMe(userServiceModel, aboutMeServiceModel)).thenReturn(aboutMeServiceModel);

        this.mockMvc.perform(post("/users/about-me")
                .param("knowledgeLevel", VALID_KNOWLEDGE_LEVEL)
                .param("selfDescription", VALID_SHORT_DESCRIPTION)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "TEACHER")
    public void writeAboutMeConfirmShouldThrowException() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(this.userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);
        when(this.aboutMeService.addTeacherAboutMe(userServiceModel, aboutMeServiceModel)).thenReturn(aboutMeServiceModel);

        this.mockMvc.perform(post("/users/about-me")
                .param("knowledgeLevel", "Invalid_data")
                .param("selfDescription", VALID_SHORT_DESCRIPTION)
                .session(session)
                .with(csrf()))
                .andExpect(view().name("redirect:/home"));
    }

    @Test
    @WithMockUser(username = "rusevrado", password = "1234", roles = "ADMIN")
    public void getUserCheckedAssignments() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userServiceModel);

        when(this.userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);

        this.mockMvc.perform(get("/users/userCheckedAssignments")
                .session(session)
                .with(csrf()))
                .andExpect(view().name("users/user-checked-assignments-table"));
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
        author = userRepository.save(getAuthor());
        author.setCreatedCourses(new HashSet<>());
        course.setEnrolledStudents(new ArrayList<>());
        author.setAuthorities(Set.of(admin));
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
        user.setAuthorities(Set.of(admin));
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
        user.setAuthorities(Set.of(admin));
        user.setRegistrationDate(LocalDateTime.now());
        user.setProfilePicture(VALID_IMAGE_URL);
        return user;
    }
}