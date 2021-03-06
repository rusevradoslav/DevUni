package app.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "about_me")
public class AboutMe extends BaseEntity {


    @Column
    @Length(min = 1,max = 30,message = "Developer level must be between 1 and 30 characters")
    private String knowledgeLevel;

    @Column(columnDefinition = "TEXT")
    @Length(min = 100,max = 300,message = "Knowledge level must be between 100 and 300 characters")
    private String selfDescription;





}
