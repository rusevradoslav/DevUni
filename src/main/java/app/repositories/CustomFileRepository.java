package app.repositories;

import app.models.entity.CustomFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomFileRepository extends JpaRepository<CustomFile,String> {
}
