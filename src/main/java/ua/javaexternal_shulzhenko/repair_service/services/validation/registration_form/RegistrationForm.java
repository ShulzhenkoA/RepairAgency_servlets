package ua.javaexternal_shulzhenko.repair_service.services.validation.registration_form;

import ua.javaexternal_shulzhenko.repair_service.models.Role;
import ua.javaexternal_shulzhenko.repair_service.services.validation.*;

import javax.servlet.http.HttpServletRequest;

public class RegistrationForm {

    @MustConform(regExp = Regex.NAMES)
    private String firstName;
    @MustConform(regExp = Regex.NAMES)
    private String lastName;
    @Email
    @MustConform(regExp = Regex.EMAIL)
    private String email;
    @Pass
    @MustConform(regExp = Regex.PASSWORD)
    private String password;
    @PassConfirmation
    private String passwordConfirmation;
    private Role role;

    public RegistrationForm(HttpServletRequest req) {
        firstName = req.getParameter("fName");
        lastName = req.getParameter("lName");
        email = req.getParameter("email");
        password = req.getParameter("pass");
        passwordConfirmation = req.getParameter("passConf");
        role = Role.REGISTERED_USER;                                  //Role.valueOf(req.getParameter("role"));
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
}
