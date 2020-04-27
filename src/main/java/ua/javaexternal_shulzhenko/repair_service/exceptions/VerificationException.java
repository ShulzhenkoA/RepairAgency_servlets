package ua.javaexternal_shulzhenko.repair_service.exceptions;

import javax.servlet.http.HttpServletResponse;

public class VerificationException extends AbstractWebappException{

    private VerificationExceptionType type;

    public VerificationException(VerificationExceptionType type) {
        this.type = type;
    }

    public VerificationException(VerificationExceptionType type, String message){
        this(message);
        this.type = type;
    }

    public VerificationException(String message) {
        super(message, HttpServletResponse.SC_BAD_REQUEST);
    }


    public VerificationExceptionType getType() {
        return type;
    }
}
