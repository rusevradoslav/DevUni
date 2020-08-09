package app.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "assigments")
public class Assignment extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private double gradePercentage = 0.0;

    @OneToOne
    private CustomFile file;

    @ManyToOne
    private User user;

    @ManyToOne
    private Lecture lecture;

    @Column
    private boolean checked;

    @Column
    private LocalDateTime checkedOn;
}
