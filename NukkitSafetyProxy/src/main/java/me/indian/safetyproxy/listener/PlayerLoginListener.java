package me.indian.safetyproxy.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import me.indian.safetyproxy.SafetyProxyNukkit;

public class PlayerLoginListener implements Listener {

    private final SafetyProxyNukkit plugin;
    public PlayerLoginListener(final SafetyProxyNukkit plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerLogin(final PlayerPreLoginEvent event){
        final Player player = event.getPlayer();
        // TODO: implement getting message from WDPE
    }
}
