package app.repositories;

import app.models.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic,String> {
    Optional<Topic> findFirstByName(String name);
}
