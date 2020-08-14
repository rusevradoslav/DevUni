package app.models.service;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class AssignmentServiceModel extends BaseServiceModel {

    private String description;

    private double gradePercentage;

    private MultipartFile file;

    private UserServiceModel user;

    private LectureServiceModel lecture;

    private boolean checked;

}
