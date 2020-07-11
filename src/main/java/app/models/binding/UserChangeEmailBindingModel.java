package app.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserChangeEmailBindingModel {
    private String oldEmail;
    private String newEmail;
    private String confirmNewEmail;
}
