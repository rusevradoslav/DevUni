package app.models.binding;

import app.validations.anotations.PasswordMatches;
import app.validations.anotations.ValidEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@PasswordMatches
public class UserRegisterBindingModel {

    @NotNull(message = "This field is required")
    @Length(min = 3, max = 30, message = "Username must be at least 3 characters.")
    private String username;

    @NotNull(message = "This field is required")
    private String firstName;

    @NotNull(message = "This field is required")
    private String lastName;

    @NotNull(message = "This field is required")
    @ValidEmail(message = "Invalid email")
    private String email;

    @NotNull(message = "This field is required")
    @Length(min = 3, message = "Password must be at least 3 characters.")
    private String password;

    @NotNull(message = "This field is required")
    private String confirmPassword;
}
