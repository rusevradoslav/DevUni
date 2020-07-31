package app.services;

import app.error.TopicNotFoundException;
import app.models.entity.Topic;
import app.models.service.TopicServiceModel;
import app.repositories.TopicRepository;
import app.services.impl.TopicServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TopicServiceImplTest {
    private static final String TOPIC_C_SHARP = "C#";
    private static final String TOPIC_JAVA = "Java";
    private static final String TOPIC_JAVASCRIPT = "JavaScript";
    private static final String TOPIC_PYTHON = "Python";
    private static final String TOPIC_PHP = "Php";
    private static final String TOPIC_CPP = "C++";

    private static List<Topic> testTopicRepository = new ArrayList<>();
    Topic cSharp;
    Topic java;
    Topic javaScript;
    Topic python;
    Topic php;
    Topic cpp;
    @InjectMocks
    private TopicServiceImpl topicService;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private ModelMapper modelMapper;

    @Before
    public void setUp() {

        when(topicRepository.saveAndFlush(isA(Topic.class)))
                .thenAnswer(invocationOnMock -> {
                    testTopicRepository.add((Topic) invocationOnMock.getArguments()[0]);
                    return null;
                });

        //actual mappings
        ModelMapper actualMapper = new ModelMapper();
        when(modelMapper.map(any(Topic.class), eq(TopicServiceModel.class)))
                .thenAnswer(invocation -> actualMapper.map(invocation.getArguments()[0], TopicServiceModel.class));


        cSharp = new Topic(TOPIC_C_SHARP);
        java = new Topic(TOPIC_JAVA);
        javaScript = new Topic(TOPIC_JAVASCRIPT);
        python = new Topic(TOPIC_PYTHON);
        php = new Topic(TOPIC_PHP);
        cpp = new Topic(TOPIC_CPP);

        when(topicRepository.findFirstByName(TOPIC_C_SHARP)).thenReturn(Optional.of(cSharp));
        when(topicRepository.findFirstByName(TOPIC_JAVA)).thenReturn(Optional.of(java));
        when(topicRepository.findFirstByName(TOPIC_JAVASCRIPT)).thenReturn(Optional.of(javaScript));
        when(topicRepository.findFirstByName(TOPIC_PYTHON)).thenReturn(Optional.of(python));
        when(topicRepository.findFirstByName(TOPIC_PHP)).thenReturn(Optional.of(php));
        when(topicRepository.findFirstByName(TOPIC_CPP)).thenReturn(Optional.of(cpp));


    }

    @Test
    public void seedTopicsInDB_shouldSeedTopicsInDb_whenTopicRepositoryIsEmpty() {
        //Arrange
        System.out.println();
        when(topicRepository.count()).thenReturn(0L);

        //Act
        topicService.seedTopicsInDb();

        //Assert
        int actual = 6;
        int expected = testTopicRepository.size();

        Topic cSharp = topicRepository.findFirstByName(TOPIC_C_SHARP).orElse(null);
        String cSharpTopicName = cSharp.getName();
        Topic java = topicRepository.findFirstByName(TOPIC_JAVA).orElse(null);
        String javaTopicName = java.getName();
        Topic javaScript = topicRepository.findFirstByName(TOPIC_JAVASCRIPT).orElse(null);
        String javaScriptTopicName = javaScript.getName();
        Topic python = topicRepository.findFirstByName(TOPIC_PYTHON).orElse(null);
        String pythonTopicName = python.getName();
        Topic php = topicRepository.findFirstByName(TOPIC_PHP).orElse(null);
        String phpTopicName = php.getName();
        Topic cpp = topicRepository.findFirstByName(TOPIC_CPP).orElse(null);
        String cppTopicName = cpp.getName();

        assertEquals(actual, expected);
        assertEquals(cSharpTopicName, TOPIC_C_SHARP);
        assertEquals(javaTopicName, TOPIC_JAVA);
        assertEquals(javaScriptTopicName, TOPIC_JAVASCRIPT);
        assertEquals(pythonTopicName, TOPIC_PYTHON);
        assertEquals(phpTopicName, TOPIC_PHP);
        assertEquals(cppTopicName, TOPIC_CPP);
    }


    @Test
    public void seedTopicsInDB_shouldNotSeedTopicsInDb_whenTopicRepositoryIsNoEmpty() {
        //Arrange
        when(topicRepository.count()).thenReturn(6L);

        //Act
        topicService.seedTopicsInDb();

        //Assert
        long actual = 6L;
        long expected = this.topicRepository.count();

        assertEquals(actual, expected);

    }

    @Test
    public void findTopicServiceByName_shouldReturnCorrectTopicIfExist() {
        //Arrange
        when(topicRepository.findFirstByName(TOPIC_JAVA)).thenReturn(Optional.of(java));
        //Act
        TopicServiceModel topicServiceModel = this.topicService.findTopicServiceByName(TOPIC_JAVA);
        //Assert
        String actual = TOPIC_JAVA;
        String expected = topicServiceModel.getName();

        assertEquals(actual, expected);
    }

    @Test(expected = TopicNotFoundException.class)
    public void findTopicServiceByName_shouldThrowException() {
        //Arrange
        when(topicRepository.findFirstByName(TOPIC_JAVA)).thenReturn(Optional.empty());

        //Act
        //Assert
        TopicServiceModel topicServiceModel = this.topicService.findTopicServiceByName(TOPIC_JAVA);

    }

    @Test
    public void findTopicByName_shouldReturnCorrectTopicIfExist() {
        //Arrange
        when(topicRepository.findFirstByName(TOPIC_JAVA)).thenReturn(Optional.of(java));
        //Act
        Topic topic = this.topicService.findTopicByName(TOPIC_JAVA);
        //Assert
        String actual = TOPIC_JAVA;
        String expected = topic.getName();

        assertEquals(actual, expected);
    }

    @Test(expected = TopicNotFoundException.class)
    public void findTopicByName_shouldThrowException() {
        //Arrange
        when(topicRepository.findFirstByName(TOPIC_JAVA)).thenReturn(Optional.empty());

        //Act
        //Assert
        Topic topic = this.topicService.findTopicByName(TOPIC_JAVA);

    }

    @Test
    public void findAllTopics_shouldReturnAllTopics() {
        //Arrange
        when(topicRepository.findAll()).thenReturn(Arrays.asList(cSharp, java, javaScript, python, php, cpp));

        //Act
        List<TopicServiceModel> allTopics = this.topicService.findAllTopics();

        int actual = 6;
        int expected = allTopics.size();

        //Assert

        assertEquals(actual, expected);
    }
    @Test
    public void findAllTopicNames_shouldReturnAllTopics() {
        //Arrange
        when(topicRepository.findAll()).thenReturn(Arrays.asList(cSharp, java, javaScript, python, php, cpp));

        //Act
        List<String> allTopics = this.topicService.getAllTopicNames();

        //Assert
        assertEquals(TOPIC_C_SHARP, allTopics.get(0));
        assertEquals(TOPIC_JAVA, allTopics.get(1));
        assertEquals(TOPIC_JAVASCRIPT, allTopics.get(2));
        assertEquals(TOPIC_PYTHON, allTopics.get(3));
        assertEquals(TOPIC_PHP, allTopics.get(4));
        assertEquals(TOPIC_CPP, allTopics.get(5));
    }


}