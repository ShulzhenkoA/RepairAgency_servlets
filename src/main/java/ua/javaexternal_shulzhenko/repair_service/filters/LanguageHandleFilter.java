package ua.javaexternal_shulzhenko.repair_service.filters;

import ua.javaexternal_shulzhenko.repair_service.models.user.Role;
import ua.javaexternal_shulzhenko.repair_service.models.user.User;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.UsersDBService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(filterName = "LanguageHandler")
public class LanguageHandleFilter extends AbstractFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        String language = req.getParameter("lang");
        HttpSession session = req.getSession();
        setLanguageForUser(session, language);
        setLanguageToCookie(resp, language);
        sendRedirect(req, resp);
    }

    private void setLanguageForUser(HttpSession session, String language) {
        User user = (User) session.getAttribute("user");
        Role role = user.getRole();
        user.setLanguage(language);
        if(!role.equals(Role.UNKNOWN)){
            UsersDBService.changeUserLanguage(user.getId(), language);
        }
    }

    private void setLanguageToCookie(HttpServletResponse resp, String language){
        Cookie langCookie = new Cookie("language", language);
        langCookie.setMaxAge(60 * 60 * 24 * 30);
        resp.addCookie(langCookie);
    }

    private void sendRedirect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String prevUrl = req.getParameter("prevURL");
        if (req.getParameter("page") != null) {
            resp.sendRedirect(prevUrl + "?page=" + req.getParameter("page"));
        } else {
            resp.sendRedirect(prevUrl);
        }
    }

}
