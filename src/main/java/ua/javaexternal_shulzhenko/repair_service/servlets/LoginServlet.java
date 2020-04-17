package ua.javaexternal_shulzhenko.repair_service.servlets;

import ua.javaexternal_shulzhenko.repair_service.exceptions.ValidationException;
import ua.javaexternal_shulzhenko.repair_service.models.User;
import ua.javaexternal_shulzhenko.repair_service.services.UserDBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login_page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        User user = null;
        try{
             user = UserDBService.getUser(email);
        }catch (ValidationException exc){
            req.setAttribute("error", "Email is wrong or user didn't register");
            doGet(req, resp);
        }
        HttpSession session = req.getSession();
        session.setAttribute("user_name", user.getFirstName() + " " + user.getLastName());
        session.setAttribute("user_role", user.getRole().name());

        resp.sendRedirect(req.getContextPath() + "/personal_page");
    }
}
