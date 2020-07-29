package app.error;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Topic not found!")
public class TopicNotFoundException extends RuntimeException {

    private final int status;

    public TopicNotFoundException() {
        this.status = 404;
    }

    public TopicNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}
