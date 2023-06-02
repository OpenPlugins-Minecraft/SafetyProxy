package me.indian.safetyproxy.synchronization;

import me.indian.safetyproxy.DataPacket;

public class UserLeaveDataPacket extends DataPacket {

    private final String nickname;

    public UserLeaveDataPacket(final String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }
}