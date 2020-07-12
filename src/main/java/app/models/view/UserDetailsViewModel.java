package app.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDetailsViewModel {

    private String username;
    private String fullName;
    private String email;
    private String profilePicture;
}
