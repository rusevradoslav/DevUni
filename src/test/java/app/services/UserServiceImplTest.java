package app.services;

import app.error.InvalidEmailException;
import app.error.UserAlreadyExistException;
import app.models.entity.AboutMe;
import app.models.entity.Role;
import app.models.entity.User;
import app.models.service.AboutMeServiceModel;
import app.models.service.RoleServiceModel;
import app.models.service.UserServiceModel;
import app.models.view.UserDetailsViewModel;
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
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

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

    Set<Role> authorities = new HashSet<>();

    private List<User> testRepository;

    private User user;
    private UserServiceModel testUserServiceModel;


    private AboutMe aboutMe;
    private AboutMeServiceModel testAboutMeServiceModel;


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

        testRepository = new ArrayList<>();

        when(userRepository.saveAndFlush(isA(User.class)))
                .thenAnswer(invocationOnMock -> {
                    testRepository.add((User) invocationOnMock.getArguments()[0]);
                    return null;
                });

        when(modelMapper.map(any(UserServiceModel.class), eq(User.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], User.class));

        when(modelMapper.map(any(User.class), eq(UserServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], UserServiceModel.class));

        when(modelMapper.map(any(User.class), eq(UserDetailsViewModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], UserDetailsViewModel.class));

        when(modelMapper.map(any(AboutMe.class), eq(AboutMeServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], AboutMeServiceModel.class));

        when(bCryptPasswordEncoder.encode(any())).thenAnswer(invocationOnMock -> actualEncoder.encode((CharSequence) invocationOnMock.getArguments()[0]));

        when(roleService.findByAuthority(anyString()))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(new Role((String) invocationOnMock.getArguments()[0]),
                                RoleServiceModel.class));


        aboutMe = new AboutMe();
        aboutMe.setId(VALID_ABOUT_ME_ID);
        aboutMe.setKnowledgeLevel(VALID_KNOWLEDGE_LEVEL);
        aboutMe.setSelfDescription(VALID_SELF_DESCRIPTION);


        testAboutMeServiceModel = modelMapper.map(aboutMe, AboutMeServiceModel.class);


        user = new User();

        user.setId(VALID_ID);
        user.setFirstName(VALID_FIRST_NAME);
        user.setLastName(VALID_LAST_NAME);
        user.setUsername(VALID_USERNAME);
        user.setEmail(VALID_EMAIL);
        user.setPassword(VALID_PASSWORD);
        user.setAuthorities(authorities);
        user.setAboutMe(aboutMe);
        user.setRegistrationDate(LocalDateTime.now());
        user.setProfilePicture(VALID_IMAGE_URL);

        testUserServiceModel = modelMapper.map(user, UserServiceModel.class);
        testUserServiceModel.setAboutMeServiceModel(testAboutMeServiceModel);


    }

    @Test
    public void loadUserByUsername_shouldReturnUser_WhenUserExist() {
        //Arrange
        when(userRepository.findFirstByEmail(user.getEmail())).thenReturn(Optional.of(user));

        //Act
        UserDetails userDetails = userService.loadUserByUsername(VALID_EMAIL);

        //Assert
        String actual = user.getUsername();
        String expected = userDetails.getUsername();

        assertEquals(actual, expected);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_shouldThrowException_WhenUserDoesNotExist() {
        //Arrange
        when(userRepository.findFirstByEmail(user.getEmail())).thenReturn(Optional.of(user));

        //Act //Assert
        userService.loadUserByUsername(INVALID_EMAIL);

    }

    @Test(expected = UserAlreadyExistException.class)
    public void registerNewUser_shouldThrowException_WhenUsernameAlreadyExist() throws RoleNotFoundException, UserAlreadyExistException {
        //Arrange
        when(userRepository.findFirstByUsername(testUserServiceModel.getUsername())).thenReturn(Optional.of(user));


        //Act //Assert
        userService.registerNewUserAccount(testUserServiceModel);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void registerNewUser_shouldThrowException_WhenEmailAlreadyExist() throws RoleNotFoundException, UserAlreadyExistException {
        //Arrange
        when(userRepository.findFirstByEmail(testUserServiceModel.getEmail())).thenReturn(Optional.of(user));

        //Act //Assert
        userService.registerNewUserAccount(testUserServiceModel);
    }

    @Test
    public void registerNewUser_shouldRegisterUser_whenUserDoesNotExistInDB() throws RoleNotFoundException {
        //Arrange
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.empty());
        //Act
        userService.registerNewUserAccount(testUserServiceModel);

        //Assert
        int actual = 1;
        int expected = testRepository.size();

        assertEquals(actual, expected);

    }

    @Test
    public void registerFirstUser_shouldHaveRole_ROLE_ROOT_ADMIN() throws RoleNotFoundException {
        //Arrange
        when(userRepository.count()).thenReturn(0L);
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.empty());

        //Act
        userService.registerNewUserAccount(testUserServiceModel);

        //Assert
        User user = testRepository.get(0);
        String actualRole = "ROLE_ROOT_ADMIN";
        String expectedRole = user.getAuthorities().stream().findFirst().get().getAuthority();

        assertEquals(actualRole, expectedRole);

    }

    @Test
    public void registerFirstUser_shouldHaveRole_ROLE_STUDENT() throws RoleNotFoundException {

        //Arrange
        when(userRepository.count()).thenReturn(1L);
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.empty());

        //Act
        userService.registerNewUserAccount(testUserServiceModel);

        //Assert
        User user = testRepository.get(0);
        String actualRole = "ROLE_STUDENT";
        String expectedRole = user.getAuthorities().stream().findFirst().get().getAuthority();

        assertEquals(actualRole, expectedRole);

    }

    @Test(expected = UserAlreadyExistException.class)
    public void createNewAdmin_shouldThrowException_WhenUsernameAlreadyExist() throws RoleNotFoundException, UserAlreadyExistException {
        //Arrange
        when(userRepository.findFirstByUsername(testUserServiceModel.getUsername())).thenReturn(Optional.of(user));

        //Act //Assert
        userService.createNewAdminAccount(testUserServiceModel);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void createNewAdmin_shouldThrowException_WhenEmailAlreadyExist() throws RoleNotFoundException, UserAlreadyExistException {
        //Arrange
        when(userRepository.findFirstByEmail(testUserServiceModel.getEmail())).thenReturn(Optional.of(user));

        //Act //Assert
        userService.createNewAdminAccount(testUserServiceModel);

    }

    @Test
    public void createNewAdmin_shouldThrowExcept_WhenUserAlreadyExist() throws RoleNotFoundException {
        //Arrange
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.empty());

        //Act
        userService.createNewAdminAccount(testUserServiceModel);

        //Assert
        User user = testRepository.get(0);
        String actualRole = "ROLE_ADMIN";
        String expectedRole = user.getAuthorities().stream().findFirst().get().getAuthority();

        assertEquals(actualRole, expectedRole);

    }

    @Test
    public void findById_shouldReturnCorrectUser_WhenIdExist() {
        //Arrange
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));

        //Act
        UserServiceModel userServiceModel = this.userService.findById(VALID_ID);

        //Assert
        String actual = VALID_ID;
        String expected = userServiceModel.getId();

        assertEquals(actual, expected);
    }


    @Test
    public void findByEmail_shouldReturnCorrectUser_WhenEmailExist() {
        //Arrange
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.of(user));

        //Act
        UserServiceModel user2 = this.userService.findByEmail(VALID_EMAIL);

        //Assert
        String actual = VALID_EMAIL;
        String expected = user2.getEmail();


        assertEquals(actual, expected);
    }

    @Test(expected = InvalidEmailException.class)
    public void findByEmail_shouldThrowException_WhenEmailDoesNotExist() {
        //Arrange
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
        //Act //Assert
        this.userService.findByEmail(VALID_EMAIL);
    }


    @Test
    public void findByName_shouldReturnCorrectUserWithAboutMe_WhenUserNameExist() {
        //Arrange
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.of(user));

        //Act
        UserServiceModel userServiceModel = userService.findByName(VALID_USERNAME);

        //Assert
        String actual = testAboutMeServiceModel.getKnowledgeLevel();

        String expected = userServiceModel.getAboutMeServiceModel().getKnowledgeLevel();

        assertEquals(actual, expected);


    }

    @Test
    public void findByName_shouldReturnCorrectUserWithNullAboutMe_WhenUserNameExist() {
        //Arrange
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.of(user));
        user.setAboutMe(null);
        //Act
        UserServiceModel userServiceModel = userService.findByName(VALID_USERNAME);

        //Assert
        String actual = null;

        AboutMeServiceModel expected = userServiceModel.getAboutMeServiceModel();

        assertEquals(actual, expected);


    }

    @Test(expected = UsernameNotFoundException.class)
    public void findByName_shouldThrowException_WhenUsernameDoesNotExist() {
        //Arrange
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.empty());

        //Act
        this.userService.findByName(VALID_USERNAME);

    }

    @Test
    public void getUserDetailsByUsername_shouldReturnCorrectUser_WhenEmailExist() {
        //Arrange
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.of(user));

        //Act
        UserDetailsViewModel userDetailsViewModel = this.userService.getUserDetailsByUsername(VALID_USERNAME);

        //Assert
        String actual = VALID_USERNAME;

        String expected = userDetailsViewModel.getUsername();

        assertEquals(actual, expected);

    }

    @Test(expected = UsernameNotFoundException.class)
    public void getUserDetailsByUsername_shouldThrowException_WhenEmailDoesNotExist() {
        //Arrange
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.empty());
        //Act
        this.userService.getUserDetailsByUsername(VALID_USERNAME);
    }

    @Test
    public void sentTeacherRequest_shouldCallUserRepository() {
        //Arrange
        user.setTeacherRequest(true);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));
        //Act
        UserServiceModel userServiceModel = userService.sentTeacherRequest(testUserServiceModel);

        //Assert
        boolean actual = true;
        boolean expected = userServiceModel.isTeacherRequest();

        assertEquals(actual, expected);

    }

    @Test
    public void confirmTeacherRequest_shouldCallUserRepository() throws RoleNotFoundException {
        //Arrange
        Role role = new Role("ROLE_TEACHER");
        when(roleService.findAuthorityByName("ROLE_TEACHER")).thenReturn(role);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));
        //Act
        UserServiceModel userServiceModel = userService.confirmTeacherRequest(testUserServiceModel);

        //Assert
        boolean actual = false;
        boolean expected = userServiceModel.isTeacherRequest();

        assertEquals(actual, expected);


    }

    @Test
    public void cancelTeacherRequest_shouldCallUserRepository() {
        //Arrange
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));
        //Act
        UserServiceModel userServiceModel = userService.cancelTeacherRequest(testUserServiceModel);

        //Assert
        boolean actual = false;
        boolean expected = userServiceModel.isTeacherRequest();

        assertEquals(actual, expected);

    }

    @Test
    public void changeRoleToTeacher_shouldCallUserRepository() throws RoleNotFoundException {
        //Arrange
        Set<Role> authorities = new HashSet<>();
        Role role = new Role("ROLE_TEACHER");
        authorities.add(role);
        user.setAuthorities(authorities);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));
        when(roleService.findAuthorityByName("ROLE_TEACHER")).thenReturn(role);

        //Act
        UserServiceModel userServiceModel = userService.changeRoleToTeacher(testUserServiceModel);

        //Assert
        String actual = "ROLE_TEACHER";
        String expected = userServiceModel.getAuthorities().stream().findFirst().get().getAuthority();

        assertEquals(actual, expected);

    }


    @Test
    public void changeRoleToStudent_shouldCallUserRepository() throws RoleNotFoundException {
        //Arrange

        Set<Role> authorities = new HashSet<>();
        Role role = new Role("ROLE_STUDENT");
        authorities.add(role);
        user.setAuthorities(authorities);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));
        when(roleService.findAuthorityByName("ROLE_STUDENT")).thenReturn(role);

        //Act
        UserServiceModel userServiceModel = userService.changeRoleToStudent(testUserServiceModel);


        //Assert
        String actual = "ROLE_STUDENT";
        String expected = userServiceModel.getAuthorities().stream().findFirst().get().getAuthority();

        assertEquals(actual, expected);


    }

    @Test
    public void changeRoleToAdmin_shouldCallUserRepository() throws RoleNotFoundException {
        //Arrange
        Set<Role> authorities = new HashSet<>();
        Role role = new Role("ROLE_ADMIN");
        authorities.add(role);
        user.setAuthorities(authorities);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));
        when(roleService.findAuthorityByName("ROLE_ADMIN")).thenReturn(role);
        //Act
        UserServiceModel userServiceModel = userService.changeRoleToAdmin(testUserServiceModel);

        //Assert
        String actual = "ROLE_ADMIN";
        String expected = userServiceModel.getAuthorities().stream().findFirst().get().getAuthority();

        assertEquals(actual, expected);


    }


    @Test
    public void changePassword_shouldChangeUserPasswordAndCallUserRepository() {
        //Arrange
        String newEncodedPassword = bCryptPasswordEncoder.encode(VALID_NEW_PASSWORD);
        user.setPassword(newEncodedPassword);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));

        //Act
        UserServiceModel userServiceModel = userService.changePassword(testUserServiceModel, VALID_NEW_PASSWORD);

        //Assert
        String actual = newEncodedPassword;
        String expected = userServiceModel.getPassword();

        assertEquals(actual, expected);
    }

    @Test
    public void changeEmail_shouldChangeEmailAndCallUserRepository() {
        //Arrange
        user.setEmail(VALID_NEW_EMAIL);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));
        //Act
        UserServiceModel userServiceModel = userService.changeEmail(testUserServiceModel, VALID_NEW_EMAIL);
        //Assert
        String actual = VALID_NEW_EMAIL;
        String expected = userServiceModel.getEmail();

        assertEquals(actual, expected);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void changeEmail_shouldThrowUserAlreadyExistException() {
        //Arrange
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.of(user));
        //Act
        userService.changeEmail(testUserServiceModel, VALID_EMAIL);
    }

    @Test
    public void findAllAdmins_shouldReturnAllAdminsAndCallUserRepository() {
        //Arrange
        User newUser = new User();
        newUser.setFirstName("NewUserFirstName");
        newUser.setLastName("NewUserLastName");
        newUser.setRegistrationDate(LocalDateTime.now());
        when(userRepository.findAllAdmins()).thenReturn(Arrays.asList(user, newUser));
        //Act
        List<UserDetailsViewModel> allAdmins = this.userService.findAllAdmins();

        //Assert
        int actual = 2;
        int expected = allAdmins.size();
        assertEquals(actual, expected);

    }

    @Test
    public void findAllStudents_shouldReturnAllStudentsAndCallUserRepository() {
        //Arrange
        User newUser = new User();
        newUser.setFirstName("NewUserFirstName");
        newUser.setLastName("NewUserLastName");
        newUser.setRegistrationDate(LocalDateTime.now());
        when(userRepository.findAllStudents()).thenReturn(Arrays.asList(user, newUser));
        //Act
        List<UserDetailsViewModel> allAdmins = this.userService.findAllStudents();

        //Assert
        int actual = 2;
        int expected = allAdmins.size();
        assertEquals(actual, expected);

    }

    @Test
    public void findAllStudentsWithTeacherRequests_shouldReturnAllStudentsWithTeacherRequestsAndCallUserRepository() {
        //Arrange
        User first = new User();
        first.setUsername("First");
        first.setLastName("First");
        first.setRegistrationDate(LocalDateTime.now());
        first.setTeacherRequest(true);
        User second = new User();
        second.setUsername("Second");
        second.setLastName("Second");
        second.setRegistrationDate(LocalDateTime.now());
        second.setTeacherRequest(true);
        when(userRepository.findAllStudentsWithRequests()).thenReturn(Arrays.asList(first, second));
        //Act
        List<UserDetailsViewModel> allAdmins = this.userService.findAllStudentsWithRequests();

        //Assert
        int actual = 2;
        int expected = allAdmins.size();
        assertEquals(actual, expected);

    }

    @Test
    public void findAllTeachers_shouldReturnAllTeachersWithAboutMeAndCallUserRepository() {
        //Arrange
        AboutMe firstAboutMe = new AboutMe();
        aboutMe.setId("firstAboutMe");
        AboutMe secondAboutMe = new AboutMe();
        aboutMe.setId("secondAboutMe");
        User first = new User();
        first.setUsername("First");
        first.setLastName("First");
        first.setRegistrationDate(LocalDateTime.now());
        first.setAboutMe(firstAboutMe);
        User second = new User();
        second.setUsername("Second");
        second.setLastName("Second");
        second.setRegistrationDate(LocalDateTime.now());
        second.setAboutMe(secondAboutMe);
        when(userRepository.findAllTeachers()).thenReturn(Arrays.asList(first, second));
        //Act
        List<UserDetailsViewModel> allTeachers = userService.findAllTeachers();

        //Assert
        int actual = 2;
        int expected = allTeachers.size();
        assertEquals(actual, expected);

    }

    @Test
    public void findAllTeachers_shouldReturnAllTeachersWITHOUTAboutMeAndCallUserRepository() {
        //Arrange
        User first = new User();
        first.setUsername("First");
        first.setLastName("First");
        first.setRegistrationDate(LocalDateTime.now());
        first.setAboutMe(null);
        User second = new User();
        second.setUsername("Second");
        second.setLastName("Second");
        second.setRegistrationDate(LocalDateTime.now());
        second.setAboutMe(null);
        when(userRepository.findAllTeachers()).thenReturn(Arrays.asList(first, second));
        //Act
        List<UserDetailsViewModel> allTeachers = userService.findAllTeachers();

        //Assert
        int actual = 2;
        int expected = allTeachers.size();
        assertEquals(actual, expected);

    }

    @Test
    public void blockUser_shouldCallUserRepository() {
        //Arrange
        user.setStatus(false);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));
        //Act
        UserServiceModel userServiceModel = userService.blockUser(testUserServiceModel);


        //Assert
        boolean actual = false;
        boolean expected = userServiceModel.isStatus();

        assertEquals(actual, expected);

    }

    @Test
    public void activateUser_shouldCallUserRepository() {
        //Arrange
        user.setStatus(true);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));
        //Act
        UserServiceModel userServiceModel = userService.activateUser(testUserServiceModel);

        //Assert
        boolean actual = true;
        boolean expected = userServiceModel.isStatus();

        assertEquals(actual, expected);
    }


}