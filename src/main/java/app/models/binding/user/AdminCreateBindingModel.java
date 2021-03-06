package app.models.binding.user;

import app.validations.anotations.FieldMatch;
import app.validations.anotations.ValidEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@FieldMatch(first = "password" ,second = "confirmPassword" ,message = "Password fields doesn't match!")
public class AdminCreateBindingModel {


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
