package app.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class UserDetailsViewModel {

    private String username;
    private String fullName;
    private String email;
    private String profilePicture;

    @DateTimeFormat(pattern = "yMM-dd-yyyy HH:mm")
    private LocalDateTime registrationDate;
}
