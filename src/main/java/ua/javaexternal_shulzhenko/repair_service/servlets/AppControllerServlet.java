package ua.javaexternal_shulzhenko.repair_service.servlets;

import ua.javaexternal_shulzhenko.repair_service.exceptions.VerificationException;
import ua.javaexternal_shulzhenko.repair_service.models.Role;
import ua.javaexternal_shulzhenko.repair_service.models.User;
import ua.javaexternal_shulzhenko.repair_service.services.UsersDBService;
import ua.javaexternal_shulzhenko.repair_service.services.verification.UserVerifier;
import ua.javaexternal_shulzhenko.repair_service.utils.pagination.PagePaginationHandler;
import ua.javaexternal_shulzhenko.repair_service.utils.pagination.PaginationModel;
import ua.javaexternal_shulzhenko.repair_service.services.verification.LoginForm;
import ua.javaexternal_shulzhenko.repair_service.services.validation.registration_form.RegistrationForm;
import ua.javaexternal_shulzhenko.repair_service.services.validation.registration_form.RegistrationFormValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = {"/reviews", "/home", "/personal_page", "/registration", "/login", "/contacts"})
public class AppControllerServlet extends HttpServlet {

    private PagePaginationHandler pagePaginationHandler;

    @Override
    public void init() throws ServletException {
        pagePaginationHandler = new PagePaginationHandler();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getServletPath().equals("/reviews")) {
            req.setAttribute("aside_menu", "aside_menu.jsp");
            req.setAttribute("main_block", "reviews.jsp");
            int pageNum = extractPageNum(req);
            PaginationModel paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(), pageNum, 1000, 50);
            req.setAttribute("paginationModel", paginationModel);
        } else if (req.getServletPath().equals("/home")) {
            req.setAttribute("aside_menu", "aside_menu.jsp");
            req.setAttribute("main_block", "home_common.jsp");
        } else if (req.getServletPath().equals("/personal_page")) {
            req.setAttribute("aside_menu", "aside_menu.jsp");
            req.setAttribute("main_block", "user_home.jsp");
        } else if (req.getServletPath().equals("/login")) {
            req.setAttribute("main_block", "login_form.jsp");
        } else if (req.getServletPath().equals("/registration")) {
            req.setAttribute("main_block", "registration_form.jsp");
        }else if(req.getServletPath().equals("/contacts")) {
            req.setAttribute("aside_menu", "aside_menu.jsp");
            req.setAttribute("main_block", "home_common.jsp");
        }
        req.getRequestDispatcher("WEB-INF/jsp_pages/core_page.jsp").forward(req, resp);
    }

    private int extractPageNum(HttpServletRequest req) {
        String page = req.getParameter("page");
        if (page != null) {
            return Integer.parseInt(page);
        } else {
            return 1;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, VerificationException {

        if (req.getServletPath().equals("/login")) {
            try {
                User user = UserVerifier.verify(new LoginForm(req));
                HttpSession session = req.getSession();
                session.setAttribute("userName", user.getFirstName() + " " + user.getLastName());
                session.setAttribute("userRole", user.getRole().name());
                resp.sendRedirect(req.getContextPath() + "/personal_page");
            } catch (VerificationException exc) {
                String email = req.getParameter("email");
                req.setAttribute("prevEmail", email);
                req.setAttribute("errorMessage", exc.getMessage());
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                doGet(req, resp);
                throw new VerificationException(exc.getType());
            }

        } else if (req.getServletPath().equals("/registration")) {

            try {
                RegistrationForm registrationForm = new RegistrationForm(req);
                Map<String, String> inconsistencies = new RegistrationFormValidator().validateRegistrationForm(registrationForm);
                if (inconsistencies.isEmpty()) {
                    UsersDBService.createUser(registrationForm);
                    User user = UsersDBService.getUserByEmail(registrationForm.getEmail());
                    addUserToSession(req, user);
                    resp.sendRedirect(req.getContextPath() + "/personal_page");
                } else {
                    req.setAttribute("inconsistencies", inconsistencies);
                    req.setAttribute("prevFName", req.getParameter("fName"));
                    req.setAttribute("prevLName", req.getParameter("lName"));
                    req.setAttribute("prevEmail", req.getParameter("email"));
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    doGet(req, resp);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();                                 //////////////////////////////////handle this exception !!!!!!!!!!!!!!!!!!!!!!!!!
            }
        }
    }

    private void addUserToSession(HttpServletRequest req, User user) {
        HttpSession session = req.getSession();
        session.setAttribute("user_name", user.getFirstName() + " " + user.getLastName());
        session.setAttribute("user_role", user.getRole().name());
    }
}
