package ua.javaexternal_shulzhenko.repair_service.exceptions;

public abstract class AbstractWebappException extends RuntimeException {

    public AbstractWebappException() {
    }

    public AbstractWebappException(String message) {
        super(message);
    }

    public AbstractWebappException(String message, Throwable cause) {
        super(message, cause);
    }
}
