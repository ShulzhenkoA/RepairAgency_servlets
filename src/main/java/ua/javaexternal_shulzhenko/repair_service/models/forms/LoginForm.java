package ua.javaexternal_shulzhenko.repair_service.models.forms;

import ua.javaexternal_shulzhenko.repair_service.constants.Parameters;
import ua.javaexternal_shulzhenko.repair_service.services.validation.annotations.MustConform;
import ua.javaexternal_shulzhenko.repair_service.services.validation.regex.Regex;

import javax.servlet.http.HttpServletRequest;

public class LoginForm implements Form {

    @MustConform(Regex.USER_EMAIL)
    private final String email;
    @MustConform(Regex.USER_PASSWORD)
    private final String password;

    public LoginForm(HttpServletRequest req) {
        email = req.getParameter(Parameters.EMAIL);
        password = req.getParameter(Parameters.PASS);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
