package app.web.controllers.view;

import app.models.entity.Role;
import app.models.entity.Topic;
import app.models.entity.User;
import app.models.service.UserServiceModel;
import app.repositories.TopicRepository;
import app.repositories.UserRepository;
import app.services.UserService;
import app.services.impl.CourseServiceImpl;
import app.services.impl.TopicServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class HomeControllerTest {
    private static final String VALID_ID = "validId";
    private static final String VALID_USERNAME = "rusevrado";
    private static final String VALID_PASSWORD = "1234";
    private static final String VALID_FIRST_NAME = "Radoslav";
    private static final String VALID_LAST_NAME = "Rusev";
    private static final String VALID_EMAIL = "radorusevcrypto@gmail.com";
    private static final String VALID_IMAGE_URL = "[random_url]";

    private static final String TOPIC_C_SHARP = "C#";
    private static final String TOPIC_JAVA = "Java";
    private static final String TOPIC_JAVASCRIPT = "JavaScript";
    private static final String TOPIC_PYTHON = "Python";
    private static final String TOPIC_PHP = "Php";
    private static final String TOPIC_CPP = "C++";


    private Topic cSharp;
    private Topic javaScript;
    private Topic java;
    private Topic python;
    private Topic php;
    private Topic cpp;
    private User user;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    @Autowired
    TopicRepository testTopicRepository;
    @Autowired
    UserRepository testUserRepository;

    @MockBean
    private UserService userService;
    @MockBean
    private CourseServiceImpl courseService;
    @MockBean
    private TopicServiceImpl topicService;

    @Autowired
    private HomeController homeController;

    private UserServiceModel userServiceModel;

    @BeforeEach
    public void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();


        homeController = new HomeController(userService,courseService,topicService);
        user = new User();
        Set<Role> authorities = new HashSet<>();
        authorities.add(new Role("ROLE_ADMIN"));
        authorities.add(new Role("STUDENT"));
        authorities.add(new Role("TEACHER"));

        user.setId(VALID_ID);
        user.setFirstName(VALID_FIRST_NAME);
        user.setLastName(VALID_LAST_NAME);
        user.setUsername(VALID_USERNAME);
        user.setEmail(VALID_EMAIL);
        user.setPassword(VALID_PASSWORD);
        user.setAuthorities(authorities);
        user.setRegistrationDate(LocalDateTime.now());
        user.setProfilePicture(VALID_IMAGE_URL);
        User save = testUserRepository.save(user);
        userServiceModel = modelMapper.map(save,UserServiceModel.class);

        cSharp = new Topic(TOPIC_C_SHARP);
        java = new Topic(TOPIC_JAVA);
        javaScript = new Topic(TOPIC_JAVASCRIPT);
        python = new Topic(TOPIC_PYTHON);
        php = new Topic(TOPIC_PHP);
        cpp = new Topic(TOPIC_CPP);

        testTopicRepository.save(cSharp);
        testTopicRepository.save(java);
        testTopicRepository.save(javaScript);
        testTopicRepository.save(python);
        testTopicRepository.save(php);
        testTopicRepository.save(cpp);


    }

    @Test
    public void getIndexShouldReturnStatusOk() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

    }

    @Test
    @WithMockUser("rusevrado")
    public void getIndexShouldReturnStatusOkAnfRedirectToHome() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    public void getAboutUsShouldReturnStatusOk() throws Exception {
        this.mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
        ;
    }

    @Test
    @WithMockUser(username = "rusevrado",password = "1234",roles = "USER")
    public void getHomeShouldReturnStatusOk() throws Exception {
        System.out.println();
        MockHttpSession session = new MockHttpSession();
     when(userService.findByName(VALID_USERNAME)).thenReturn(userServiceModel);

        session.setAttribute("user", user);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/home")
                .session(session);
        this.mockMvc.perform(builder
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());


    }

}