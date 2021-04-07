package by.yauhenistaradubets.exceptions;

public class TooManyConstructorsException extends RuntimeException {
    public TooManyConstructorsException() {
    }

    public TooManyConstructorsException(String message) {
        super(message);
    }

    public TooManyConstructorsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyConstructorsException(Throwable cause) {
        super(cause);
    }

    public TooManyConstructorsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
