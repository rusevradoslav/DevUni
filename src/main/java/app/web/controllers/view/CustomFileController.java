package app.web.controllers.view;

import app.models.entity.CustomFile;
import app.services.AssignmentService;
import app.services.CustomFileService;
import app.services.LectureService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@AllArgsConstructor
@Controller
@RequestMapping("/files")
public class CustomFileController {
    private final CustomFileService customFileService;
    private final LectureService lectureService;
    private final AssignmentService assignmentService;

    @GetMapping("/resources/{id}")
    @PreAuthorize("isAuthenticated()")
    public void downloadResources(@PathVariable("id") String lectureId, HttpServletResponse httpServletResponse) {
        String resourceId = lectureService.findLectureResourcesIDByLectureId(lectureId);
        downloadFileByFileId(resourceId, httpServletResponse);
    }
    @GetMapping("/submission/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN,ROLE_TEACHER,ROLE_STUDENT')")
    public void downloadSubmission(@PathVariable("id") String assignmentId, HttpServletResponse httpServletResponse) {
        String resourceId = assignmentService.findFileForAssignmentById(assignmentId);

        downloadFileByFileId(resourceId, httpServletResponse);
    }


    private void downloadFileByFileId(String fileId, HttpServletResponse httpServletResponse) {

        try {
            CustomFile file = this.customFileService.getFile(fileId);
            byte[] fileBytes = file.getData();

            httpServletResponse.setContentType(file.getFileType());
            httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getFileName());
            InputStream inputStream = new ByteArrayInputStream(fileBytes);
            IOUtils.copy(inputStream, httpServletResponse.getOutputStream());

            httpServletResponse.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
