package app.services.impl;
import app.error.TopicNotFoundException;
import app.models.entity.Topic;
import app.models.service.TopicServiceModel;
import app.repositories.TopicRepository;
import app.services.TopicService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;


    @Override
    public void seedTopicsInDb() {
        if (this.topicRepository.count() == 0) {
            this.topicRepository.saveAndFlush(new Topic("C#"));
            this.topicRepository.saveAndFlush(new Topic("Java"));
            this.topicRepository.saveAndFlush(new Topic("JavaScript"));
            this.topicRepository.saveAndFlush(new Topic("Python"));
            this.topicRepository.saveAndFlush(new Topic("Php"));
            this.topicRepository.saveAndFlush(new Topic("C++"));
        }
    }

    @Override
    public TopicServiceModel findTopicServiceByName(String name) {
        return this.topicRepository.findFirstByName(name).map(topic -> this.modelMapper.map(topic, TopicServiceModel.class))
                .orElseThrow(() -> new TopicNotFoundException("Topic not found !"));
    }

    @Override
    public Topic findTopicByName(String name) {
        return this.topicRepository.findFirstByName(name).orElseThrow(() -> new TopicNotFoundException("Topic not found !"));
    }

    @Override
    public List<String> getAllTopicNames() {
        return this.topicRepository.findAll().stream().map(Topic::getName).collect(Collectors.toList());
    }


}
