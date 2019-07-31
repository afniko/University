package ua.com.foxminded.task.dao.exception;

public class NoCreatedEntityException extends RuntimeException {

    public NoCreatedEntityException() {
        super();
    }

    public NoCreatedEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCreatedEntityException(String message) {
        super(message);
    }

}
