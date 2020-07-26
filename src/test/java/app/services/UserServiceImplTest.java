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
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.RoleNotFoundException;
import java.io.IOException;
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
    private static final String VALID_EDITED_PASSWORD = "12345";
    private static final String VALID_FIRST_NAME = "Radoslav";
    private static final String VALID_LAST_NAME = "Rusev";
    private static final String VALID_EMAIL = "radorusevcrypto@gmail.com";
    private static final String INVALID_EMAIL = "invalid@gmail.com";
    private static final String VALID_EDITED_EMAIL = "radorusevcrypto_new@gmail.com";
    private static final String USERNAME_EXIST_EXCEPTION = "There is an account with that username : rusevrado";
    private static final String EMAIL_EXIST_EXCEPTION = "There is an account with that email address: radorusevcrypto@gmail.com";

    private static final String VALID_IMAGE_URL = "[random_url]";


    private static final String WRONG_OLD_PASSWORD = "[wrong_old_password]";

    private static final String VALID_ABOUT_ME_ID = "validAboutMeId";
    private static final String VALID_KNOWLEDGE_LEVEL = "Java Developer";
    private static final String VALID_SELF_DESCRIPTION = "Java Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus id massa fermentum nullam sodales.";

    Set<Role> authorities = new HashSet<>();

    private List<User> fakeRepository;

    private User user;
    private UserServiceModel fakeUserServiceModel;
    private UserDetailsViewModel fakeUserDetailsViewModel;

    private AboutMe aboutMe;
    private AboutMeServiceModel fakeAboutMeServiceModel;


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

        when(modelMapper.map(any(User.class), eq(UserDetailsViewModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], UserDetailsViewModel.class));

        when(modelMapper.map(any(AboutMe.class), eq(AboutMeServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], AboutMeServiceModel.class));

        when(bCryptPasswordEncoder.encode(any())).thenAnswer(invocationOnMock -> actualEncoder.encode((CharSequence) invocationOnMock.getArguments()[0]));

        when(bCryptPasswordEncoder.matches(any(), any())).thenAnswer(invocationOnMock ->
                actualEncoder.matches((String) invocationOnMock.getArguments()[0], (String) invocationOnMock.getArguments()[1]));


        when(roleService.findByAuthority(anyString()))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(new Role((String) invocationOnMock.getArguments()[0]),
                                RoleServiceModel.class));

        when(userRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);


        authorities.add(new Role("ROLE_ROOT_ADMIN"));

        aboutMe = new AboutMe();
        aboutMe.setId(VALID_ABOUT_ME_ID);
        aboutMe.setKnowledgeLevel(VALID_KNOWLEDGE_LEVEL);
        aboutMe.setSelfDescription(VALID_SELF_DESCRIPTION);


        fakeAboutMeServiceModel = modelMapper.map(aboutMe, AboutMeServiceModel.class);


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

        fakeUserServiceModel = modelMapper.map(user, UserServiceModel.class);
        fakeUserServiceModel.setAboutMeServiceModel(fakeAboutMeServiceModel);

        fakeUserDetailsViewModel = this.modelMapper.map(user, UserDetailsViewModel.class);


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

        when(userRepository.findFirstByUsername(fakeUserServiceModel.getUsername())).thenReturn(Optional.of(user));


        userService.registerNewUserAccount(fakeUserServiceModel);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void registerNewUser_shouldThrowException_WhenEmailAlreadyExist() throws RoleNotFoundException, UserAlreadyExistException {

        when(userRepository.findFirstByEmail(fakeUserServiceModel.getEmail())).thenReturn(Optional.of(user));

        userService.registerNewUserAccount(fakeUserServiceModel);

    }

    @Test
    public void registerNewUser_shouldRegisterUser_whenUserDoesNotExistInDB() throws RoleNotFoundException {
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.empty());

        userService.registerNewUserAccount(fakeUserServiceModel);

        int actual = 1;
        int expected = fakeRepository.size();

        assertEquals(actual, expected);

    }

    @Test
    public void registerFirstUser_shouldHaveRole_ROLE_ROOT_ADMIN() throws RoleNotFoundException {

        when(userRepository.count()).thenReturn(0L);
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.empty());

        userService.registerNewUserAccount(fakeUserServiceModel);


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

        userService.registerNewUserAccount(fakeUserServiceModel);


        User user = fakeRepository.get(0);
        String actualRole = "ROLE_STUDENT";
        String expectedRole = user.getAuthorities().stream().findFirst().get().getAuthority();

        assertEquals(actualRole, expectedRole);

    }

    @Test(expected = UserAlreadyExistException.class)
    public void createNewAdmin_shouldThrowException_WhenUsernameAlreadyExist() throws RoleNotFoundException, UserAlreadyExistException {

        when(userRepository.findFirstByUsername(fakeUserServiceModel.getUsername())).thenReturn(Optional.of(user));


        userService.createNewAdminAccount(fakeUserServiceModel);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void createNewAdmin_shouldThrowException_WhenEmailAlreadyExist() throws RoleNotFoundException, UserAlreadyExistException {

        when(userRepository.findFirstByEmail(fakeUserServiceModel.getEmail())).thenReturn(Optional.of(user));

        userService.createNewAdminAccount(fakeUserServiceModel);

    }

    @Test
    public void createNewAdmin_shouldThrowExcept_WhenUserAlreadyExist() throws RoleNotFoundException {
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.empty());

        userService.createNewAdminAccount(fakeUserServiceModel);

        User user = fakeRepository.get(0);
        String actualRole = "ROLE_ADMIN";
        String expectedRole = user.getAuthorities().stream().findFirst().get().getAuthority();
        System.out.println();
        assertEquals(actualRole, expectedRole);

    }

    @Test
    public void findById_shouldReturnCorrectUser_WhenIdExist() {
        when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(user));
        UserServiceModel user2 = this.userService.findById(VALID_ID);

        String actual = VALID_ID;
        String expected = user2.getId();


        assertEquals(actual, expected);


    }


    @Test
    public void findByEmail_shouldReturnCorrectUser_WhenEmailExist() {
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.of(user));
        UserServiceModel user2 = this.userService.findByEmail(VALID_EMAIL);

        String actual = VALID_EMAIL;
        String expected = user2.getEmail();


        assertEquals(actual, expected);


    }

    @Test(expected = InvalidEmailException.class)
    public void findByEmail_shouldThrowException_WhenEmailDoesNotExist() {
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
        this.userService.findByEmail(VALID_EMAIL);

    }


    @Test
    public void findByName_shouldReturnCorrectUserWithAboutMe_WhenUserNameExist() {
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.of(user));

        UserServiceModel userServiceModel = userService.findByName(VALID_USERNAME);

        String actual = fakeAboutMeServiceModel.getKnowledgeLevel();

        String expected = userServiceModel.getAboutMeServiceModel().getKnowledgeLevel();

        assertEquals(actual, expected);


    }

    @Test
    public void findByName_shouldReturnCorrectUserWithNullAboutMe_WhenUserNameExist() {
        System.out.println();
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.of(user));
        user.setAboutMe(null);
        UserServiceModel userServiceModel = userService.findByName(VALID_USERNAME);

        String actual = null;

        AboutMeServiceModel expected = userServiceModel.getAboutMeServiceModel();

        assertEquals(actual, expected);


    }

    @Test(expected = UsernameNotFoundException.class)
    public void findByName_shouldThrowException_WhenUsernameDoesNotExist() {
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.empty());
        System.out.println();
        this.userService.findByName(VALID_USERNAME);

    }

    @Test
    public void getUserDetailsByUsername_shouldReturnCorrectUser_WhenEmailExist() {
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.of(user));

        UserDetailsViewModel userDetailsViewModel = this.userService.getUserDetailsByUsername(VALID_USERNAME);

        String actual = VALID_USERNAME;

        String expected = userDetailsViewModel.getUsername();

        assertEquals(actual, expected);

    }

    @Test(expected = UsernameNotFoundException.class)
    public void getUserDetailsByUsername_shouldThrowException_WhenEmailDoesNotExist() {
        when(userRepository.findFirstByUsername(VALID_USERNAME)).thenReturn(Optional.empty());
        this.userService.getUserDetailsByUsername(VALID_USERNAME);
    }

    @Test
    public void sentTeacherRequest_shouldCallUserRepository() {
        userService.sentTeacherRequest(fakeUserServiceModel);

    }

    @Test
    public void confirmTeacherRequest_shouldCallUserRepository() throws RoleNotFoundException {
        Role role = new Role("ROLE_TEACHER");
        when(roleService.findAuthorityByName("ROLE_TEACHER")).thenReturn(role);
        userService.confirmTeacherRequest(fakeUserServiceModel);

    }

    @Test
    public void changeRoleToTeacher_shouldCallUserRepository() throws RoleNotFoundException {
        Role role = new Role("ROLE_TEACHER");
        when(roleService.findAuthorityByName("ROLE_TEACHER")).thenReturn(role);
        userService.changeRoleToTeacher(fakeUserServiceModel);

    }

    @Test
    public void changeRoleToStudent_shouldCallUserRepository() throws RoleNotFoundException {
        Role role = new Role("ROLE_STUDENT");
        when(roleService.findAuthorityByName("ROLE_STUDENT")).thenReturn(role);
        userService.changeRoleToStudent(fakeUserServiceModel);

    }

    @Test
    public void changeRoleToAdmin_shouldCallUserRepository() throws RoleNotFoundException {
        Role role = new Role("ROLE_ADMIN");
        when(roleService.findAuthorityByName("ROLE_ADMIN")).thenReturn(role);
        userService.changeRoleToAdmin(fakeUserServiceModel);

    }

    @Test
    public void cancelTeacherRequest_shouldCallUserRepository() {
        userService.cancelTeacherRequest(fakeUserServiceModel);
    }

    @Test
    public void setAboutMeToTheTeacher_shouldUpdateTeacherAboutMeAndCallUserRepository() {
        userService.setAboutMeToTheTeacher(aboutMe, user);
    }

    @Test
    public void changePassword_shouldChangeUserPasswordAndCallUserRepository() {
        userService.changePassword(fakeUserServiceModel, VALID_EDITED_PASSWORD);
    }

    @Test
    public void changeEmail_shouldChangeEmailAndCallUserRepository() {
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.of(user));
        userService.changeEmail(fakeUserServiceModel, VALID_EDITED_EMAIL);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void changeEmail_shouldThrowUserAlreadyExistException() {
        when(userRepository.findFirstByEmail(VALID_EMAIL)).thenReturn(Optional.of(user));
        userService.changeEmail(fakeUserServiceModel, VALID_EMAIL);
    }

    @Test
    public void addProfilePicture_shouldChangeEmailAndCallUserRepository() {
        userService.changeEmail(fakeUserServiceModel, VALID_EDITED_EMAIL);
    }

    @Test
    public void blockUser_shouldCallUserRepository() {
        userService.blockUser(fakeUserServiceModel);
    }

    @Test
    public void activateUser_shouldCallUserRepository() {
        userService.activateUser(fakeUserServiceModel);
    }

    @Test
    public void findAllAdmins_shouldReturnAllAdminsAndCallUserRepository() {
        User newUser = new User();
        newUser.setFirstName("NewUserFirstName");
        newUser.setLastName("NewUserLastName");
        newUser.setRegistrationDate(LocalDateTime.now());
        when(userRepository.findAllAdmins()).thenReturn(Arrays.asList(user, newUser));
        List<UserDetailsViewModel> allAdmins = this.userService.findAllAdmins();

        int actual = 2;
        int expected = allAdmins.size();
        assertEquals(actual, expected);

    }

    @Test
    public void findAllStudents_shouldReturnAllStudentsAndCallUserRepository() {
        User newUser = new User();
        newUser.setFirstName("NewUserFirstName");
        newUser.setLastName("NewUserLastName");
        newUser.setRegistrationDate(LocalDateTime.now());
        when(userRepository.findAllStudents()).thenReturn(Arrays.asList(user, newUser));
        List<UserDetailsViewModel> allAdmins = this.userService.findAllStudents();

        int actual = 2;
        int expected = allAdmins.size();
        assertEquals(actual, expected);

    }

    @Test
    public void findAllStudentsWithTeacherRequests_shouldReturnAllStudentsWithTeacherRequestsAndCallUserRepository() {
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
        List<UserDetailsViewModel> allAdmins = this.userService.findAllStudentsWithRequests();
        int actual = 2;
        int expected = allAdmins.size();
        assertEquals(actual, expected);

    }

    @Test
    public void findAllTeachers_shouldReturnAllTeachersWithAboutMeAndCallUserRepository() {
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
        List<UserDetailsViewModel> allTeachers = userService.findAllTeachers();
        int actual = 2;
        int expected = allTeachers.size();
        assertEquals(actual, expected);

    }

    @Test
    public void findAllTeachers_shouldReturnAllTeachersWITHOUTAboutMeAndCallUserRepository() {
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
        List<UserDetailsViewModel> allTeachers = userService.findAllTeachers();
        int actual = 2;
        int expected = allTeachers.size();
        assertEquals(actual, expected);

    }


    @Test
    public void addProfilePicture_shouldAddProfilePictureToUserAndCallUserRepository() throws IOException {
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        this.userService.addProfilePicture(fakeUserServiceModel,multipartFile);
    }

}