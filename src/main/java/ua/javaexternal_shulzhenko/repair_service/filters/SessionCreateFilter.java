package ua.javaexternal_shulzhenko.repair_service.filters;

import ua.javaexternal_shulzhenko.repair_service.models.user.Role;
import ua.javaexternal_shulzhenko.repair_service.models.user.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "SessionCreator")
public class SessionCreateFilter extends AbstractFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        if (req.getSession(false) == null) {
            setUnknownUserSession(req);
        }
        filterChain.doFilter(req, resp);
    }

    private void setUnknownUserSession(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        User user = new User.UserBuilder().
                setLanguage(defineLanguageForUser(req)).
                setRole(Role.UNKNOWN).
                build();
        session.setAttribute("user", user);
    }

    private String defineLanguageForUser(HttpServletRequest req) {
        String language = tryGetLanguageFromCookie(req);
        if (language != null) {
            return language;
        } else {
            return "en";
        }
    }

    private String tryGetLanguageFromCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("language")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}