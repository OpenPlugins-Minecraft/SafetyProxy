package me.indian.safetyproxy.messaging;

import me.indian.safetyproxy.AbstractMessageListener;
import me.indian.safetyproxy.MessageHandler;
import me.indian.safetyproxy.MessageService;
import me.indian.safetyproxy.synchronization.UserJoinDataPacket;

public class UserJoinListener extends AbstractMessageListener<UserJoinDataPacket> {

    private final MessageHandler messageHandler;

    public UserJoinListener(final MessageHandler messageHandler) {
        super(UserJoinDataPacket.class, MessageService.SUBJECT);
        this.messageHandler = messageHandler;
    }

    @Override
    public void onMessage(final UserJoinDataPacket type) {
        this.messageHandler.handleMessage(type);
    }
}