package app.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class LectureAddBindingModel {


    @Length(min = 10, max = 40, message = "Title must be between 10 and 40 characters !")
    private String title;

    @Length(min = 50, max = 150, message = "Description must be between 50 and 150 characters")
    private String description;


    @Pattern(regexp = "^(?:https?:\\/\\/)?(?:www\\.)?(?:youtu\\.be\\/|youtube\\.com\\/(?:embed\\/|v\\/|watch\\?v=|watch\\?.+&v=)).*$"
            , message = "Invalid YouTube URL ! " +
            "YouTube URL must looks like https://www.youtube.com/watch?v={video id }")
    private String lectureVideoUrl;


    private MultipartFile resources;
}
