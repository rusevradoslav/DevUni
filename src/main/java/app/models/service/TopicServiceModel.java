package app.models.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TopicServiceModel extends BaseServiceModel {


    private String name;

    private List<CourseServiceModel> courses;
}
