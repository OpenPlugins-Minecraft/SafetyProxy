package me.indian.safetyproxy.util;

/**
 * The JavaUtil class provides utility methods related to Java version.
 */
public final class JavaUtil {

    /**
     * The Java version string.
     */
    public static final String JAVA_VERSION = System.getProperty("java.version");

    /**
     * Checks if the Java version is less than 11.
     *
     * @return {@code true} if the Java version is less than 11, {@code false} otherwise.
     */
    public static boolean isJavaVersionLessThan11() {
        final String[] components = JAVA_VERSION.split("\\.");
        final int majorVersion = Integer.parseInt(components[0]);

        int minorVersion = 0;
        if (components.length > 1) {
            minorVersion = Integer.parseInt(components[1]);
        }
        return (majorVersion < 11) || (majorVersion == 11 && minorVersion == 0);
    }
}