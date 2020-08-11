package app.repositories;

import app.models.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

    @Query("select c from Course as c where c.author.id = :authorId  order by c.startedOn desc")
    List<Course> findAllCoursesByAuthorId(@Param("authorId") String authorId);

    @Query("select c from Course c where c.status=true order by c.startedOn desc")
    List<Course> findRecentCourses();

    @Modifying
    @Query("update Course c set c.status = true where c.id =:courseId")
    void changeCourseStatusToTrue(@Param("courseId") String courseId);

    @Modifying
    @Query("update Course c set c.status = false where c.id =:courseId")
    void changeCourseStatusToFalse(@Param("courseId") String courseId);

    @Query("select c from Course as c where c.status = true order by c.startedOn desc")
    List<Course> findAllCoursesWithStatusTrue();

    @Query("select c from Course as c where c.status = true and c.topic.id = :topicId  order by c.startedOn desc")
    List<Course> findAllCoursesInTopic(@Param("topicId") String topicId);

    @Query("select c from Course as c where c.topic.id =:topicId and c.status = true order by c.startedOn desc ")
    Page<Course> findAllCourseByTopic(@Param("topicId") String topicId, Pageable pageable);

    @Query("select c from Course as c  where c.status = true order by c.startedOn desc")
    Page<Course> findAllCoursesPage(Pageable pageable);
}
