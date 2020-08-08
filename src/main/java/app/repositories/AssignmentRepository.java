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

    @Query("select a from Assignment as a where a.lecture.id =:lectureID and a.checked=false ")
    List<Assignment> findAllByLecture_Id(@Param("lectureID") String lectureID);

    @Query(value = "select * from assigments as a \n" +
            "join lectures l on a.lecture_id = l.id\n" +
            "join courses c on l.course_id = c.id\n" +
            "where c.id =:courseId and a.user_id = :userId and a.checked =true", nativeQuery = true)
    List<Assignment> findAllAssignmentsByUserIdAndCourseId(@Param("userId") String userId, @Param("courseId") String courseId);


    @Query("select a from Assignment a where a.user.id=:userId and a.checked = true ")
    List<Assignment> findAssignmentsByUserId(@Param("userId") String userId);
}
