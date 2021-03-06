package app.repositories;


import app.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findFirstByEmail(String email);

    Optional<User> findFirstByUsername(String username);

    @Query(value = "select * from users\n" +
            "join users_authorities ua on users.id = ua.user_id\n" +
            "join authorities a on ua.authorities_id = a.id\n" +
            "where authority = 'ROLE_ADMIN'", nativeQuery = true)
    List<User> findAllAdmins();

    @Query(value = "select * from users\n" +
            "join users_authorities ua on users.id = ua.user_id\n" +
            "join authorities a on ua.authorities_id = a.id\n" +
            "where authority = 'ROLE_STUDENT'", nativeQuery = true)
    List<User> findAllStudents();

    @Query(value = "select * from users\n" +
            "join users_authorities ua on users.id = ua.user_id\n" +
            "join authorities a on ua.authorities_id = a.id\n" +
            "where authority = 'ROLE_TEACHER'", nativeQuery = true)
    List<User> findAllTeachers();

    @Query(value = "select * from users as u\n" +
            "         join users_authorities ua on u.id = ua.user_id\n" +
            "         join authorities a on ua.authorities_id = a.id\n" +
            "where authority = 'ROLE_STUDENT'and u.teacher_request = true;", nativeQuery = true)
    List<User> findAllStudentsWithRequests();


    @Modifying
    @Transactional
    @Query(value = "update users_authorities ua " +
            "set ua.authorities_id = :authorityId\n" +
            "where user_id = :userId", nativeQuery = true)
    void changeRole(@Param("authorityId") String authorityId, @Param("userId") String userId);



}

