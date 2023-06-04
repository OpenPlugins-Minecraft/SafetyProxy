package me.indian.safetyproxy.listener;

import dev.waterdog.waterdogpe.event.defaults.PlayerDisconnectedEvent;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import me.indian.safetyproxy.IUserManager;
import me.indian.safetyproxy.MessageService;
import me.indian.safetyproxy.SafetyProxyWaterdogPe;
import me.indian.safetyproxy.synchronization.UserLeaveDataPacket;
import me.indian.safetyproxy.util.ColorUtil;

public class PlayerDisconnectListener {

    private final SafetyProxyWaterdogPe plugin;
    private final IUserManager userManager;
    private final MessageService messageService;

    public PlayerDisconnectListener(final SafetyProxyWaterdogPe plugin, final IUserManager userManager, final MessageService messageService) {
        this.plugin = plugin;
        this.userManager = userManager;
        this.messageService = messageService;
    }

    public void onDisconnect(final PlayerDisconnectedEvent event) {
        final ProxiedPlayer player = event.getPlayer();
        if (this.userManager.isAlive(player.getName())) {
            this.userManager.removeUser(player.getName());

            final UserLeaveDataPacket dataPacket = new UserLeaveDataPacket(player.getName());
            this.messageService.publishMessage(dataPacket, MessageService.SUBJECT);
        } else {
            this.plugin.getLogger().error(ColorUtil.color("&cLeave state at not existing user"));
        }
    }
}