package ua.javaexternal_shulzhenko.repair_service.services.validation.registration_form;

import ua.javaexternal_shulzhenko.repair_service.services.UsersDBService;
import ua.javaexternal_shulzhenko.repair_service.services.validation.Email;
import ua.javaexternal_shulzhenko.repair_service.services.validation.MustConform;
import ua.javaexternal_shulzhenko.repair_service.services.validation.Pass;
import ua.javaexternal_shulzhenko.repair_service.services.validation.PassConfirmation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegistrationFormValidator {

    public Map<String, String> validateRegistrationForm(RegistrationForm registrationForm) throws IllegalAccessException {
        Field[] fields = registrationForm.getClass().getDeclaredFields();
        Map<String, String> inconsistencies = new HashMap<>();
        String pass = null;
        for (Field field : fields) {
            Annotation[] fieldAnnotations = field.getAnnotations();
            field.setAccessible(true);                                            //////////////////////////////////////check if must set accessible false!!!?????;
            for (Annotation annotation : fieldAnnotations) {
                if (annotation instanceof MustConform) {
                    MustConform mustConformAnnotation = (MustConform) annotation;
                    String regExp = mustConformAnnotation.regExp().getExpression();
                    String fieldValue = (String) field.get(registrationForm);
                    if (!checkRegExpCompliance(regExp, fieldValue)) {
                        addErrorMessage(inconsistencies, field.getName());
                    }
                } else if (annotation instanceof Pass) {
                    pass = (String) field.get(registrationForm);
                } else if (annotation instanceof PassConfirmation) {
                    String passConfirmation = (String) field.get(registrationForm);
                    if (!checkPasswordsCompliance(pass, passConfirmation)) {
                        addErrorMessage(inconsistencies, field.getName());
                    }
                } else if (annotation instanceof Email) {
                    String email = (String) field.get(registrationForm);
                    if (!checkEmailIsFree(email)){
                        addErrorMessage(inconsistencies, "notFreeEmail");
                    }
                }
            }
            field.setAccessible(false);
        }
        return inconsistencies;
    }

    private boolean checkRegExpCompliance(String regExp, String fieldValue) {
        return Pattern.matches(regExp, fieldValue);
    }

    private boolean checkPasswordsCompliance(String pass, String passConfirmation) {
        return pass.equals(passConfirmation);
    }

    private boolean checkEmailIsFree(String email) {
        return UsersDBService.isUserEmailFree(email);
    }

    private void addErrorMessage(Map<String, String> inconsistencies, String fieldName) {
        switch (fieldName) {
            case "firstName":
                inconsistencies.put("firstName", "The name can't begin with a space or contain digits and signs."); /////////I18N !!!!!!!!!!!!!!!!!!!!
                break;
            case "lastName":
                inconsistencies.put("lastName", "A surname can't begin with a space or contain digits and signs."); /////////I18N !!!!!!!!!!!!!!!!!!!!
                break;
            case "notFreeEmail":
                inconsistencies.put("email", "A user with this email already exists."); /////////I18N !!!!!!!!!!!!!!!!!!!!
                break;
            case "email":
                inconsistencies.put("email", "Email must conform: 'example@mail.com'"); /////////I18N !!!!!!!!!!!!!!!!!!!!
                break;
            case "password":
                inconsistencies.put("pass", "The password length must be between 8 to 20. It can contain only latin letters and digits. It must contain at least one uppercase letter, lowercase letter and digit."); /////////I18N !!!!!!!!!!!!!!!!!!!!
                break;
            case "passwordConfirmation":
                inconsistencies.put("passConfirmation", "Password confirmation is incorrect."); /////////I18N !!!!!!!!!!!!!!!!!!!!
                break;
            default:
                break;
        }
    }
}
