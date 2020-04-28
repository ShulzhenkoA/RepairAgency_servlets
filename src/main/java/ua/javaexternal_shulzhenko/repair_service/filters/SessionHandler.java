package ua.javaexternal_shulzhenko.repair_service.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "SessionHandler")
public class SessionHandler extends AbstractFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            session = req.getSession(true);
            setLanguage(req, session);
        }
        filterChain.doFilter(req, resp);
    }

    private void setLanguage(HttpServletRequest req, HttpSession session) {
        boolean flag = true;
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("language")) {
                    session.setAttribute("language", cookie.getValue());
                    flag = false;
                }
            }
        }
        if (flag) {
            session.setAttribute("language", "en");
        }
    }
}
