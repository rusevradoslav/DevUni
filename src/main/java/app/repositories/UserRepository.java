package app.repositories;


import app.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findFirstByEmail(String email);

    Optional<User> findFirstByUsername(String username);

    User findByUsername(String username);

    User findByEmail(String email);

    @Query(value = "select * from users\n" +
            "join users_authorities ua on users.id = ua.user_id\n" +
            "join authorities a on ua.authorities_id = a.id\n" +
            "where authority = 'ROLE_ADMIN'", nativeQuery = true)
    List<User> findAllAdmins();
}
