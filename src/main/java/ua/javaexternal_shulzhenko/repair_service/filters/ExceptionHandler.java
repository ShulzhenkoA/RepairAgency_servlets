package ua.javaexternal_shulzhenko.repair_service.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebFilter("/*")
public class ExceptionHandler implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(ExceptionHandler.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        }catch (Throwable exc){
            LOGGER.error("Failed request (" + request.getRequestURI()+"): " + exc.getMessage(), exc);
        }
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.getWriter().print("<h1>Somme error has been occurred<h2>");
    }

}
