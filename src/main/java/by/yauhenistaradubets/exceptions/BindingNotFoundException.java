package by.yauhenistaradubets.exceptions;

public class BindingNotFoundException extends RuntimeException {
    public BindingNotFoundException() {
    }

    public BindingNotFoundException(String message) {
        super(message);
    }

    public BindingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BindingNotFoundException(Throwable cause) {
        super(cause);
    }

    public BindingNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
