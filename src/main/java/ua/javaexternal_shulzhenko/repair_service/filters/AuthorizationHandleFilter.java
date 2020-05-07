package ua.javaexternal_shulzhenko.repair_service.filters;

import ua.javaexternal_shulzhenko.repair_service.exceptions.NotFoundException;
import ua.javaexternal_shulzhenko.repair_service.models.user.User;
import ua.javaexternal_shulzhenko.repair_service.services.authorization.AuthorizationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthorizationHandler")
public class AuthorizationHandleFilter extends AbstractFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        String servletPath = req.getServletPath();
        User user = (User) req.getSession().getAttribute("user");
        if (AuthorizationService.authorize(servletPath, user)) {
            filterChain.doFilter(req, resp);
        } else if (req.getServletPath().equals("/login") || req.getServletPath().equals("/registration")) {
            throw new NotFoundException(req.getServletPath() + " page not found");
        } else {
            if (req.getServletPath().equals("/leave_review")) {
                req.getSession().setAttribute("review", req.getParameter("review"));
            }
            req.setAttribute("main_block", "login_main_block.jsp");
            req.getSession().setAttribute("previousBeforeLogin", req.getServletPath());
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
