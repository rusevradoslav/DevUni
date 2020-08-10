package app.services;

import app.error.InvalidEmailException;
import app.error.UserAlreadyExistException;
import app.error.UserNotFoundException;
import app.models.entity.*;
import app.models.service.AboutMeServiceModel;
import app.models.service.CourseServiceModel;
import app.models.service.RoleServiceModel;
import app.models.service.UserServiceModel;
import app.models.view.AboutMeViewModel;
import app.models.view.UserDetailsViewModel;
import app.repositories.UserRepository;
import app.services.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.RoleNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static app.models.entity.Difficulty.ADVANCE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
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
    private Course course;


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

        when(modelMapper.map(any(Course.class), eq(CourseServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], CourseServiceModel.class));

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

        aboutMe = new AboutMe();
        aboutMe.setId(VALID_ABOUT_ME_ID);
        aboutMe.setKnowledgeLevel(VALID_KNOWLEDGE_LEVEL);
        aboutMe.setSelfDescription(VALID_SELF_DESCRIPTION);


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

        course = getCourse();

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
        when(userRepository.save(any(User.class))).thenReturn(user);
        //Act
        UserServiceModel userServiceModel = userService.sentTeacherRequest(testUserServiceModel);

        //Assert
        boolean actual = true;
        boolean expected = userServiceModel.isTeacherRequest();

        assertEquals(actual, expected);

    }

    @Test(expected = UserNotFoundException.class)
    public void sentTeacherRequest_shouldThrowUseNotFoundException() {
        //Arrange
        user.setTeacherRequest(true);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.empty());
        //Act
        UserServiceModel userServiceModel = userService.sentTeacherRequest(testUserServiceModel);

        //Assert


    }

    @Test
    public void confirmTeacherRequest_shouldCallUserRepository() throws RoleNotFoundException {
        //Arrange
        Role role = new Role("ROLE_TEACHER");
        when(roleService.findAuthorityByName("ROLE_TEACHER")).thenReturn(role);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
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
        when(userRepository.save(any(User.class))).thenReturn(user);
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
        when(userRepository.save(any(User.class))).thenReturn(user);

        //Act
        UserServiceModel userServiceModel = userService.changePassword(testUserServiceModel, VALID_NEW_PASSWORD);

        //Assert
        String actual = user.getPassword();
        String expected = userServiceModel.getPassword();

        assertEquals(actual, expected);
    }

    @Test(expected = UserNotFoundException.class)
    public void changePassword_shouldThrowUserNotFoundException() {
        //Arrange
        String newEncodedPassword = bCryptPasswordEncoder.encode(VALID_NEW_PASSWORD);
        user.setPassword(newEncodedPassword);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.empty());


        //Act
        UserServiceModel userServiceModel = userService.changePassword(testUserServiceModel, VALID_NEW_PASSWORD);


    }

    @Test
    public void changeEmail_shouldChangeEmailAndCallUserRepository() {
        //Arrange
        user.setEmail(VALID_NEW_EMAIL);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        //Act
        UserServiceModel userServiceModel = userService.changeEmail(testUserServiceModel, VALID_NEW_EMAIL);
        //Assert
        String actual = VALID_NEW_EMAIL;
        String expected = userServiceModel.getEmail();

        assertEquals(actual, expected);
    }

    @Test(expected = UserNotFoundException.class)
    public void changeEmail_shouldThrowUserNotFoundException() {
        //Arrange
        user.setEmail(VALID_NEW_EMAIL);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.empty());

        //Act
        UserServiceModel userServiceModel = userService.changeEmail(testUserServiceModel, VALID_NEW_EMAIL);

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
        Role role = new Role();
        role.setId("VALID_ID");
        role.setAuthority("ROLE_STUDENT");
        authorities.add(role);
        User newUser = new User();
        newUser.setUsername("NewUserUsername");
        newUser.setFirstName("NewUserFirstName");
        newUser.setLastName("NewUserLastName");
        newUser.setRegistrationDate(LocalDateTime.now());
        newUser.setAuthorities(authorities);
        when(userRepository.findAllStudents()).thenReturn(Arrays.asList(newUser));
        //Act
        List<UserDetailsViewModel> allAdmins = this.userService.findAllStudents();

        //Assert
        String actual = "NewUserUsername";
        String expected = allAdmins.stream().findFirst().get().getUsername();
        assertEquals(actual, expected);

    }

    @Test
    public void findAllStudentsWithTeacherRequests_shouldReturnAllStudentsWithTeacherRequestsAndCallUserRepository() {
        //Arrange
        Role role = new Role();
        role.setId("VALID_ID");
        role.setAuthority("ROLE_TEACHER");
        authorities.add(role);
        User first = new User();
        first.setUsername("First");
        first.setLastName("First");
        first.setRegistrationDate(LocalDateTime.now());
        first.setTeacherRequest(true);
        first.setAuthorities(authorities);
        when(userRepository.findAllStudentsWithRequests()).thenReturn(Arrays.asList(first));
        //Act
        List<UserDetailsViewModel> allAdmins = this.userService.findAllStudentsWithRequests();

        //Assert
        String actual = "First";
        String expected = allAdmins.stream().findFirst().get().getUsername();
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
        when(userRepository.save(any(User.class))).thenReturn(user);
        //Act
        UserServiceModel userServiceModel = userService.blockUser(testUserServiceModel);


        //Assert
        boolean actual = false;
        boolean expected = userServiceModel.isStatus();

        assertEquals(actual, expected);

    }

    @Test(expected = UserNotFoundException.class)
    public void blockUser_shouldThrowUserNotFoundException() {
        //Arrange
        user.setStatus(false);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.empty());

        //Act
        UserServiceModel userServiceModel = userService.blockUser(testUserServiceModel);


    }

    @Test
    public void activateUser_shouldCallUserRepository() {
        //Arrange
        user.setStatus(true);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        //Act
        UserServiceModel userServiceModel = userService.activateUser(testUserServiceModel);

        //Assert
        boolean actual = true;
        boolean expected = userServiceModel.isStatus();

        assertEquals(actual, expected);
    }

    @Test(expected = UserNotFoundException.class)
    public void activateUser_shouldThrowUserNotFoundException() {
        //Arrange
        user.setStatus(true);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.empty());


        //Act
        UserServiceModel userServiceModel = userService.activateUser(testUserServiceModel);


    }

    @Test
    public void setAboutMeToTheTeacher_shouldReturnUserWithGivenAboutMe() {

        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserServiceModel userServiceModel = this.userService.setAboutMeToTheTeacher(aboutMe, user);

        String actual = VALID_ABOUT_ME_ID;
        String expected = userServiceModel.getAboutMeServiceModel().getId();

        assertEquals(actual, expected);

    }

    @Test(expected = UserNotFoundException.class)
    public void setAboutMeToTheTeacher_shouldThrowUserNotFoundException() {

        when(userRepository.findById(VALID_ID)).thenReturn(Optional.empty());

        UserServiceModel userServiceModel = this.userService.setAboutMeToTheTeacher(aboutMe, user);


    }

    @Test
    public void findTeacher_shouldReturnTeachersWithAboutMe() {

        AboutMe firstAboutMe = new AboutMe();
        aboutMe.setId("firstAboutMe");
        Set<Role> roles = new HashSet<>();
        Role role = new Role("ROLE_TEACHER");
        roles.add(role);
        User user = new User();
        user.setId(VALID_ID);
        user.setUsername("First");
        user.setLastName("First");
        user.setRegistrationDate(LocalDateTime.now());
        user.setAboutMe(firstAboutMe);
        user.setAuthorities(roles);

        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));
        //Act
        UserDetailsViewModel teacher = userService.findTeacher(VALID_ID);

        //Assert
        String actual = "First";
        String expected = teacher.getUsername();
        assertEquals(actual, expected);
    }

    @Test
    public void findTeacher_shouldReturnTeachersWithoutAboutMe() {

        Set<Role> roles = new HashSet<>();
        Role role = new Role("ROLE_TEACHER");
        roles.add(role);
        User user = new User();
        user.setId(VALID_ID);
        user.setUsername("First");
        user.setLastName("First");
        user.setRegistrationDate(LocalDateTime.now());
        user.setAboutMe(null);
        user.setAuthorities(roles);

        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));
        //Act
        UserDetailsViewModel teacher = userService.findTeacher(VALID_ID);

        //Assert

        AboutMeViewModel expected = teacher.getAboutMeViewModel();
        assertEquals(null, expected);
    }

    @Test(expected = UserNotFoundException.class)
    public void findTeacher_shouldThrowExceptionIfTeacherDoesNotExist() {
        //Arrange
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.empty());
        //Act
        UserDetailsViewModel teacher = userService.findTeacher(VALID_ID);


    }

    @Test
    public void findAllCoursesByUserId_shouldReturnAllEnrolledCoursesFromUser() {
        //Arrange
        Set<Course> enrolledCourses = new HashSet<>();
        Course course = getCourse();
        enrolledCourses.add(course);
        user.setEnrolledCourses(enrolledCourses);
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));

        //Act
        List<CourseServiceModel> allCoursesByUserId = this.userService.findAllCoursesByUserId(VALID_ID);

        //Assert
        int actual = 1;
        int expected = allCoursesByUserId.size();
        assertEquals(actual, expected);
    }

    @Test(expected = UserNotFoundException.class)
    public void findAllCoursesByUserId_shouldThrowUserNotFoundException() {
        //Arrange
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.empty());

        //Act
        List<CourseServiceModel> allCoursesByUserId = this.userService.findAllCoursesByUserId(VALID_ID);


    }

    @Test
    public void findAllCompletedCourses_shouldReturnAllCompletedCoursesByUser() {
        //Arrange
        user.setCompletedCourses(Set.of(course));
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));

        //Act
        List<CourseServiceModel> allCompletedCourses = userService.findAllCompletedCourses(VALID_ID);

        //Assert
        assertEquals(1, allCompletedCourses.size());

    }

    @Test(expected = UserNotFoundException.class)
    public void findAllCompletedCourses_shouldThrowExceptionWhenUserDoesNotExist() {
        //Arrange
        user.setCompletedCourses(Set.of(course));
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.empty());

        //Act
        List<CourseServiceModel> allCompletedCourses = userService.findAllCompletedCourses(VALID_ID);


    }

    @Test
    public void updateUser_shouldReturnAllCompletedCoursesByUser() {

        //Arrange
        user.setCompletedCourses(new HashSet<>());
        user.setEnrolledCourses(new HashSet<>());
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        //Act
        UserServiceModel userServiceModel = userService.updateUser(VALID_ID, course);

        //Assert
        assertEquals(1, userServiceModel.getCompletedCourses().size());
        assertEquals(0, userServiceModel.getEnrolledCourses().size());

    }

    @Test
    public void addProfilePicture_shouldUpdateDefaultProfilePicture() throws IOException {
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserServiceModel userServiceModel = this.userService.addProfilePicture(testUserServiceModel, multipartFile);

        String actual = VALID_USERNAME;
        String expected = userServiceModel.getUsername();
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