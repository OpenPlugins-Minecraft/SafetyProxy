package me.indian.safetyproxy.util;

/**
 * The SystemUtil class provides utility methods for system-related operations.
 */
public final class SystemUtil {

    /**
     * The username retrieved from the system property.
     */
    private static final String USERNAME = System.getProperty("user.name");

    /**
     * Retrieves the username of the current user.
     *
     * @return The username of the current user.
     */
    public static String getUsername() {
        return USERNAME; // get username from system property
    }

    /**
     * Checks if the current operating system is Windows.
     *
     * @return {@code true} if the current operating system is Windows, {@code false} otherwise.
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").contains("Windows");
    }

    /**
     * Checks if the current user has root/administrator privileges.
     *
     * @return {@code true} if the current user has root/administrator privileges, {@code false} otherwise.
     */
    public static boolean isRoot() {
        if (isWindows()) return false;
        return getUsername().equalsIgnoreCase("root");
    }
}