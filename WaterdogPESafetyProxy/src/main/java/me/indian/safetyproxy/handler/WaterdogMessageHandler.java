package me.indian.safetyproxy.handler;

import me.indian.safetyproxy.IUserManager;
import me.indian.safetyproxy.MessageHandler;
import me.indian.safetyproxy.synchronization.UserJoinDataPacket;
import me.indian.safetyproxy.synchronization.UserLeaveDataPacket;

public class WaterdogMessageHandler implements MessageHandler {

    private final IUserManager userManager;

    public WaterdogMessageHandler(final IUserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public void handleMessage(final UserLeaveDataPacket packet) {
        if (this.userManager.isAlive(packet.getNickname())) {
            this.userManager.removeUser(packet.getNickname());
            return;
        }
        throw new IllegalStateException("cannot remove not existing user: " + packet.getNickname());
    }

    @Override
    public void handleMessage(final UserJoinDataPacket packet) {
        this.userManager.addUser(packet.getNickname());
    }
}