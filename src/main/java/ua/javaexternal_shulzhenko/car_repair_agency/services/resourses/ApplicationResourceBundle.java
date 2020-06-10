package ua.javaexternal_shulzhenko.car_repair_agency.services.resourses;

import ua.javaexternal_shulzhenko.car_repair_agency.constants.CommonConstants;

import java.util.ResourceBundle;

public final class ApplicationResourceBundle {

    public static ResourceBundle resourceBundle = ResourceBundle.getBundle(CommonConstants.APPLICATION);

    public static void setTestBundle(){
        resourceBundle = ResourceBundle.getBundle(CommonConstants.TEST_APPLICATION);
    }

    private ApplicationResourceBundle() {
    }
}
