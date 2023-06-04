package me.indian.safetyproxy;

import org.jetbrains.annotations.NotNull;

public interface IUserManager {

    void addUser(@NotNull final String username);

    boolean isAlive(@NotNull final String nickname);

    void removeUser(@NotNull final String username);
}