package me.indian.safetyproxy;

import me.indian.safetyproxy.synchronization.UserJoinDataPacket;
import me.indian.safetyproxy.synchronization.UserLeaveDataPacket;
import org.jetbrains.annotations.NotNull;

public interface MessageHandler {

    void handleMessage(@NotNull final UserJoinDataPacket packet);

    void handleMessage(@NotNull final UserLeaveDataPacket packet);
}