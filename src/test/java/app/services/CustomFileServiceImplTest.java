package app.services;

import app.error.FileStorageException;
import app.models.entity.CustomFile;
import app.repositories.CustomFileRepository;
import app.services.impl.CustomFileServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomFileServiceImplTest {

    private final String VALID_ID = "validId";
    private final String VALID_FILE_NAME = "validFileName.txt";
    private final String VALID_FILE_TYPE = "validFileType";
    private final byte[] VALID_DATA = new byte[]{11, 11};

    private CustomFile customFile;

    @Mock
    private CustomFileRepository customFileRepository;

    @InjectMocks
    private CustomFileServiceImpl customFileService;

    @Before
    public void setUp() {

        customFile = new CustomFile();
        customFile.setId(VALID_ID);
        customFile.setFileName(VALID_FILE_NAME);
        customFile.setFileType(VALID_FILE_TYPE);
        customFile.setData(VALID_DATA);
    }

    @Test
    public void storeFile_shouldStoreFileCorrectly() throws IOException, FileStorageException {

        // Arrange
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename())
                .thenReturn(VALID_FILE_NAME);
        when(multipartFile.getContentType())
                .thenReturn(VALID_FILE_TYPE);
        when(multipartFile.getBytes())
                .thenReturn(VALID_DATA);

        when(this.customFileRepository.save(Mockito.any(CustomFile.class)))
                .thenReturn(customFile);

        // Act
        CustomFile file = this.customFileService.storeFile(multipartFile);

        assertEquals(VALID_FILE_NAME, file.getFileName());
        assertEquals(VALID_FILE_TYPE, file.getFileType());
        assertEquals(VALID_DATA, file.getData());
    }

    @Test
    public void storeFileShouldThrowFileStorageExceptionOnInvalidFileName() {
        // Arrange
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename())
                .thenReturn("invalid..name");

        // Act
        // Assert
        assertThrows(FileStorageException.class, () -> this.customFileService.storeFile(multipartFile));

    }

    @Test
    public void getFileShouldReturnCorrectFile() throws  FileNotFoundException {
        // Arrange
        when(this.customFileRepository.findById(VALID_ID))
                .thenReturn(Optional.of(customFile));

        // Act
        CustomFile file = this.customFileService.getFile(VALID_ID);

        // Assert
        assertEquals(VALID_ID, file.getId());
        assertEquals(VALID_FILE_NAME, file.getFileName());
        assertEquals(VALID_FILE_TYPE, file.getFileType());
        assertEquals(VALID_DATA, file.getData());
    }

    @Test
    public void getFileShouldThrowFileNotFoundExceptionOnInvalidId() throws FileNotFoundException {
        // Arrange
        final String invalidId = "invalidId";
        when(this.customFileRepository.findById(invalidId))
                .thenReturn(Optional.empty());

        // Act
        // Assert
        assertThrows(FileNotFoundException.class, () -> this.customFileService.getFile(invalidId));
    }

    @Test
    public void deleteFileShouldCorrectlyDeleteFile() {
        // Arrange

        // Act
        this.customFileService.deleteFile(VALID_ID);

        // Assert
        verify(this.customFileRepository, times(1)).deleteById(VALID_ID);
    }

    @Test
    public void getAllFilesShouldReturnAllFiles() {
        // Arrange
        when(this.customFileRepository.findAll())
                .thenReturn(List.of(customFile));

        // Act
        List<CustomFile> files = this.customFileService.getAllFiles();

        // Assert
        assertEquals(VALID_ID, files.get(0).getId());
    }
}