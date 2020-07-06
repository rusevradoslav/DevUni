package app.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true, updatable = false)
    @Length(min = 3, max = 30, message = "Username must be at least 3 characters.")
    private String username;
    @Column(nullable = false, unique = true)
    @Email
    private String email;
    @Column(nullable = false,updatable = false)
    @Length(min = 3, message = "Password must be at least 3 characters.")
    private String password;
    @ManyToMany
    private Set<Role> authorities;

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }
}
