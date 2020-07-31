package app.services;

import app.models.entity.User;
import app.repositories.UserRepository;
import app.services.impl.UserDetailsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.management.relation.RoleNotFoundException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {
    private static final String VALID_ID = "validId";
    private static final String VALID_USERNAME = "rusevrado";
    private static final String VALID_PASSWORD = "1234";
    private static final String VALID_NEW_PASSWORD = "12345";
    private static final String VALID_FIRST_NAME = "Radoslav";
    private static final String VALID_LAST_NAME = "Rusev";
    private static final String VALID_EMAIL = "radorusevcrypto@gmail.com";
    private static final String VALID_IMAGE_URL = "[random_url]";
    private User user;

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Before
    public void init() throws RoleNotFoundException {

        user = new User();

        user.setId(VALID_ID);
        user.setFirstName(VALID_FIRST_NAME);
        user.setLastName(VALID_LAST_NAME);
        user.setUsername(VALID_USERNAME);
        user.setEmail(VALID_EMAIL);
        user.setPassword(VALID_PASSWORD);
        user.setAuthorities(new HashSet<>());
        user.setRegistrationDate(LocalDateTime.now());
        user.setProfilePicture(VALID_IMAGE_URL);
    }

    @Test
    public void loadUserByUsername_shouldReturnCorrectUser() {
        //Arrange
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.of(user));
        //Act
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(VALID_EMAIL);
        //Assert
        assertEquals(userDetails.getUsername(), VALID_USERNAME);
    }
    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_shouldThrowException() {
        //Arrange
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
        //Act
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(VALID_EMAIL);

    }
}