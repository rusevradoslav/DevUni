package app.services;

import app.models.entity.Topic;
import app.models.service.TopicServiceModel;

import java.util.List;

public interface TopicService {

     void seedTopicsInDb();

     TopicServiceModel findTopicServiceByName(String name);

     Topic findTopicByName(String name);

    List<String> getAllTopicNames();

    List<TopicServiceModel> findAllTopics();
}
