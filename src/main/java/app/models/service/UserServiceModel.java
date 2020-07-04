package app.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel extends BaseServiceModel {
    @NotNull(message = "First name ist required")
    private String firstName;

    @NotNull(message = "Last name ist required")
    private String lastName;

    @Length(min = 3, max = 30, message = "Password must be at least 3 characters.")
    private String username;

    @Email(message = "Email must contain @")
    private String email;

    @Length(min = 3, message = "Password must be at least 3 characters.")
    private String password;

    private Set<RoleServiceModel> authorities;
}
