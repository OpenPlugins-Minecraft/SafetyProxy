package me.indian.safetyproxy.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import me.indian.safetyproxy.IUserManager;

public class PlayerQuitListener implements Listener {

    private final IUserManager userManager;

    public PlayerQuitListener(final IUserManager userManager) {
        this.userManager = userManager;
    }

    @EventHandler
    private void onDisconnect(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        if (!this.userManager.isAlive(player.getName())) {
            throw new IllegalStateException("leave state at non existing user");
        }
    }
}