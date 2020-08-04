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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

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

        MultipartFile resources = lectureServiceModel.getResources();
        CustomFile customFile = customFileService.storeFile(resources);
        lecture.setResources(customFile);

        Lecture newLecture = this.lectureRepository.save(lecture);



        return this.modelMapper.map(newLecture, LectureServiceModel.class);
    }

    @Override
    public String findLectureResourcesIDByLectureId(String lectureId) {
        Lecture lecture = this.lectureRepository.findById(lectureId).orElseThrow(() -> new LectureNotFoundException("Lecture with given id was not found !"));
        return lecture.getResources().getId();
    }
}
