package app.services;

import app.error.FileStorageException;
import app.models.entity.CustomFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

public interface CustomFileService {
    CustomFile storeFile(MultipartFile file) throws FileStorageException;

    CustomFile getFile(String fileId) throws FileNotFoundException;

    void deleteFile(String fileId);

    List<CustomFile> getAllFiles();
}
