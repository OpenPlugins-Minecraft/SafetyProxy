package me.indian.safetyproxy.synchronization;

import org.jetbrains.annotations.NotNull;

public class UserLeaveDataPacket {

    private final String nickname;

    public UserLeaveDataPacket(@NotNull final String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }
}