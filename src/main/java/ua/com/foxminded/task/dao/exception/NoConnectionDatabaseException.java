package ua.com.foxminded.task.dao.exception;

public class NoConnectionDatabaseException extends RuntimeException {

    public NoConnectionDatabaseException() {
        super();
    }

    public NoConnectionDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoConnectionDatabaseException(String message) {
        super(message);
    }

}
