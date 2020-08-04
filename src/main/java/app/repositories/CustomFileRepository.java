package app.repositories;

import app.models.entity.CustomFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomFileRepository extends JpaRepository<CustomFile,String> {
}
