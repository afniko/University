package ua.com.foxminded.task.dao.exception;

public class NoExecuteQueryException extends RuntimeException {

    public NoExecuteQueryException() {
        super();
    }

    public NoExecuteQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoExecuteQueryException(String message) {
        super(message);
    }

}
