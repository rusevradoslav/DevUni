package app.models.entity;

import app.validations.anotations.ValidEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @Column(nullable = false, unique = true,updatable = true)
    @ValidEmail
    private String email;
    @Column(nullable = false,updatable = true)
    @Length(min = 3, message = "Password must be at least 3 characters.")
    private String password;

    @Column
    private String profilePicture;

     @Column(nullable = false )
     @DateTimeFormat(pattern = "yMM-dd-yyyy HH:mm")
     private LocalDateTime registrationDate;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    private Set<Role> authorities;

    @Column
    private boolean isNonBlocked;


    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNonBlocked;
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
