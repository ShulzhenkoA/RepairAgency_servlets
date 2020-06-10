package ua.javaexternal_shulzhenko.car_repair_agency.services.authorization;

import ua.javaexternal_shulzhenko.car_repair_agency.constants.Role;
import ua.javaexternal_shulzhenko.car_repair_agency.models.user.User;

import java.util.List;

public final class AuthorizationService {
    public static boolean authorize(String servletPath, User user){
        Role role = user.getRole();
        List<String> urls = role.getUrls();
        if(urls.contains(servletPath)){
            return true;
        }
        return false;
    }
}
