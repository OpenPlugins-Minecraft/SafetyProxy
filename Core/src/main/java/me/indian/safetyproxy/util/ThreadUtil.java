package me.indian.safetyproxy.util;

/**
 * The ThreadUtil class provides utility methods for working with threads.
 */
public final class ThreadUtil {

    /**
     * Causes the currently executing thread to sleep (temporarily cease execution) for the specified number of seconds.
     *
     * @param seconds The number of seconds to sleep.
     */
    public static void sleep(final int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (final InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}