package me.indian.safetyproxy.synchronization;

import me.indian.safetyproxy.DataPacket;

public class UserJoinDataPacket extends DataPacket {

    private final String nickname;
    private final String ipAddress;

    public UserJoinDataPacket(final String nickname, final String ipAddress) {
        this.nickname = nickname;
        this.ipAddress = ipAddress;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }
}