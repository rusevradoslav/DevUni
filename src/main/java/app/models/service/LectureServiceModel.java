package app.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class LectureServiceModel extends BaseServiceModel {
    @NotNull
    @Length(min = 10, max = 40, message = "Title must be between 10 and 40 characters !")
    private String title;

    @NotNull
    @Length(min = 50, max = 150, message = "Description must be between 50 and 150 characters")
    private String description;

    @ManyToOne
    private CourseServiceModel course;
}
