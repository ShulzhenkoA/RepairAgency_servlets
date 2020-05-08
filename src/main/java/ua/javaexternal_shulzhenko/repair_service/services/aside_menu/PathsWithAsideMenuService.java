package ua.javaexternal_shulzhenko.repair_service.services.aside_menu;

import ua.javaexternal_shulzhenko.repair_service.services.resourses.ApplicationResourceBundle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PathsWithAsideMenuService {
    private static final Set<String> APPLICATION_PATHS_WITH_ASIDE_MENU = new HashSet<>();

    private PathsWithAsideMenuService() {
    }

    public static void initializePathsWithAsideMenuService() {
        String pathsString = ApplicationResourceBundle.resourceBundle.getString("sr.pr_req.paths");
        String[] paths = pathsString.split(";");
        APPLICATION_PATHS_WITH_ASIDE_MENU.addAll(Arrays.asList(paths));
    }

    public static boolean mustReflectAsideMenu(String path) {
        return APPLICATION_PATHS_WITH_ASIDE_MENU.contains(path);
    }
}
