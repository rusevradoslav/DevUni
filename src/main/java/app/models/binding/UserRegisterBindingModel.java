package app.models.binding;

import app.validations.anotations.PasswordMatches;
import app.validations.anotations.ValidEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@PasswordMatches
public class UserRegisterBindingModel {


    @Length(min = 3, max = 30, message = "Username must be at least 3 characters.")
    private String username;


    @Length(min = 1, message = "First Name must be at least 1 character.")
    private String firstName;


    @Length(min = 1, message = "Last Name must be at least 1 character.")
    private String lastName;

    @ValidEmail(message = "Invalid email")
    private String email;


    @Length(min = 3, message = "Password must be at least 3 characters.")
    private String password;


    private String confirmPassword;
}
