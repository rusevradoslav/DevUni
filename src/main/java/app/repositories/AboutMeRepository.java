package app.repositories;

import app.models.entity.AboutMe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutMeRepository extends JpaRepository<AboutMe,String> {

}
