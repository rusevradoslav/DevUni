package app.models.service;

import app.models.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class AboutMeServiceModel extends BaseEntity {

    @Length(min = 1, max = 30, message = "Knowledge level must be between 1 and 30 characters")
    private String knowledgeLevel;

    @Length(min = 100, max = 300, message = "Knowledge level must be between 100 and 300 characters")
    private String selfDescription;

}
