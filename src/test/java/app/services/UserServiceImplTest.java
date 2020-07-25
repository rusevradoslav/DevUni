package app.services;

import app.error.UserAlreadyExistException;
import app.models.entity.Role;
import app.models.entity.User;
import app.models.service.RoleServiceModel;
import app.models.service.UserServiceModel;
import app.repositories.UserRepository;
import app.services.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.management.relation.RoleNotFoundException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String VALID_ID = "validId";
    private static final String VALID_USERNAME = "rusevrado";
    private static final String VALID_PASSWORD = "1234";
    private static final String VALID_EDITED_PASSWORD = "12345";
    private static final String VALID_FIRST_NAME = "Radoslav";
    private static final String VALID_LAST_NAME = "Rusev";
    private static final String VALID_EMAIL = "radorusevcrypto@gmail.com";
    private static final String INVALID_EMAIL = "invalid@gmail.com";
    private static final String VALID_EDITED_EMAIL = "radorusevcrypto_new@gmail.com";
    private static final String USERNAME_EXIST_EXCEPTION = "There is an account with that username : rusevrado";
    private static final String EMAIL_EXIST_EXCEPTION = "There is an account with that email address: radorusevcrypto@gmail.com";

    private static final String NEW_IMAGE_URL = "[random_url]";
    private static final String NEW_IMAGE_ID = "[random_id]";

    private static final String WRONG_OLD_PASSWORD = "[wrong_old_password]";

    Set<Role> authorities = new HashSet<>();

    private List<User> fakeRepository;

    private UserServiceModel userServiceModel = new UserServiceModel();

    private User user = new User();

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    RoleService roleService;
    @Mock
    CloudinaryService cloudinaryService;
    @Mock
    ModelMapper modelMapper;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Before
    public void init() throws RoleNotFoundException {
        ModelMapper actualMapper = new ModelMapper();
        BCryptPasswordEncoder actualEncoder = new BCryptPasswordEncoder();

        fakeRepository = new ArrayList<>();

        when(userRepository.saveAndFlush(isA(User.class)))
                .thenAnswer(invocationOnMock -> {
                    fakeRepository.add((User) invocationOnMock.getArguments()[0]);
                    return null;
                });

        when(modelMapper.map(any(UserServiceModel.class), eq(User.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], User.class));

        when(modelMapper.map(any(User.class), eq(UserServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], UserServiceModel.class));

        when(bCryptPasswordEncoder.encode(any())).thenAnswer(invocationOnMock -> actualEncoder.encode((CharSequence) invocationOnMock.getArguments()[0]));

        when(bCryptPasswordEncoder.matches(any(), any())).thenAnswer(invocationOnMock ->
                actualEncoder.matches((String) invocationOnMock.getArguments()[0], (String) invocationOnMock.getArguments()[1]));


        when(roleService.findByAuthority(anyString()))
                .thenAnswer(invocationOnMock -> actualMapper.map(new Role((String) invocationOnMock.getArguments()[0]),
                        RoleServiceModel.class));

        when(userRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);


        user.setId(VALID_ID);
        user.setUsername(VALID_USERNAME);
        user.setEmail(VALID_EMAIL);
        user.setAuthorities(authorities);


        userServiceModel.setUsername(VALID_USERNAME);
        userServiceModel.setFirstName(VALID_FIRST_NAME);
        userServiceModel.setLastName(VALID_LAST_NAME);
        userServiceModel.setPassword(VALID_PASSWORD);
        userServiceModel.setEmail(VALID_EMAIL);
        user.setAuthorities(authorities);

    }

    @Test
    public void loadUserByUsername_shouldReturnUser_WhenUserExist() {
        when(userRepository.findFirstByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(VALID_EMAIL);

        String actual = user.getUsername();
        String expected = userDetails.getUsername();

        System.out.println();

        assertEquals(actual, expected);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_shouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findFirstByEmail(user.getEmail())).thenReturn(Optional.of(user));

        userService.loadUserByUsername(INVALID_EMAIL);

    }


    @Test(expected = UserAlreadyExistException.class)
    public void registerNewUser_shouldThrowException_WhenUsernameAlreadyExist() throws RoleNotFoundException, UserAlreadyExistException {

        when(userRepository.findFirstByUsername(userServiceModel.getUsername())).thenReturn(Optional.of(user));


        userService.registerNewUserAccount(userServiceModel);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void registerNewUser_shouldThrowException_WhenEmailAlreadyExist() throws RoleNotFoundException, UserAlreadyExistException {

        when(userRepository.findFirstByEmail(userServiceModel.getEmail())).thenReturn(Optional.of(user));

        userService.registerNewUserAccount(userServiceModel);

    }


    @Test
    public void registerNewUser_shouldRegisterUser_whenUserDoesNotExistInDB() throws RoleNotFoundException {
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.empty());

        userService.registerNewUserAccount(userServiceModel);

        int actual = 1;
        int expected = fakeRepository.size();

        assertEquals(actual, expected);

    }

    @Test
    public void registerFirstUser_shouldHaveRole_ROLE_ROOT_ADMIN() throws RoleNotFoundException {

        when(userRepository.count()).thenReturn(0L);
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.empty());

        userService.registerNewUserAccount(userServiceModel);


        User user = fakeRepository.get(0);
        String actualRole = "ROLE_ROOT_ADMIN";
        String expectedRole = user.getAuthorities().stream().findFirst().get().getAuthority();

        assertEquals(actualRole, expectedRole);

    }

    @Test
    public void registerFirstUser_shouldHaveRole_ROLE_STUDENT() throws RoleNotFoundException {

        when(userRepository.count()).thenReturn(1L);
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.empty());

        userService.registerNewUserAccount(userServiceModel);


        User user = fakeRepository.get(0);
        String actualRole = "ROLE_STUDENT";
        String expectedRole = user.getAuthorities().stream().findFirst().get().getAuthority();

        assertEquals(actualRole, expectedRole);

    }

    @Test(expected = UserAlreadyExistException.class)
    public void createNewAdmin_shouldThrowException_WhenUsernameAlreadyExist() throws RoleNotFoundException, UserAlreadyExistException {

        when(userRepository.findFirstByUsername(userServiceModel.getUsername())).thenReturn(Optional.of(user));


        userService.createNewAdminAccount(userServiceModel);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void createNewAdmin_shouldThrowException_WhenEmailAlreadyExist() throws RoleNotFoundException, UserAlreadyExistException {

        when(userRepository.findFirstByEmail(userServiceModel.getEmail())).thenReturn(Optional.of(user));

        userService.createNewAdminAccount(userServiceModel);

    }

    @Test
    public void createNewAdmin_shouldThrowExcept_WhenUserAlreadyExist() throws RoleNotFoundException {
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.empty());

        userService.createNewAdminAccount(userServiceModel);

        User user = fakeRepository.get(0);
        String actualRole = "ROLE_ADMIN";
        String expectedRole = user.getAuthorities().stream().findFirst().get().getAuthority();
        System.out.println();
        assertEquals(actualRole, expectedRole);

    }

    @Test
    public void sendTeacherRequest_shouldChangeTeacherRequestToTrue() throws RoleNotFoundException {


        System.out.println();
    }
  /*   @Test
        public void registerNewUser_shouldThrowException_WhenEmailAlreadyExist() {
            String actual = EMAIL_EXIST_EXCEPTION;
            String expected = "";

            when(userRepository.findFirstByEmail(userServiceModel.getEmail())).thenReturn(Optional.of(user));

            try {
                userService.registerNewUserAccount(userServiceModel);
            } catch (UserAlreadyExistException | RoleNotFoundException e) {
                expected = e.getMessage();
            }
            assertEquals(actual, expected);
        }
        @Test
        public void registerNewUser_shouldThrowException_WhenUsernameAlreadyExist() {

            when(userRepository.findFirstByUsername(userServiceModel.getUsername())).thenReturn(Optional.of(user));

            String actual = USERNAME_EXIST_EXCEPTION;
            String expected = "";
            try {
                userService.registerNewUserAccount(userServiceModel);
            } catch (UserAlreadyExistException | RoleNotFoundException e) {
                expected = e.getMessage();
            }
            System.out.println();
            assertEquals(actual, expected);
        }*/
}