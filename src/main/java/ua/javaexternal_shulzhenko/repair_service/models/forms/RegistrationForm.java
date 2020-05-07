package ua.javaexternal_shulzhenko.repair_service.models.forms;

import ua.javaexternal_shulzhenko.repair_service.models.user.Role;
import ua.javaexternal_shulzhenko.repair_service.models.user.User;
import ua.javaexternal_shulzhenko.repair_service.services.validation.annotations.*;
import ua.javaexternal_shulzhenko.repair_service.services.validation.regex.Regex;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RegistrationForm implements Form {

    @MustConform(Regex.NAMES)
    private final String firstName;
    @MustConform(Regex.NAMES)
    private final String lastName;
    @Email
    @MustConform(Regex.USER_EMAIL)
    private final String email;
    @Pass
    @MustConform(Regex.USER_PASSWORD)
    private final String password;
    @PassConfirmation
    private final String passwordConfirmation;
    private final String language;
    @NotEmpty
    private final Role role;

    public RegistrationForm(HttpServletRequest req) {
        firstName = req.getParameter("fName");
        lastName = req.getParameter("lName");
        email = req.getParameter("email");
        password = req.getParameter("pass");
        passwordConfirmation = req.getParameter("passConf");
        language = extractLanguage(req);
        role = extractRole(req);
    }

    private String extractLanguage(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        return user.getLanguage();
    }

    private Role extractRole(HttpServletRequest req) {
        String role = req.getParameter("role");
        if(role != null){
            return Role.valueOf(role);
        }
        return null;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public String getLanguage() {
        return language;
    }

    public Role getRole() {
        return role;
    }
}
