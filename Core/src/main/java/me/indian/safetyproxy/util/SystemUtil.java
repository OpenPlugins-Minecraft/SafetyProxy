package me.indian.safetyproxy.util;

public final class SystemUtil {

    private static final String USERNAME = System.getProperty("user.name");

    public static String getUsername() {
        return USERNAME; // get username from system property
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").contains("Windows");
    }

    public static boolean isRoot() {
        if (isWindows()) return false;
        return getUsername().equalsIgnoreCase("root");
    }
}