package app.services.impl;

import app.error.FileStorageException;
import app.models.entity.CustomFile;
import app.repositories.CustomFileRepository;
import app.services.CustomFileService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class CustomFileServiceImpl implements CustomFileService {

    private final CustomFileRepository customFileRepository;

    public CustomFile storeFile(MultipartFile file) throws FileStorageException {


        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Filename contains invalid characters!");
            }

            CustomFile dbFile = new CustomFile(fileName, file.getContentType(), file.getBytes());

            return this.customFileRepository.save(dbFile);

        } catch (IOException | FileStorageException e) {

            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
    }

    public CustomFile getFile(String fileId) throws FileNotFoundException {

        return this.customFileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File with id " + fileId + " not found!"));
    }

    public void deleteFile(String fileId) {

        this.customFileRepository.deleteById(fileId);

    }

    public List<CustomFile> getAllFiles() {

        return this.customFileRepository.findAll();
    }
}
