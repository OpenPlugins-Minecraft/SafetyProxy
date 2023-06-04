package me.indian.safetyproxy.synchronization;

import me.indian.safetyproxy.DataPacket;
import org.jetbrains.annotations.NotNull;

public class UserJoinDataPacket extends DataPacket {

    private final String nickname;

    public UserJoinDataPacket(@NotNull final String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }
}