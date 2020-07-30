package app.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course extends BaseEntity {

    @Column(nullable = false, updatable = false)
    @Length(min = 10, max = 40, message = "Title must be between 10 and 40 characters !")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Length(min = 50, max = 150, message = "Short Description must be between 50 and 150 characters")
    private String shortDescription;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Length(min = 400, max = 850, message = "Description must be between 400 and 850 characters")
    private String description;

    @Column(nullable = false)
    private String coursePhoto;

    @Column
    private double passPercentage = 0.0;


    @Column(nullable = false)
    private LocalDate startedOn;


    @Column(nullable = false)
    private LocalDate endedON;

    @Column
    private boolean status;

    @Column
    private int courseRating;

    @ManyToOne
    private User author;

    @ManyToMany(mappedBy = "enrolledCourses")
    private List<User> enrolledStudents;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private List<Lecture> lectures;

    @ManyToOne(fetch = FetchType.EAGER)
    private Topic topic;


}
