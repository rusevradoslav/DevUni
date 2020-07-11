package app.models.binding;

import app.validations.anotations.FieldMatch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@FieldMatch(first = "newPassword",second = "confirmNewPassword",message = "New password doesn't match !")
public class UserChangePasswordBindingModel {

    @Length(min = 3, message = "Password must be at least 3 characters.")
    private String oldPassword;
    @Length(min = 3, message = "Password must be at least 3 characters.")
    private String newPassword;
    private String confirmNewPassword;
}
