package ua.com.foxminded.task.dao.exception;

public class NoEntityFound extends RuntimeException {

    public NoEntityFound(String message) {
        super(message);
    }

    public NoEntityFound(String message, Throwable cause) {
        super(message, cause);
    }

    public NoEntityFound(Throwable cause) {
        super(cause);
    }

}
