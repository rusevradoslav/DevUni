package app.models.service;

import app.validations.anotations.ValidEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel extends BaseServiceModel {
    @Length(min = 3, max = 30, message = "Password must be at least 3 characters.")
    private String username;

    @NotNull(message = "First name ist required")
    private String firstName;

    @NotNull(message = "Last name ist required")
    private String lastName;


    @ValidEmail(message = "Email must contain @")
    private String email;

    @Length(min = 3, message = "Password must be at least 3 characters.")
    private String password;

    private String profilePicture;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime registrationDate;

    private boolean teacherRequest;

    private boolean status;

    private AboutMeServiceModel aboutMeServiceModel;

    private Set<RoleServiceModel> authorities;


    private Set<CourseServiceModel> createdCourses;


    private Set<CourseServiceModel> enrolledCourses;

    private List<AssignmentServiceModel> solutionAssignments;
}
