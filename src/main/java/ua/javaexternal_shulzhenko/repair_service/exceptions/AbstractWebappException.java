package ua.javaexternal_shulzhenko.repair_service.exceptions;

public abstract class AbstractWebappException extends RuntimeException {

    private final int CODE;

    public AbstractWebappException(String message, int code) {
        super(message);
        CODE = code;
    }

    public AbstractWebappException(Throwable cause, int code) {
        super(cause);
        CODE = code;
    }

    public AbstractWebappException(String message, Throwable cause, int code) {
        super(message, cause);
        CODE = code;
    }

    public int getCODE() {
        return CODE;
    }
}
