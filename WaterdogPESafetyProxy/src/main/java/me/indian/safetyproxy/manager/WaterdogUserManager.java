package me.indian.safetyproxy.manager;

import me.indian.safetyproxy.IUserManager;

import java.util.ArrayList;
import java.util.List;

public class WaterdogUserManager implements IUserManager {

    private final List<String> connectedUsers;

    public WaterdogUserManager() {
        this.connectedUsers = new ArrayList<>();
    }

    @Override
    public void addUser(final String username) {
        this.connectedUsers.add(username);
    }

    @Override
    public boolean isAlive(final String nickname) {
        return this.connectedUsers.contains(nickname);
    }

    @Override
    public void removeUser(final String username) {
        this.connectedUsers.remove(username);
    }
}