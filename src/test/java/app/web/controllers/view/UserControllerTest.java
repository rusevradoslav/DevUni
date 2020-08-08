package app.web.controllers.view;

import app.models.entity.*;
import app.repositories.CourseRepository;
import app.repositories.RoleRepository;
import app.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashSet;

import static app.models.entity.Difficulty.ADVANCE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
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

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private CourseRepository courseRepository;

    @Before
    public void setUp() {
/*
        roleRepository.saveAndFlush(new Role("ROLE_STUDENT"));
        roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));
        roleRepository.saveAndFlush(new Role("ROLE_TEACHER"));
        roleRepository.saveAndFlush(new Role("ROLE_STUDENT"));
        List<Role> authorities = roleRepository.findAll();
        Set<Role> roles = authorities.stream().collect(Collectors.toSet());

        aboutMe = new AboutMe();
        aboutMe.setId(VALID_ABOUT_ME_ID);
        aboutMe.setKnowledgeLevel(VALID_KNOWLEDGE_LEVEL);
        aboutMe.setSelfDescription(VALID_SELF_DESCRIPTION);

        User user = new User();

        user.setId(VALID_ID);
        user.setFirstName(VALID_FIRST_NAME);
        user.setLastName(VALID_LAST_NAME);
        user.setUsername(VALID_USERNAME);
        user.setEmail(VALID_EMAIL);
        user.setPassword(VALID_PASSWORD);
        user.setAuthorities(roles);
        user.setAboutMe(aboutMe);
        user.setRegistrationDate(LocalDateTime.now());
        user.setProfilePicture(VALID_IMAGE_URL);

        user = userRepository.saveAndFlush(this.user);

        Course course1 = getCourse();

        course = courseRepository.saveAndFlush(course1);*/
    }

    @AfterEach
    public void tearDown() {
       /* this.userRepository.deleteAll();
        this.roleRepository.deleteAll();
        this.courseRepository.deleteAll();*/
    }

    @Test
    public void loginReturnsCorrectView() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users/login")
                        .param("title", "Login"))
                .andExpect(view().name("login"));
        //TODO java.lang.NullPointerException
    }

    @Test
    public void registerReturnsCorrectView() throws Exception {

        this.mockMvc.perform(
                get("/users/register"))
                .andExpect(status().isOk());
        //TODO java.lang.NullPointerException
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