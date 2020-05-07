package ua.javaexternal_shulzhenko.repair_service.exceptions;

public class DataBaseInteractionException extends AbstractWebappException {

    public DataBaseInteractionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataBaseInteractionException(String message) { super(message); }
}
