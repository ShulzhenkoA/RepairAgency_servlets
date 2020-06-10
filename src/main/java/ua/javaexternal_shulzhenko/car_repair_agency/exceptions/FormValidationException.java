package ua.javaexternal_shulzhenko.car_repair_agency.exceptions;

public class FormValidationException extends RuntimeException{
    public FormValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
