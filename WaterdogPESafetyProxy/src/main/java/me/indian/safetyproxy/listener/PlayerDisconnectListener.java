package me.indian.safetyproxy.listener;

import dev.waterdog.waterdogpe.event.defaults.PlayerDisconnectedEvent;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import me.indian.safetyproxy.IUserManager;
import me.indian.safetyproxy.MessageService;
import me.indian.safetyproxy.synchronization.UserLeaveDataPacket;

public class PlayerDisconnectListener {

    private final IUserManager userManager;
    private final MessageService messageService;

    public PlayerDisconnectListener(final IUserManager userManager, final MessageService messageService) {
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
            throw new IllegalStateException("leave state at not existing user");
        }
    }
}