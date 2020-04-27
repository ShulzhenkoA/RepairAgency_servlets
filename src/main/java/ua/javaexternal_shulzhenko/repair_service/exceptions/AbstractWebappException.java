package ua.javaexternal_shulzhenko.repair_service.exceptions;

public abstract class AbstractWebappException extends RuntimeException {

    private int STATUS_CODE;

    public AbstractWebappException() {
    }

    public AbstractWebappException(String message, int code) {
        super(message);
        this.STATUS_CODE = code;
    }

    public AbstractWebappException(Throwable cause, int code) {
        super(cause);
        this.STATUS_CODE = code;
    }

    public AbstractWebappException(String message, Throwable cause, int code) {
        super(message, cause);
        this.STATUS_CODE = code;
    }

    public int getCODE() {
        return STATUS_CODE;
    }
}
