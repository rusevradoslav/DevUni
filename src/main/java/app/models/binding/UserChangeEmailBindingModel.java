package app.models.binding;

import app.validations.anotations.FieldMatch;
import app.validations.anotations.ValidEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@FieldMatch(first = "newEmail",second = "confirmNewEmail",message = "New email must match!")
public class UserChangeEmailBindingModel {
    @ValidEmail(message = "Invalid Email")
    private String oldEmail;

    @ValidEmail(message = "Invalid Email")
    private String newEmail;

    private String confirmNewEmail;
}
