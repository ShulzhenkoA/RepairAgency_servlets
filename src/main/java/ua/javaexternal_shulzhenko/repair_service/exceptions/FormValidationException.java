package ua.javaexternal_shulzhenko.repair_service.exceptions;

public class FormValidationException extends AbstractWebappException{
    public FormValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
