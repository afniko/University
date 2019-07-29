package ua.com.foxminded.task.dao.exception;

public class NoCreatedEntity extends RuntimeException {

    public NoCreatedEntity() {
        super();
    }

    public NoCreatedEntity(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCreatedEntity(String message) {
        super(message);
    }

}
