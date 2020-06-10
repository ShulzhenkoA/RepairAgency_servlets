package ua.javaexternal_shulzhenko.car_repair_agency.exceptions;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super(message);
    }
}
