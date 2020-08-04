package app.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Lecture not found!")
public class LectureNotFoundException extends RuntimeException {
    private final int status;

    public LectureNotFoundException() {
        this.status = 404;
    }

    public LectureNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}
