package app.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Course not found!")
public class CourseNotFoundException extends RuntimeException {

    private final int status;

    public CourseNotFoundException() {
        this.status = 404;
    }

    public CourseNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}
