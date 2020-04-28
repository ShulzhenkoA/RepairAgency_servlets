package ua.javaexternal_shulzhenko.repair_service.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(filterName = "LanguageChanger")
public class LanguageFilter extends AbstractFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        String language = req.getParameter("lang");
        String prevUrl = req.getParameter("prevURL");
        HttpSession session = req.getSession();
        session.setAttribute("language", language);
        if (req.getParameter("page") != null) {
            resp.sendRedirect(prevUrl + "?page=" + req.getParameter("page"));
        } else {
            resp.sendRedirect(prevUrl);
        }
    }
}
