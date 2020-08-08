package app.models.binding.assignment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@NoArgsConstructor
public class AssignmentSolutionAddBindingModel {

    private MultipartFile file;
}
