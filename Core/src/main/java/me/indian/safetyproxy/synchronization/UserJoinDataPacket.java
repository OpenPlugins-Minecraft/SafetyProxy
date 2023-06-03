package me.indian.safetyproxy.synchronization;

import me.indian.safetyproxy.DataPacket;

public class UserJoinDataPacket extends DataPacket {

    private final String nickname;

    public UserJoinDataPacket(final String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }
}