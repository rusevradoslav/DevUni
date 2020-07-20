package app.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class AboutMeAddBindingModel {


    @Length(min = 1, max = 30, message = "Knowledge level must be between 1 and 30 characters")
    private String knowledgeLevel;

    @Length(min = 1, max = 300, message = "Knowledge level must be between 1 and 300 characters")
    private String selfDescription;

}
