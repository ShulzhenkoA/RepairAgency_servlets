package ua.javaexternal_shulzhenko.repair_service.filters;

import ua.javaexternal_shulzhenko.repair_service.constants.Attributes;
import ua.javaexternal_shulzhenko.repair_service.constants.CommonConstants;
import ua.javaexternal_shulzhenko.repair_service.constants.Parameters;
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
        String language = req.getParameter(Parameters.LANG);
        setLanguageForUser(req, language);
        setLanguageToCookie(resp, language);
        sendRedirect(req, resp);
    }

    private void setLanguageForUser(HttpServletRequest req, String language) {
        User user = getUserFromSession(req);
        Role role = user.getRole();
        user.setLanguage(language);
        if(!role.equals(Role.UNKNOWN)){
            UsersDBService.changeUserLanguage(user.getId(), language);
        }
    }

    private void setLanguageToCookie(HttpServletResponse resp, String language){
        Cookie langCookie = new Cookie(Attributes.LANGUAGE, language);
        langCookie.setMaxAge(CommonConstants.COOKIE_AGE);
        resp.addCookie(langCookie);
    }

    private void sendRedirect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String prevUrl = req.getParameter(Parameters.PREV_URL);
        if (req.getParameter(Parameters.PAGE) != null) {
            resp.sendRedirect(prevUrl + CommonConstants.PAGE_EQUAL + req.getParameter(Parameters.PAGE));
        } else {
            resp.sendRedirect(prevUrl);
        }
    }

}
