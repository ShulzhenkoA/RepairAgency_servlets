package ua.javaexternal_shulzhenko.repair_service.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.javaexternal_shulzhenko.repair_service.exceptions.NotFoundException;
import ua.javaexternal_shulzhenko.repair_service.exceptions.VerificationException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "ExceptionHandler")
public class ExceptionHandleFilter extends AbstractFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) {

        try {
            filterChain.doFilter(req, resp);
        } catch (Throwable exc) {
            if (exc instanceof VerificationException) {
                switch (((VerificationException) exc).getType()) {
                    case EMAIL:
                        LOGGER.warn("Attempt to log in using non-existing email: " + req.getParameter("email") + "\t User-Agent: " + req.getHeader("User-Agent"));
                        break;
                    case PASS:
                        LOGGER.warn("Wrong password log in attempt. User: " + req.getParameter("email") + "\t User-Agent: " + req.getHeader("User-Agent"));
                        break;
                    default:
                        break;
                }
            } else if (exc instanceof NotFoundException){
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                req.setAttribute("main_block", "error.jsp");
                try {
                    req.getRequestDispatcher("WEB-INF/jsp_pages/core_page.jsp").forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else{
                LOGGER.error("Failed request (" + req.getRequestURI() + "): " + exc.getMessage(), exc);
                try {
                    resp.getWriter().print("<h1>Somme error has been occurred<h2>");
                } catch (IOException e) {
                    e.printStackTrace();                                       ////////////////////////////////////////////////////////
                }
            }
        }
    }
}
