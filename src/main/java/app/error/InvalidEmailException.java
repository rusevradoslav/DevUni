package app.error;

public final class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidEmailException(final String message) {
        super(message);
    }

    public InvalidEmailException(final Throwable cause) {
        super(cause);
    }
}
