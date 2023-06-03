package me.indian.safetyproxy;

import me.indian.safetyproxy.synchronization.UserJoinDataPacket;
import me.indian.safetyproxy.synchronization.UserLeaveDataPacket;

public interface MessageHandler {

    void handleMessage(final UserJoinDataPacket packet);

    void handleMessage(final UserLeaveDataPacket packet);
}