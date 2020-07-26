package app.services;

import app.models.entity.AboutMe;
import app.models.entity.User;
import app.models.service.AboutMeServiceModel;
import app.models.service.UserServiceModel;
import app.repositories.AboutMeRepository;
import app.services.impl.AboutMeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AboutMeServiceImplTest {
    private static String VALID_ID = "validId";
    private static String VALID_KNOWLEDGE_LEVEL = "Java Master";
    private static String VALID_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
            " Donec id arcu eu lacus semper gravida vulputate ut " +
            "erat. Mauris ultricies nisl id justo nisi.";

    private User user;
    private UserServiceModel userServiceModel;
    private AboutMeServiceModel aboutMeServiceModel;

    @InjectMocks
    private AboutMeServiceImpl aboutMeService;
    @Mock
    private AboutMeRepository aboutMeRepository;
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;


    @Before
    public void init() {
        ModelMapper actualMapper = new ModelMapper();
        when(modelMapper.map(any(UserServiceModel.class), eq(User.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], User.class));

        when(modelMapper.map(any(User.class), eq(UserServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], UserServiceModel.class));

        when(modelMapper.map(any(AboutMeServiceModel.class), eq(AboutMe.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], AboutMe.class));

        when(modelMapper.map(any(AboutMe.class), eq(AboutMeServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], AboutMeServiceModel.class));


        user = new User();
        user.setId("validUserAId");
        user.setUsername("validUsername");

        userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
    }

    @Test
    public void addTeacherAboutMe_ShouldCreateNewAboutMeForTeacher_WhenTeacherDoesNOTHaveAboutMeAndCallAboutMeRepository() {
        AboutMeServiceModel aboutMeServiceModel = new AboutMeServiceModel();
        aboutMeServiceModel.setId(VALID_ID);
        aboutMeServiceModel.setKnowledgeLevel(VALID_KNOWLEDGE_LEVEL);
        aboutMeServiceModel.setId(VALID_DESCRIPTION);
        aboutMeService.addTeacherAboutMe(userServiceModel, aboutMeServiceModel);
    }
    @Test
    public void addTeacherAboutMe_ShouldUpdateAboutMeForTeacher_WhenTeacherHaveAboutMeAndCallAboutMeRepository(){
        AboutMeServiceModel aboutMeServiceModel = new AboutMeServiceModel();
        aboutMeServiceModel.setId(VALID_ID);
        aboutMeServiceModel.setKnowledgeLevel(VALID_KNOWLEDGE_LEVEL);
        aboutMeServiceModel.setId(VALID_DESCRIPTION);
        userServiceModel.setAboutMeServiceModel(aboutMeServiceModel);
        aboutMeService.addTeacherAboutMe(userServiceModel, aboutMeServiceModel);
    }

}