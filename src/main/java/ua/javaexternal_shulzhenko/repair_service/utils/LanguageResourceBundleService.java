package ua.javaexternal_shulzhenko.repair_service.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public enum LanguageResourceBundleService {
    INSTANCE;

    private ResourceBundle resourceBundle;
    private final String resourceName = "language";

    LanguageResourceBundleService() {
        resourceBundle = ResourceBundle.getBundle(resourceName, Locale.ENGLISH);
    }

    public void changeResource(Locale locale){
        resourceBundle = ResourceBundle.getBundle(resourceName, locale);
    }

    public String getString(String key){
        return resourceBundle.getString(key);
    }
}
