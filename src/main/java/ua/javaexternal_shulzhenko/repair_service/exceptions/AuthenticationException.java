package ua.javaexternal_shulzhenko.repair_service.exceptions;

public class AuthenticationException extends AbstractWebappException{

    private VerificationExceptionType type;

    public AuthenticationException(VerificationExceptionType type) {
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
