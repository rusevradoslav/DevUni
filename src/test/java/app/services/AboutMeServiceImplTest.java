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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
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
    private AboutMe aboutMe;
    private AboutMeServiceModel testAboutMeServiceModel;

    @InjectMocks
    private AboutMeServiceImpl aboutMeService;
    @Mock
    private AboutMeRepository aboutMeRepository;
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;
    private List<AboutMe> testAboutMeRepository;


    @Before
    public void init() {
        ModelMapper actualMapper = new ModelMapper();

        when(aboutMeRepository.saveAndFlush(isA(AboutMe.class)))
                .thenAnswer(invocation -> {

                    testAboutMeRepository.add((AboutMe) invocation.getArguments()[0]);
                    return null;
                });

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

        testAboutMeRepository = new ArrayList<>();

        user = new User();
        user.setId("validUserAId");
        user.setUsername("validUsername");

        userServiceModel = this.modelMapper.map(user, UserServiceModel.class);

        aboutMe = new AboutMe();
        aboutMe.setId(VALID_ID);
        aboutMe.setKnowledgeLevel(VALID_KNOWLEDGE_LEVEL);
        aboutMe.setSelfDescription(VALID_DESCRIPTION);

        testAboutMeServiceModel = this.modelMapper.map(aboutMe, AboutMeServiceModel.class);
    }

    @Test
    public void addTeacherAboutMe_ShouldCreateNewAboutMeForTeacher_WhenTeacherDoesNOTHaveAboutMeAndCallAboutMeRepository() {
        //Arrange
        when(aboutMeRepository.findById(VALID_ID)).thenReturn(Optional.of(aboutMe));
        userServiceModel.setAboutMeServiceModel(testAboutMeServiceModel);
        //Act
        AboutMeServiceModel aboutMeServiceModel = aboutMeService.addTeacherAboutMe(userServiceModel, testAboutMeServiceModel);

        //Assert
        String actual = VALID_ID;
        String expected = aboutMeServiceModel.getId();
        assertEquals(actual, expected);
    }

    @Test
    public void addTeacherAboutMe_ShouldUpdateAboutMeForTeacher_WhenTeacherHaveAboutMeAndCallAboutMeRepository() {

        //Arrange
        userServiceModel.setAboutMeServiceModel(null);

        //Act
        aboutMeService.addTeacherAboutMe(userServiceModel, testAboutMeServiceModel);

        //Assert
        int actual = 1;
        int expected = testAboutMeRepository.size();
        assertEquals(actual, expected);
    }

}