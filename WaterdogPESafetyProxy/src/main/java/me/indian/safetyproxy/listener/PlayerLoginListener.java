package me.indian.safetyproxy.listener;

import dev.waterdog.waterdogpe.event.defaults.PlayerAuthenticatedEvent;
import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import me.indian.safetyproxy.IUserManager;
import me.indian.safetyproxy.MessageService;
import me.indian.safetyproxy.synchronization.UserJoinDataPacket;

public class PlayerLoginListener {

    private final IUserManager userManager;
    private final MessageService messageService;

    public PlayerLoginListener(final IUserManager userManager, final MessageService messageService) {
        this.userManager = userManager;
        this.messageService = messageService;
    }

    public void onLogin(final PlayerAuthenticatedEvent event) {

        final ProxiedPlayer player = event.getPlayer();
        this.userManager.addUser(player.getName());

        final UserJoinDataPacket dataPacket = new UserJoinDataPacket(player.getName());
        this.messageService.publishMessage(dataPacket, MessageService.SUBJECT);
    }
}