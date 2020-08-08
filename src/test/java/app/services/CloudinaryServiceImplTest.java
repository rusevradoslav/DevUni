package app.services;

import app.services.impl.CloudinaryServiceImpl;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CloudinaryServiceImplTest {
    private CloudinaryService serviceToTest;

    private final String VALID_FILE_NAME = "validFileName.file";
    private final String VALID_URL = "validUrl";

    @Mock
    private Uploader mockUploader;
    @Mock
    private Map mockMap;
    @Mock
    private Object mockObject;

    @Mock
    private Cloudinary mockCloudinary;
    @Mock
    private MultipartFile mockMultipartFile;

    @Before
    public void setUp() {
        this.serviceToTest = new CloudinaryServiceImpl(mockCloudinary);
    }

    @Test
    public void uploadFileShouldStoreFileAndReturnCorrectFileUrl() throws IOException {
        // Arrange
        when(this.mockCloudinary.uploader())
                .thenReturn(mockUploader);
        when(this.mockUploader.upload(Mockito.any(File.class), Mockito.any(HashMap.class)))
                .thenReturn(mockMap);
        when(this.mockMap.get("url"))
                .thenReturn(mockObject);
        when(this.mockObject
                .toString())
                .thenReturn(VALID_URL);
        when(this.mockMultipartFile.getOriginalFilename())
                .thenReturn(VALID_FILE_NAME);

        // Act
        String result = this.serviceToTest.uploadImage(mockMultipartFile);

        // Assert
        assertEquals(VALID_URL, result);

    }
}