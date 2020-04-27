package ua.javaexternal_shulzhenko.repair_service.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.javaexternal_shulzhenko.repair_service.exceptions.VerificationException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/*")
public class ExceptionHandler extends AbstractFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        try {
            filterChain.doFilter(request, response);
        } catch (Throwable exc) {
            if (exc instanceof VerificationException) {
                switch (((VerificationException) exc).getType()) {
                    case EMAIL:
                        LOGGER.warn("Attempt to log in using non-existing email: " + request.getParameter("email") + "\t User-Agent: " + request.getHeader("User-Agent"));
                        break;
                    case PASS:
                        LOGGER.warn("Wrong password log in attempt. User: " + request.getParameter("email") + "\t User-Agent: " + request.getHeader("User-Agent"));
                        break;
                    default:
                        break;
                }
            } else {
                LOGGER.error("Failed request (" + request.getRequestURI() + "): " + exc.getMessage(), exc);
                response.getWriter().print("<h1>Somme error has been occurred<h2>");
            }
        }
    }
}
