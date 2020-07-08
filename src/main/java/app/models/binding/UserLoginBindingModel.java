package app.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class UserLoginBindingModel {

    @NotNull(message = "This field is required")
    @Length(min = 3, max = 30, message = "Username must be at least 3 characters.")
    private String username;

    @NotNull(message = "This field is required")
    @Length(min = 3, message = "Password must be at least 3 characters.")
    private String password;
}
