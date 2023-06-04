package me.indian.safetyproxy.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import me.indian.safetyproxy.IUserManager;
import me.indian.safetyproxy.SafetyProxyNukkit;
import me.indian.safetyproxy.util.ColorUtil;
import me.indian.safetyproxy.util.TransferUtil;

public class PlayerJoinListener implements Listener {

    private final SafetyProxyNukkit plugin;
    private final IUserManager userManager;

    public PlayerJoinListener(final SafetyProxyNukkit plugin, final IUserManager userManager) {
        this.plugin = plugin;
        this.userManager = userManager;
    }

    @EventHandler
    private void onLogin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if (!this.userManager.isAlive(player.getName())) {
            if (this.plugin.getConfig().getBoolean("transfer-settings.transfer-enabled")) {
                TransferUtil.transfer(player, this.plugin.getConfig().getString("transfer-settings.proxy-address"));
            }
        }
    }
}