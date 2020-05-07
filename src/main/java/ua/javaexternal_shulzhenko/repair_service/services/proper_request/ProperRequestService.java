package ua.javaexternal_shulzhenko.repair_service.services.proper_request;

import ua.javaexternal_shulzhenko.repair_service.services.resourses.ApplicationResourceBundle;

import java.io.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ProperRequestService {
    private static final Set<String> APPLICATION_PATHS = new HashSet<>();

    private ProperRequestService() {
    }

    public static void initializeProperRequestService() throws IOException {
        String pathsString = ApplicationResourceBundle.resourceBundle.getString("sr.pr_req.paths");
        String [] paths = pathsString.split(";");
        APPLICATION_PATHS.addAll(Arrays.asList(paths));
    }

    public static boolean isProperRequest(String path) {
        return APPLICATION_PATHS.contains(path);
    }
}
