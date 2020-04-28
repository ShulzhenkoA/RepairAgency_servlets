package ua.javaexternal_shulzhenko.repair_service.exceptions;

public class VerificationException extends AbstractWebappException{

    private VerificationExceptionType type;

    public VerificationException(VerificationExceptionType type) {
        this.type = type;
    }

    public VerificationExceptionType getType() {
        return type;
    }

    public enum VerificationExceptionType {
        PASS,
        EMAIL
    }
}
