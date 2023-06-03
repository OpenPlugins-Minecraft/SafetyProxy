package me.indian.safetyproxy;

import dev.waterdog.waterdogpe.event.EventManager;
import dev.waterdog.waterdogpe.event.defaults.PlayerDisconnectEvent;
import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.event.defaults.PlayerPreLoginEvent;
import dev.waterdog.waterdogpe.plugin.Plugin;
import dev.waterdog.waterdogpe.utils.config.Configuration;
import io.nats.client.Options;
import me.indian.safetyproxy.handler.WaterdogMessageHandler;
import me.indian.safetyproxy.listener.PlayerDisconnectListener;
import me.indian.safetyproxy.listener.PlayerLoginListener;
import me.indian.safetyproxy.listener.PlayerPreLoginListener;
import me.indian.safetyproxy.manager.WaterdogUserManager;
import me.indian.safetyproxy.communication.NatsMessageService;
import me.indian.safetyproxy.communication.RedisMessageService;
import me.indian.safetyproxy.messaging.UserJoinListener;
import me.indian.safetyproxy.messaging.UserLeaveListener;
import me.indian.safetyproxy.util.JavaUtil;
import me.indian.safetyproxy.util.PluginUtil;
import me.indian.safetyproxy.util.SystemUtil;
import me.indian.safetyproxy.util.ThreadUtil;

import java.util.Locale;

public final class SafetyProxyWaterdogPe extends Plugin {

    @Override
    public void onEnable() {
        this.checkForRoot();
        this.saveResource("config.yml");
        this.loadConfig();

        final EventManager eventManager = this.getProxy().getEventManager();
        final Configuration config = this.getConfig();
        final String serviceType = config.getString("messaging-service.type");
        final MessageService messageService;

        if (serviceType.toUpperCase(Locale.ROOT).equals("NATS")) {
            final Options options = new Options.Builder()
                    .server("nats://" + config.getString("messaging-service.host") + ":" + config.getInt("messaging-service.port"))
                    .userInfo(config.getString("messaging-service.username").equals("your_username") ? null : config.getString("messaging-service.username"),
                            config.getString("messaging-service.password").equals("PASTE-YOUR-PASSWORD-HERE") ? null : config.getString("messaging-service.password"))
                    .maxReconnects(-1)
                    .build();
            messageService = new NatsMessageService(options);
        } else if (serviceType.toUpperCase(Locale.ROOT).equals("REDIS")) {
            messageService = new RedisMessageService(config.getString("messaging-service.host"), config.getInt("messaging-service.port"));
        } else {
            this.getLogger().error("** INVALID MESSAGING SERVICE TYPE **");
            this.getLogger().error("Excpected NATS or REDIS but found " + serviceType);
            this.getLogger().error("Plugin is disabling....");
            PluginUtil.shutdown(this);
            return;
        }

        final IUserManager userManager = new WaterdogUserManager();
        final MessageHandler messageHandler = new WaterdogMessageHandler(userManager);
        messageService.addMessageListener(new UserJoinListener(messageHandler));
        messageService.addMessageListener(new UserLeaveListener(messageHandler));

        eventManager.subscribe(PlayerPreLoginEvent.class, e -> new PlayerPreLoginListener(this, userManager));
        eventManager.subscribe(PlayerDisconnectEvent.class, e -> new PlayerDisconnectListener(userManager, messageService));
        eventManager.subscribe(PlayerLoginEvent.class, e -> new PlayerLoginListener(userManager, messageService));
    }

    private void checkForRoot() {
        if (SystemUtil.isRoot()) {
            this.getLogger().error("** YOU ARE RUNNING AS ROOT USER **");
            this.getLogger().error(" ");
            this.getLogger().error("Minecraft server software does not require root privileges");
            this.getLogger().error("You should restart your server on different user account");
            this.getLogger().error(" ");
            this.getLogger().error("Loading will be continued in 5 seconds...");
            ThreadUtil.sleep(5);
        }
    }
}