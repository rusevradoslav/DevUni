package app.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDetailsBindingModel {

    private String newEmail;

    private String password;

    private String newPassword;

    private String confirmNewPassword;
}
