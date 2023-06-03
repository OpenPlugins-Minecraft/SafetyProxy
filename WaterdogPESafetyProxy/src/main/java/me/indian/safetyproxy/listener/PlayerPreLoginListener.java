package me.indian.safetyproxy.listener;

import dev.waterdog.waterdogpe.event.defaults.PlayerPreLoginEvent;
import me.indian.safetyproxy.IUserManager;
import me.indian.safetyproxy.SafetyProxyWaterdogPe;
import me.indian.safetyproxy.util.ColorUtil;

public class PlayerPreLoginListener {

    private final SafetyProxyWaterdogPe plugin;
    private final IUserManager userManager;

    public PlayerPreLoginListener(final SafetyProxyWaterdogPe plugin, final IUserManager userManager) {
        this.plugin = plugin;
        this.userManager = userManager;
    }

    public void onPreLogin(final PlayerPreLoginEvent event) {
        final String name = event.getLoginData().getDisplayName();
        if (this.userManager.isAlive(name)) {
            event.setCancelReason(ColorUtil.color(this.plugin.getConfig().getString("messages.already-connected")));
            event.setCancelled(true);
        }
    }
}