package ua.javaexternal_shulzhenko.repair_service.services.verification;

import ua.javaexternal_shulzhenko.repair_service.exceptions.VerificationException;
import ua.javaexternal_shulzhenko.repair_service.exceptions.VerificationExceptionType;
import ua.javaexternal_shulzhenko.repair_service.models.User;
import ua.javaexternal_shulzhenko.repair_service.services.UsersDBService;

public class UserVerifier {
    public static User verify(LoginForm loginForm) {
        User user = UsersDBService.getUserByEmail(loginForm.getEmail());
        if(user != null){
            if (loginForm.getPassword() == user.getPassword()) {
                return user;
            }else {
                throw new VerificationException(VerificationExceptionType.PASS, "You have entered the wrong password");
            }
        }else {
            throw new VerificationException(VerificationExceptionType.EMAIL, "User with this email doesn't exist");
        }
    }

    private UserVerifier() {
    }
}
