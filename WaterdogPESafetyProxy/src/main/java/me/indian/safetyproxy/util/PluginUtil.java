package me.indian.safetyproxy.util;

import dev.waterdog.waterdogpe.plugin.Plugin;
import dev.waterdog.waterdogpe.utils.exceptions.PluginChangeStateException;

public final class PluginUtil {

    public static void shutdown(final Plugin plugin) {
        try {
            plugin.setEnabled(false);
        } catch (final PluginChangeStateException exception) {
            exception.printStackTrace();
        }
    }
}