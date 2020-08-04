package app.models.service;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentServiceModel extends BaseServiceModel {

    private String description;

    private MultipartFile file;

    private UserServiceModel user;

    private LectureServiceModel lecture;

}
