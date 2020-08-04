package app.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "lectures")
public class Lecture extends BaseEntity {

    @Column(nullable = false, updatable = false)
    @Length(min = 10, max = 40, message = "Title must be between 10 and 40 characters !")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Length(min = 50, max = 150, message = "Description must be between 50 and 150 characters")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String lectureVideoUrl;


    @OneToOne
    private CustomFile resources;

    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "lecture")
    List<Assignment> studentsAssignmentSolutions;





}
