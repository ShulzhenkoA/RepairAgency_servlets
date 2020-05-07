package ua.javaexternal_shulzhenko.repair_service.services.validation;

import ua.javaexternal_shulzhenko.repair_service.models.forms.Form;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.UsersDBService;
import ua.javaexternal_shulzhenko.repair_service.services.validation.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FormValidator {

    public static Map<String, String> validateForm(Form form) throws IllegalAccessException {
        Field[] fields = form.getClass().getDeclaredFields();
        Map<String, String> inconsistencies = new HashMap<>();
        String pass = null;
        for (Field field : fields) {
            Annotation[] fieldAnnotations = field.getAnnotations();
            field.setAccessible(true);                                            //////////////////////////////////////check if must set accessible false!!!?????;
            for (Annotation annotation : fieldAnnotations) {
                if (annotation instanceof MustConform) {
                    MustConform mustConformAnnotation = (MustConform) annotation;
                    String regExp = mustConformAnnotation.value().getExpression();
                    String fieldValue = (String) field.get(form);
                    if (!checkRegExpCompliance(regExp, fieldValue)) {
                        inconsistencies.put(field.getName(), "");
                    }
                } else if (annotation instanceof Pass) {
                    pass = (String) field.get(form);                          ///////////////////////// add to login form errors messages!!!!!!!!!!!!!!!!
                } else if (annotation instanceof PassConfirmation) {
                    String passConfirmation = (String) field.get(form);
                    if (!checkPasswordsCompliance(pass, passConfirmation)) {
                        inconsistencies.put(field.getName(), "");
                    }
                } else if (annotation instanceof Email) {
                    String email = (String) field.get(form);
                    if (!checkEmailIsFree(email)) {
                        inconsistencies.put("notFreeEmail", "");
                    }
                } else if (annotation instanceof NotEmpty) {
                    Object fieldValue = field.get(form);
                    if(checkEmpty(fieldValue)){
                        inconsistencies.put(field.getName(), "");
                    }
                }
            }
            field.setAccessible(false);
        }
        return inconsistencies;
    }

    private static boolean checkRegExpCompliance(String regExp, String fieldValue) {
        return Pattern.matches(regExp, fieldValue);
    }

    private static boolean checkPasswordsCompliance(String pass, String passConfirmation) {
        return pass.equals(passConfirmation);
    }

    private static boolean checkEmailIsFree(String email) {
        return UsersDBService.isUserEmailFree(email);
    }

    private static boolean checkEmpty(Object fieldValue) {
        if (fieldValue != null) {
            if (fieldValue instanceof String) {
                return ((String) fieldValue).trim().length() == 0;
            } else {
                return false;
            }
        }
        return true;
    }
}
