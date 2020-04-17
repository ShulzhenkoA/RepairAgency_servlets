package ua.javaexternal_shulzhenko.repair_service.exceptions;

import javax.servlet.http.HttpServletResponse;

public class NotFoundException extends AbstractWebappException {

    public NotFoundException(String message) {
        super(message, HttpServletResponse.SC_NOT_FOUND);
    }
}
