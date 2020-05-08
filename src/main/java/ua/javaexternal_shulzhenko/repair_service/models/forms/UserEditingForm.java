package ua.javaexternal_shulzhenko.repair_service.models.forms;

import ua.javaexternal_shulzhenko.repair_service.models.user.Role;
import ua.javaexternal_shulzhenko.repair_service.services.validation.annotations.Email;
import ua.javaexternal_shulzhenko.repair_service.services.validation.annotations.MustConform;
import ua.javaexternal_shulzhenko.repair_service.services.validation.annotations.NotEmpty;
import ua.javaexternal_shulzhenko.repair_service.services.validation.annotations.UserID;
import ua.javaexternal_shulzhenko.repair_service.services.validation.regex.Regex;

import javax.servlet.http.HttpServletRequest;

public class UserEditingForm implements Form {

    @UserID
    private final int id;
    @MustConform(Regex.NAMES)
    private final String firstName;
    @MustConform(Regex.NAMES)
    private final String lastName;
    @Email
    @MustConform(Regex.USER_EMAIL)
    private final String email;
    @NotEmpty
    private final Role role;

    public UserEditingForm(HttpServletRequest req) {
        id = Integer.parseInt(req.getParameter("editing_user_id"));
        firstName = req.getParameter("fName");
        lastName = req.getParameter("lName");
        email = req.getParameter("email");
        role = Role.valueOf(req.getParameter("role"));
    }

    public int getId() { return id; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() { return lastName; }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}