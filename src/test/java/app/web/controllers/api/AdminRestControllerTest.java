package app.web.controllers.api;

import app.models.entity.Role;
import app.models.entity.User;
import app.repositories.RoleRepository;
import app.repositories.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class AdminRestControllerTest {
    private static final String VALID_ID = "validId";
    private static final String VALID_USERNAME = "rusevrado";
    private static final String VALID_PASSWORD = "1234";
    private static final String VALID_FIRST_NAME = "Radoslav";
    private static final String VALID_LAST_NAME = "Rusev";
    private static final String VALID_EMAIL = "radorusevcrypto@gmail.com";
    private static final String VALID_IMAGE_URL = "[random_url]";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleRepository roleRepository;

    private User user;


    @Before
    public void setUp() {
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


    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = "ROOT_ADMIN")
    public void getAllAdminsShouldReturnAllAdmins() throws Exception {
        when(this.userRepository.findAllAdmins()).thenReturn(List.of(user));

        this.mockMvc.perform(get("/admins/all-admins-rest"))
                .andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id",is(VALID_ID)))
                .andExpect(jsonPath("$.[0].username",is(VALID_USERNAME)));

    }

}