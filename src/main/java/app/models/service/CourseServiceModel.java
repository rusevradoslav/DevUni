package app.models.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseServiceModel extends BaseServiceModel {


    @NotNull(message = "Title is required")
    @Length(min = 10, max = 40, message = "Title must be between 10 and 40 characters !")
    private String title;

    @NotNull(message = "Short description is required")
    @Length(min = 50, max = 150, message = "Short Description must be between 50 and 150 characters")
    private String shortDescription;

    @NotNull(message = "Description is required")
    @Length(min = 400, max = 850, message = "Description must be between 400 and 850 characters")
    private String description;

    @NotNull(message = "Course photo is required")
    private String coursePhoto;


    @Min(value = 1, message = "Pass percentage must be at least 1%")
    @Max(value = 100, message = "Pass percentage must be at maximum 100%")
    private double passPercentage = 0.0;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @FutureOrPresent(message = "The date must be in the future!")
    private LocalDateTime startedOn;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @FutureOrPresent(message = "The date must be in the future!")
    private LocalDateTime endedON;

    @NotNull
    int durationWeeks;


    private String difficulty;

    private int rating;

    private boolean status;

    private UserServiceModel author;


    private List<UserServiceModel> enrolledStudents;


    private List<LectureServiceModel> lectures;


    private String topic;

}
