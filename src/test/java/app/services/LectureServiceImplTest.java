package app.services;

import app.error.FileStorageException;
import app.error.LectureNotFoundException;
import app.models.entity.*;
import app.models.service.CourseServiceModel;
import app.models.service.LectureServiceModel;
import app.repositories.LectureRepository;
import app.services.impl.LectureServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static app.models.entity.Difficulty.ADVANCE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LectureServiceImplTest {
    private static final String VALID_COURSE_ID = "validCourseId";
    private static final String VALID_CUSTOM_FILE_ID = "VALID_CUSTOM_FILE_ID";
    private static final String VALID_COURSE_TITLE = "Java Master Class";
    private static final String VALID_SHORT_DESCRIPTION = "Curabitur cursus elementum nibh quis dignissim. Mauris tristique, tortor vel auctor elementum, lorem urna sed.";
    private static final String VALID_COURSE_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis nisi lectus, elementum in accumsan nec, porttitor vitae lacus. Nunc tempor sodales sem vitae porttitor. Nullam id iaculis sapien. Nunc non porttitor sapien, sed elementum sem. Suspendisse elementum hendrerit neque, vitae luctus ex interdum sit amet. Morbi eu congue arcu, sed fringilla dolor. Donec pharetra sem et turpis consectetur quis.";
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

    private static final String VALID_ID = "valid_Lecture_Id";
    private static final String VALID_TITLE = "Spring Security";
    private static final String VALID_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis nisi lectus, elementum in accumsan nec, porttitor vitae lacus. Nunc tempor sodales sem vitae porttitor. Nullam id iaculis sapien. Nunc non porttitor sapien, sed elementum sem. Suspendisse elementum hendrerit neque, vitae luctus ex interdum sit amet. Morbi eu congue arcu, sed fringilla dolor. Donec pharetra sem et turpis consectetur quis.";
    private static final String VALID_VIDEO_URL = "https://www.youtube.com/watch?v=xRE12Y-PFQs";

    @Mock
    private LectureRepository lectureRepository;
    @Mock
    private CustomFileService customFileService;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private LectureServiceImpl lectureService;

    private Lecture lecture;
    private CourseServiceModel courseServiceModel;
    private LectureServiceModel lectureServiceModel;
    private MultipartFile multipartFile;
    private CustomFile dbFile;


    @Before
    public void setUp() {
        ModelMapper actualMapper = new ModelMapper();
        when(modelMapper.map(any(LectureServiceModel.class), eq(Lecture.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], Lecture.class));

        when(modelMapper.map(any(Lecture.class), eq(LectureServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], LectureServiceModel.class));


        when(modelMapper.map(any(Course.class), eq(CourseServiceModel.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], CourseServiceModel.class));

        when(modelMapper.map(any(CourseServiceModel.class), eq(Course.class)))
                .thenAnswer(invocationOnMock ->
                        actualMapper.map(invocationOnMock.getArguments()[0], Course.class));

        dbFile = new CustomFile();
        dbFile.setId(VALID_CUSTOM_FILE_ID);
        dbFile.setData(new byte[]{11, 11});
        Course course = getCourse();
        lecture = new Lecture();
        lecture.setId(VALID_ID);
        lecture.setTitle(VALID_TITLE);
        lecture.setDescription(VALID_DESCRIPTION);
        lecture.setLectureVideoUrl(VALID_VIDEO_URL);
        lecture.setResources(dbFile);
        lecture.setCourse(course);
        courseServiceModel = this.modelMapper.map(course, CourseServiceModel.class);
        lectureServiceModel = this.modelMapper.map(lecture, LectureServiceModel.class);
        multipartFile = Mockito.mock(MultipartFile.class);
        lectureServiceModel.setResources(multipartFile);
    }

    @Test
    public void addLecture_shouldAddNewLectureToCourse() throws FileStorageException {
        //Arrange
        when(lectureRepository.save(lecture)).thenReturn(lecture);
        when(customFileService.storeFile(multipartFile)).thenReturn(dbFile);
        //Act

        this.lectureService.addLecture(courseServiceModel, this.lectureServiceModel);

        //Assert
        verify(this.lectureRepository, times(1)).save(Mockito.any(Lecture.class));

    }

    @Test
    public void findLectureResourcesIDByLectureId_shouldReturnResourceIdIfLectureExist() {
        //Arrange
        when(lectureRepository.findById(VALID_ID)).thenReturn(Optional.of(lecture));

        //Act
        String lectureResourcesIDByLectureId = this.lectureService.findLectureResourcesIDByLectureId(VALID_ID);

        //Assert
        String actual = VALID_CUSTOM_FILE_ID;
        String expected = lectureResourcesIDByLectureId;
        assertEquals(actual, expected);
    }

    @Test(expected = LectureNotFoundException.class)
    public void findLectureResourcesIDByLectureId_shouldThrowExceptionIfLectureDoesNotExist() {
        //Arrange
        when(lectureRepository.findById(VALID_ID)).thenReturn(Optional.empty());

        //Act
        String lectureResourcesIDByLectureId = this.lectureService.findLectureResourcesIDByLectureId(VALID_ID);

    }

    @Test
    public void findLectureByIdShouldReturnLecture_whenLectureExist(){
        //Arrange
        when(lectureRepository.findById(VALID_ID)).thenReturn(Optional.of(lecture));

        //Act
        LectureServiceModel lecture = lectureService.findLectureById(VALID_ID);

        //Assert
        String actual = VALID_ID;
        String expected = lecture.getId();

        assertEquals(actual,expected);
    }
    @Test(expected = LectureNotFoundException.class)
    public void findLectureByIdShouldReturnLecture_shouldThrowException(){
        //Arrange
        when(lectureRepository.findById(VALID_ID)).thenReturn(Optional.empty());

        //Act
        LectureServiceModel lecture = lectureService.findLectureById(VALID_ID);

    }

    private Course getCourse() {
        Course course = new Course();
        course.setId(VALID_COURSE_ID);
        course.setTitle(VALID_COURSE_TITLE);
        course.setShortDescription(VALID_SHORT_DESCRIPTION);
        course.setDescription(VALID_COURSE_DESCRIPTION);
        course.setCoursePhoto(VALID_COURSE_PHOTO);
        course.setPassPercentage(VALID_PASS_PERCENTAGE);
        course.setDurationWeeks(VALID_DURATION_WEEKS);
        course.setStatus(VALID_STATUS);
        course.setCourseRating(VALID_COURSE_RATING);
        course.setDifficulty(VALID_COURSE_DIFFICULTY);
        course.setStartedOn(LocalDateTime.now().plus(VALID_DURATION_WEEKS * 7, ChronoUnit.DAYS));
        course.setEndedON(LocalDateTime.now().plus(VALID_DURATION_WEEKS * 8, ChronoUnit.DAYS));
        course.setLectures(new ArrayList<>());
        course.setEnrolledStudents(new ArrayList<>());
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