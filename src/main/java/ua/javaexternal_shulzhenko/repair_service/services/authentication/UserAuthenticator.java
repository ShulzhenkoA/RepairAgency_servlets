package ua.javaexternal_shulzhenko.repair_service.services.authentication;

import ua.javaexternal_shulzhenko.repair_service.exceptions.AuthenticationException;
import ua.javaexternal_shulzhenko.repair_service.models.user.User;
import ua.javaexternal_shulzhenko.repair_service.models.forms.LoginForm;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.UsersDBService;

public final class UserAuthenticator {
    public static User authenticate(LoginForm loginForm) {
        User user = UsersDBService.getUserByEmail(loginForm.getEmail());
        if(user != null){
            if (loginForm.getPassword().hashCode() == user.getPassword()) {
                return user;
            }else {
                throw new AuthenticationException(AuthenticationException.VerificationExceptionType.PASS);
            }
        }else {
            throw new AuthenticationException(AuthenticationException.VerificationExceptionType.EMAIL);
        }
    }

    private UserAuthenticator() {
    }
}
