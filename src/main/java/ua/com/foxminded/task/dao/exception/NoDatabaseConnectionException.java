package ua.com.foxminded.task.dao.exception;

public class NoDatabaseConnectionException extends RuntimeException {

    public NoDatabaseConnectionException() {
        super();
    }

    public NoDatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoDatabaseConnectionException(String message) {
        super(message);
    }

}
