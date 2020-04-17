package ua.javaexternal_shulzhenko.repair_service.exceptions;

import javax.servlet.http.HttpServletResponse;

public class DataBaseInteractionException extends AbstractWebappException {

    public DataBaseInteractionException(String message) {
        super(message, HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }

    public DataBaseInteractionException(Throwable cause) {
        super(cause, HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }

    public DataBaseInteractionException(String message, Throwable cause) {
        super(message, cause, HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }
}
