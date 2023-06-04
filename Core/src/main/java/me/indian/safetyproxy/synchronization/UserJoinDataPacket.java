package me.indian.safetyproxy.synchronization;

import org.jetbrains.annotations.NotNull;

public class UserJoinDataPacket {

    private final String nickname;

    public UserJoinDataPacket(@NotNull final String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }
}