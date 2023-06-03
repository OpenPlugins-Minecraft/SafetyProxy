package me.indian.safetyproxy.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import me.indian.safetyproxy.IUserManager;
import me.indian.safetyproxy.SafetyProxyNukkit;
import me.indian.safetyproxy.util.ColorUtil;
import me.indian.safetyproxy.util.TransferUtil;

public class PlayerPreLoginListener implements Listener {

    private final SafetyProxyNukkit plugin;
    private final IUserManager userManager;

    public PlayerPreLoginListener(final SafetyProxyNukkit plugin, final IUserManager userManager) {
        this.plugin = plugin;
        this.userManager = userManager;
    }

    @EventHandler
    private void onPreLogin(final PlayerPreLoginEvent event) {
        final Player player = event.getPlayer();
        if (!this.userManager.isAlive(player.getName())) {
            if (this.plugin.getConfig().getBoolean("transfer-settings.transfer-enabled")) {
                TransferUtil.transfer(player, this.plugin.getConfig().getString("transfer-settings.proxy-address"));
                return;
            }
            event.setKickMessage(ColorUtil.color(this.plugin.getConfig().getString("messages.only-proxy-allowed")));
            event.setCancelled(true);
        }
    }
}