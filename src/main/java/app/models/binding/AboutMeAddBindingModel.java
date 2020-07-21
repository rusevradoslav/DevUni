package app.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class AboutMeAddBindingModel {


    @Length(min = 3, max = 30, message = "Knowledge level must be between 3 and 30 characters")
    private String knowledgeLevel;

    @Length(min = 100, max = 300, message = "Description must be between 100 and 300 characters")
    private String selfDescription;

}
