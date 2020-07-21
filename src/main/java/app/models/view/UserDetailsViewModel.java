package app.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@Getter
@Setter
public class UserDetailsViewModel {

    private String id;
    private String username;
    private String fullName;
    private String email;
    private boolean status;
    private String profilePicture;
    private boolean teacherRequest;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String registrationDate;


    private AboutMeViewModel aboutMeViewModel;
}
