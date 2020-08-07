package app.services.impl;

import app.error.FileStorageException;
import app.error.LectureNotFoundException;
import app.models.entity.Course;
import app.models.entity.CustomFile;
import app.models.entity.Lecture;
import app.models.service.CourseServiceModel;
import app.models.service.LectureServiceModel;
import app.repositories.LectureRepository;
import app.services.CustomFileService;
import app.services.LectureService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LectureServiceImpl implements LectureService {
    private final LectureRepository lectureRepository;
    private final CustomFileService customFileService;
    private final ModelMapper modelMapper;

    @Override
    public LectureServiceModel addLecture(CourseServiceModel courseServiceModel, LectureServiceModel lectureServiceModel) throws FileStorageException {

        Lecture lecture = this.modelMapper.map(lectureServiceModel, Lecture.class);
        Course course = this.modelMapper.map(courseServiceModel, Course.class);
        lecture.setCourse(course);


        String code = extractYouTubeCode(lecture);

        lecture.setLectureVideoUrl(code);

        MultipartFile resources = lectureServiceModel.getResources();
        CustomFile customFile = customFileService.storeFile(resources);
        lecture.setResources(customFile);

        Lecture newLecture = this.lectureRepository.save(lecture);


        return this.modelMapper.map(newLecture, LectureServiceModel.class);
    }

    @Nullable
    private String extractYouTubeCode(Lecture lecture) {
        Pattern pattern = Pattern.compile("^(?:https?:\\/\\/)?(?:www\\.)?(?:youtu\\.be\\/|youtube\\.com\\/(?:embed\\/|v\\/|watch\\?v=|watch\\?.+&v=))(?<myGroup>.*)$");
        String lectureVideoUrl = lecture.getLectureVideoUrl();
        Matcher matcher = pattern.matcher(lectureVideoUrl);
        String nameStr = null;
        if (matcher.find()) {
            nameStr = matcher.group("myGroup");

        }
        return nameStr;
    }

    @Override
    public String findLectureResourcesIDByLectureId(String lectureId) {
        Lecture lecture = this.lectureRepository.findById(lectureId).orElseThrow(() -> new LectureNotFoundException("Lecture with given id was not found !"));
        return lecture.getResources().getId();
    }

    @Override
    public LectureServiceModel findLectureById(String lectureId) {
        Lecture lecture = this.lectureRepository.findById(lectureId).orElseThrow(() -> new LectureNotFoundException("Lecture with given id was not found !"));

        return this.modelMapper.map(lecture, LectureServiceModel.class);
    }


}
