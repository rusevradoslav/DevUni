package app.repositories;

import app.models.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, String> {

    Optional<Assignment> findFirstByDescription(String description);

    @Query("select a from Assignment as a where a.lecture.id =:lectureID")
    List<Assignment> findAllByLecture_Id(@Param("lectureID") String lectureID);
}
