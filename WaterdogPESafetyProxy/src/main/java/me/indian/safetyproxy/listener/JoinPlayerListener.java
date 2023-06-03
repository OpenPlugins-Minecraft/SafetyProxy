package me.indian.safetyproxy.listener;

import dev.waterdog.waterdogpe.event.defaults.PlayerPreLoginEvent;

public class JoinPlayerListener {

    public void loginEvent(final PlayerPreLoginEvent event){
        final String name = event.getLoginData().getDisplayName();
        // TODO: implement sending
    }

}
