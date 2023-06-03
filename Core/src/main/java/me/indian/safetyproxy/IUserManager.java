package me.indian.safetyproxy;
public interface IUserManager {

    void addUser(final String username);

    boolean isAlive(final String nickname);

    void removeUser(final String username);
}