package ua.javaexternal_shulzhenko.repair_service.exceptions;

import javax.servlet.http.HttpServletResponse;

public class ValidationException extends AbstractWebappException{
    public ValidationException(String message) {
        super(message, HttpServletResponse.SC_BAD_REQUEST);
    }
}
