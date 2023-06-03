package me.indian.safetyproxy;

import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.event.EventManager;
import dev.waterdog.waterdogpe.event.defaults.PlayerPreLoginEvent;
import dev.waterdog.waterdogpe.plugin.Plugin;
import me.indian.safetyproxy.listener.JoinPlayerListener;


public class SafetyProxyWaterdogPe extends Plugin {

    @Override
    public void onEnable() {
        final ProxyServer server = ProxyServer.getInstance();
        final EventManager eventManager = this.getProxy().getEventManager();

        this.saveResource("config.yml");
        this.loadConfig();



        eventManager.subscribe(PlayerPreLoginEvent.class , playerPreLoginEvent -> new JoinPlayerListener().loginEvent(playerPreLoginEvent));
    }
}