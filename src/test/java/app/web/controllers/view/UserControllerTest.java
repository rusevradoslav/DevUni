package app.web.controllers.view;

import app.models.entity.*;
import app.repositories.CourseRepository;
import app.repositories.RoleRepository;
import app.repositories.UserRepository;
import app.services.AboutMeService;
import app.services.AssignmentService;
import app.services.UserService;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static app.models.entity.Difficulty.ADVANCE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private static final String VALID_COURSE_ID = "validId";
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

    private static final String VALID_ID = "validId";
    private static final String VALID_USERNAME = "rusevrado";
    private static final String VALID_PASSWORD = "1234";
    private static final String VALID_NEW_PASSWORD = "12345";
    private static final String VALID_FIRST_NAME = "Radoslav";
    private static final String VALID_LAST_NAME = "Rusev";
    private static final String VALID_EMAIL = "radorusevcrypto@gmail.com";
    private static final String INVALID_EMAIL = "invalid@gmail.com";
    private static final String VALID_NEW_EMAIL = "radorusevcrypto_new@gmail.com";

    private static final String VALID_IMAGE_URL = "[random_url]";

    private static final String VALID_ABOUT_ME_ID = "validAboutMeId";
    private static final String VALID_KNOWLEDGE_LEVEL = "Java Master";
    private static final String VALID_SELF_DESCRIPTION = "Java Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus id massa fermentum nullam sodales.";
    private User user;
    private AboutMe aboutMe;
    private Course course;

    private Role admin;
    private Role student;
    private Role teacher;
    private Role root;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private AboutMeService aboutMeService;
    @Autowired
    private AssignmentService assignmentService;

    private final ModelMapper modelMapper = new ModelMapper();


    @BeforeEach
    public void setUp() {
        this.userRepository.deleteAll();
        this.roleRepository.deleteAll();


        root = new Role("ROLE_ROOT_ADMIN");
        admin = new Role("ROLE_ADMIN");
        student = new Role("ROLE_STUDENT");
        teacher = new Role("ROLE_TEACHER");
        Set<Role> authorities = Set.of(root, admin, student, teacher);
        roleRepository.saveAndFlush(root);
        roleRepository.saveAndFlush(admin);
        roleRepository.saveAndFlush(student);
        roleRepository.saveAndFlush(teacher);

        user = new User();

        user.setId(VALID_ID);
        user.setFirstName(VALID_FIRST_NAME);
        user.setLastName(VALID_LAST_NAME);
        user.setUsername(VALID_USERNAME);
        user.setEmail(VALID_EMAIL);
        user.setPassword(VALID_PASSWORD);
        user.setAuthorities(authorities);
        user.setTeacherRequest(true);
        user.setRegistrationDate(java.time.LocalDateTime.now());
        user.setProfilePicture(VALID_IMAGE_URL);

        userRepository.saveAndFlush(user);

        aboutMe = new AboutMe();
        aboutMe.setId(VALID_ABOUT_ME_ID);
        aboutMe.setKnowledgeLevel(VALID_KNOWLEDGE_LEVEL);
        aboutMe.setSelfDescription(VALID_SELF_DESCRIPTION);


        Course course1 = getCourse();

        course = courseRepository.saveAndFlush(course1);
    }

    @AfterEach
    public void tearDown() {
        this.userRepository.deleteAll();
        this.roleRepository.deleteAll();
        this.courseRepository.deleteAll();
    }

    @Test
    public void loginReturnsCorrectView() throws Exception {

        this.mockMvc.perform(get("/users/login"))
                .andExpect(view().name("login"));

    }

    @Test
    public void registerReturnsCorrectView() throws Exception {

        this.mockMvc.perform(get("/users/register")
                .flashAttr("attr" ,"test")
        ).andExpect(status().isOk());

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
        User author = new User();
        author.setId(VALID_AUTHOR_ID);
        author.setUsername(VALID_AUTHOR_USERNAME);
        author.setCreatedCourses(new HashSet<>());
        course.setAuthor(author);
        Topic topic = new Topic();
        topic.setId(VALID_TOPIC_ID);
        topic.setName(VALID_TOPIC_NAME);
        topic.setCourses(new ArrayList<>());
        course.setTopic(topic);
        return course;
    }
}