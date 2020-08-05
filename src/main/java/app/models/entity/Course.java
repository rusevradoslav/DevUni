package app.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course extends BaseEntity {

    @Column(nullable = false, updatable = false)
    @Length(min = 5, max = 40, message = "Title must be between 10 and 40 characters !")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Length(min = 120, max = 150, message = "Short Description must be between 50 and 150 characters")
    private String shortDescription;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Length(min = 400, max = 850, message = "Description must be between 400 and 850 characters")
    private String description;

    @Column
    private String coursePhoto;

    @Column
    private double passPercentage = 0.0;


    @Column(nullable = false)
    private LocalDateTime startedOn;


    @Column(nullable = false)
    private LocalDateTime endedON;

    @Column(nullable = false)
    int durationWeeks;

    @Column
    private boolean status;

    @Column
    private int courseRating;



    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, updatable = false)
    private Difficulty difficulty;

    @ManyToOne
    private User author;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> enrolledStudents;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Lecture> lectures;

    @ManyToOne(fetch = FetchType.EAGER)
    private Topic topic;


}
