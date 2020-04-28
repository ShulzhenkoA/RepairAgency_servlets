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
                       inconsistencies.put(field.getName(), "");
                    }
                } else if (annotation instanceof Pass) {
                    pass = (String) field.get(registrationForm);
                } else if (annotation instanceof PassConfirmation) {
                    String passConfirmation = (String) field.get(registrationForm);
                    if (!checkPasswordsCompliance(pass, passConfirmation)) {
                        inconsistencies.put(field.getName(), "");
                    }
                } else if (annotation instanceof Email) {
                    String email = (String) field.get(registrationForm);
                    if (!checkEmailIsFree(email)){
                        inconsistencies.put("notFreeEmail", "");
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
}
