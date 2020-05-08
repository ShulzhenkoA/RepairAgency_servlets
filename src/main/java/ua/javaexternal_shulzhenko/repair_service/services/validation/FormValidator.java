package ua.javaexternal_shulzhenko.repair_service.services.validation;

import ua.javaexternal_shulzhenko.repair_service.models.forms.Form;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.UsersDBService;
import ua.javaexternal_shulzhenko.repair_service.services.validation.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class FormValidator {

    public static Set<String> validateForm(Form form) throws IllegalAccessException {
        Field[] fields = form.getClass().getDeclaredFields();
        Set<String> inconsistencies = new HashSet<>();
        String pass = null;
        int id = 0;
        for (Field field : fields) {
            Annotation[] fieldAnnotations = field.getAnnotations();
            field.setAccessible(true);                                            //////////////////////////////////////check if must set accessible false!!!?????;
            for (Annotation annotation : fieldAnnotations) {
                if (annotation instanceof MustConform) {
                    MustConform mustConformAnnotation = (MustConform) annotation;
                    String regExp = mustConformAnnotation.value().getExpression();
                    String fieldValue = (String) field.get(form);
                    if (!checkRegExpCompliance(regExp, fieldValue)) {
                        inconsistencies.add(field.getName());
                    }
                } else if (annotation instanceof Email) {
                    String email = (String) field.get(form);
                    if (!checkEmailIsFree(email, id)) {
                        inconsistencies.add("notFreeEmail");
                    }
                } else if (annotation instanceof Pass) {
                    pass = (String) field.get(form);
                } else if (annotation instanceof PassConfirmation) {
                    String passConfirmation = (String) field.get(form);
                    if (!checkPasswordsCompliance(pass, passConfirmation)) {
                        inconsistencies.add(field.getName());
                    }
                } else if (annotation instanceof UserID) {
                    id = field.getInt(form);
                } else if (annotation instanceof NotEmpty) {
                    Object fieldValue = field.get(form);
                    if (checkEmpty(fieldValue)) {
                        inconsistencies.add(field.getName());
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

    private static boolean checkEmailIsFree(String email, int id) {
        if (UsersDBService.isUserEmailFree(email)) {
            return true;
        } else if (!UsersDBService.isUserEmailFree(email) && id > 0) {
            return UsersDBService.getUserByEmail(email).getId() == id;
        } else {
            return false;
        }
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
