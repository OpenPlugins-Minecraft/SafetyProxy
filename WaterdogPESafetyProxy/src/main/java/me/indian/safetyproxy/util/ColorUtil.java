package me.indian.safetyproxy.util;

public final class ColorUtil {

    public static String color(final String string) {
        return string.replaceAll("&", "§");
    }
}