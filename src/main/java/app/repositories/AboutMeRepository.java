package app.repositories;

import app.models.entity.AboutMe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AboutMeRepository extends JpaRepository<AboutMe, String> {

    @Modifying
    @Query("update AboutMe as ab set ab.knowledgeLevel=:knowledgeLevel,ab.selfDescription=:selfDescription where ab.id=:aboutMeId")
    void updateAboutMe(@Param("aboutMeId") String aboutMeId, @Param("knowledgeLevel") String knowledgeLevel
            , @Param("selfDescription") String selfDescription);
}
