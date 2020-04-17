package ua.javaexternal_shulzhenko.repair_service.servlets;

import ua.javaexternal_shulzhenko.repair_service.models.Role;
import ua.javaexternal_shulzhenko.repair_service.models.User;
import ua.javaexternal_shulzhenko.repair_service.services.UserDBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/registration_page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        User user = extractUserFromRequest(req);
        UserDBService.addUser(user);
        addUserToSession(req, user);
        resp.sendRedirect(req.getContextPath() + "/personal_page");
    }

    private void addUserToSession(HttpServletRequest req, User user) {
        HttpSession session = req.getSession();
        session.setAttribute("user_name", user.getFirstName() + " " + user.getLastName());
        session.setAttribute("user_role", user.getRole().name());
    }

    private User extractUserFromRequest(HttpServletRequest req) {
        User user = new User();
        user.setFirstName(req.getParameter("firstName"));
        user.setLastName(req.getParameter("lastName"));
        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password").hashCode());
        user.setLanguage(req.getParameter("language"));
        user.setRole(Role.REGISTERED_USER);
        return user;
    }
}
