package ua.javaexternal_shulzhenko.repair_service.services.authorization;

import ua.javaexternal_shulzhenko.repair_service.models.user.Role;
import ua.javaexternal_shulzhenko.repair_service.models.user.User;

import java.util.List;

public class AuthorizationService {
    public static boolean authorize(String servletPath, User user){
        Role role = user.getRole();
        List<String> urls = role.getUrls();
        if(urls.contains(servletPath)){
            return true;
        }
        return false;
    }
}
