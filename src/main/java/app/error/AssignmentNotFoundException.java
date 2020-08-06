package app.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Assignment not found!")
public class AssignmentNotFoundException extends RuntimeException{

    private final int status;

    public AssignmentNotFoundException() {
        this.status = 404;
    }

    public AssignmentNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}
