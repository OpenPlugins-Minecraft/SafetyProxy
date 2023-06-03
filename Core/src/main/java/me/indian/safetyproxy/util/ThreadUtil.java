package me.indian.safetyproxy.util;

public final class ThreadUtil {

    public static void sleep(final int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (final InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}