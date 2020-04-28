package ua.javaexternal_shulzhenko.repair_service.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LanguageCookieCreator")
public class LanguageCookieCreator extends AbstractFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        String language = req.getParameter("lang");
        Cookie langCookie = new Cookie("language", language);
        langCookie.setMaxAge(60 * 60 * 24 * 30);
        resp.addCookie(langCookie);
        filterChain.doFilter(req, resp);
    }
}