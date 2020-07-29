package app.services;

import app.models.entity.Topic;
import app.models.service.TopicServiceModel;

public interface TopicService {

     void seedTopicsInDb();

     TopicServiceModel findTopicServiceByName(String name);

     Topic findTopicByName(String name);
}
