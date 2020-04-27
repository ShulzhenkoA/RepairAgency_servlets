package ua.javaexternal_shulzhenko.repair_service.services.verification;

import javax.servlet.http.HttpServletRequest;

public class LoginForm {

    private String email;
    private int password;

    public LoginForm(HttpServletRequest req) {
        email = req.getParameter("email");
        password = req.getParameter("pass").hashCode();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }


}
