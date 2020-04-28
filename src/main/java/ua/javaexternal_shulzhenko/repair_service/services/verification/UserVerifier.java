package ua.javaexternal_shulzhenko.repair_service.services.verification;

import ua.javaexternal_shulzhenko.repair_service.exceptions.VerificationException;
import ua.javaexternal_shulzhenko.repair_service.models.User;
import ua.javaexternal_shulzhenko.repair_service.services.UsersDBService;

public class UserVerifier {
    public static User verify(LoginForm loginForm) {
        User user = UsersDBService.getUserByEmail(loginForm.getEmail());
        if(user != null){
            if (loginForm.getPassword() == user.getPassword()) {
                return user;
            }else {
                throw new VerificationException(VerificationException.VerificationExceptionType.PASS);
            }
        }else {
            throw new VerificationException(VerificationException.VerificationExceptionType.EMAIL);
        }
    }

    private UserVerifier() {
    }
}
