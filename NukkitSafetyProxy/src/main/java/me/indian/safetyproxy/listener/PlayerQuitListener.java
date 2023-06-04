package me.indian.safetyproxy.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import me.indian.safetyproxy.IUserManager;
import me.indian.safetyproxy.SafetyProxyNukkit;

public class PlayerQuitListener implements Listener {

    private final SafetyProxyNukkit plugin;
    private final IUserManager userManager;

    public PlayerQuitListener(final SafetyProxyNukkit plugin, final IUserManager userManager) {
        this.plugin = plugin;
        this.userManager = userManager;
    }

    @EventHandler
    private void onDisconnect(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        if (!this.userManager.isAlive(player.getName())) {
            this.plugin.getLogger().error("&cleave state at non existing use");
        }
    }
}