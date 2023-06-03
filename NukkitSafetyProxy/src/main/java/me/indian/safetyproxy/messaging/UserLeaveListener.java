package me.indian.safetyproxy.messaging;

import me.indian.safetyproxy.AbstractMessageListener;
import me.indian.safetyproxy.MessageHandler;
import me.indian.safetyproxy.MessageService;
import me.indian.safetyproxy.synchronization.UserLeaveDataPacket;

public class UserLeaveListener extends AbstractMessageListener<UserLeaveDataPacket> {

    private final MessageHandler messageHandler;

    public UserLeaveListener(final MessageHandler messageHandler) {
        super(UserLeaveDataPacket.class, MessageService.SUBJECT);
        this.messageHandler = messageHandler;
    }

    @Override
    public void onMessage(final UserLeaveDataPacket type) {
        this.messageHandler.handleMessage(type);
    }
}