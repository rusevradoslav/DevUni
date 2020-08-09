package app.web.controllers.api;

import app.models.entity.Role;
import app.models.entity.User;
import app.repositories.RoleRepository;
import app.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class AdminRestControllerTest {
    private static final String VALID_ID = "validId";
    private static final String VALID_USERNAME = "validUsername";
    private static final String VALID_PASSWORD = "1234";
    private static final String VALID_FIRST_NAME = "Radoslav";
    private static final String VALID_LAST_NAME = "Rusev";
    private static final String VALID_EMAIL = "validEmail@gmail.com";
    private static final String VALID_IMAGE_URL = "[random_url]";


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private MockMvc mockMvc;

    private User user;
    private Role admin;
    private Role student;
    private Role teacher;
    private Role root;


    @BeforeEach
    public void setUp() {


        this.userRepository.deleteAll();
        this.roleRepository.deleteAll();
        user = new User();

        root = new Role("ROLE_ROOT_ADMIN");
        admin = new Role("ROLE_ADMIN");
        student = new Role("ROLE_STUDENT");
        teacher = new Role("ROLE_TEACHER");
        Set<Role> authorities = Set.of(root, admin, student, teacher);
        roleRepository.saveAndFlush(root);
        roleRepository.saveAndFlush(admin);
        roleRepository.saveAndFlush(student);
        roleRepository.saveAndFlush(teacher);


        user.setId(VALID_ID);
        user.setFirstName(VALID_FIRST_NAME);
        user.setLastName(VALID_LAST_NAME);
        user.setUsername(VALID_USERNAME);
        user.setEmail(VALID_EMAIL);
        user.setPassword(VALID_PASSWORD);
        user.setAuthorities(authorities);
        user.setTeacherRequest(true);
        user.setRegistrationDate(LocalDateTime.now());
        user.setProfilePicture(VALID_IMAGE_URL);

        userRepository.saveAndFlush(user);

    }

    @AfterEach
    void after() {
        this.userRepository.deleteAll();
        this.roleRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = "ROOT_ADMIN")
    public void getAllAdminsShouldReturnAllAdmins() throws Exception {

        this.mockMvc.perform(get("/admins/all-admins-rest"))
                .andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = "ROOT_ADMIN")
    public void getAllAdminsShouldReturnAllTeachers() throws Exception {

        this.mockMvc.perform(get("/admins/all-teachers-rest"))
                .andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = "ROOT_ADMIN")
    public void getAllAdminsShouldReturnAllStudents() throws Exception {

        this.mockMvc.perform(get("/admins/all-students-rest"))
                .andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = "ROOT_ADMIN")
    public void getAllAdminsShouldReturnAllStudentsWithTeacherRequests() throws Exception {
        this.mockMvc.perform(get("/admins/all-students-rest"))
                .andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(1)));

    }

}