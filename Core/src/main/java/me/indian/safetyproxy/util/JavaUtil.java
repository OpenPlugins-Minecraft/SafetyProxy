package me.indian.safetyproxy.util;

public final class JavaUtil {

    public static final String JAVA_VERSION = System.getProperty("java.version");

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