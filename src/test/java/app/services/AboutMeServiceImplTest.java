package app.services;

        import app.models.entity.AboutMe;
import app.repositories.AboutMeRepository;
import app.services.impl.AboutMeServiceImpl;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.List;


@RunWith(MockitoJUnitRunner.class)
class AboutMeServiceImplTest {
    private static String VALID_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
            " Donec id arcu eu lacus semper gravida vulputate ut " +
            "erat. Mauris ultricies nisl id justo nisi.";

    private static List<AboutMe> fakeRepository;

    @InjectMocks
    private AboutMeServiceImpl aboutMeService;
    @Mock
    private AboutMeRepository aboutMeRepository;
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

/*

    @Before
    public void init() {
        fakeRepository = new ArrayList<>();
        when(aboutMeRepository.saveAndFlush(isA(AboutMe.class)))
                .thenAnswer(invocation -> {
                    fakeRepository.add((AboutMe) invocation.getArguments()[0]);
                    return null;
                });
    }
*/

/*    @Test
public void addTeacherAboutMe_shouldCreateNewAboutMe_WhenUserAboutMeIsNull() throws RoleNotFoundException {
    AboutMe aboutMe = new AboutMe("Java Master", VALID_DESCRIPTION);
    UserServiceModel user = new UserServiceModel();
    user.setUsername("username");
    user.setFirstName("firstName");
    user.setLastName("lastName");
    user.setEmail("valid@gmail.com");
    user.setPassword("validPassword");
    user.setAuthorities(new HashSet<>());
    user.setRegistrationDate(LocalDateTime.now());
    user.setProfilePicture("validProfilePicture");
    System.out.println();
    AboutMeServiceModel aboutMeServiceModel = this.modelMapper.map(aboutMe, AboutMeServiceModel.class);


    aboutMeService.addTeacherAboutMe(user, aboutMeServiceModel);

    System.out.println();

    int expected = 1;
    int actual = fakeRepository.size();

    assertEquals(expected, actual);
    assertEquals(fakeRepository.get(0).getKnowledgeLevel(), aboutMe.getKnowledgeLevel());

}*/
}