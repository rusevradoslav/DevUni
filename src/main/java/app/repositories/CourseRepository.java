package app.repositories;

import app.models.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,String> {

    @Query("select c from Course as c where c.author.id = :authorId")
    List<Course> findAllCoursesByAuthorId(@Param("authorId") String authorId);

    @Query("select c from Course c order by c.startedOn desc")
    List<Course> findTopThreeRecentCourses();
}
