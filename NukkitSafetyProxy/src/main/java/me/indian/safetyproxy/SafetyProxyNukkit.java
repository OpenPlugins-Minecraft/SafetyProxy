package me.indian.safetyproxy;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.utils.Config;
import io.nats.client.Options;

import me.indian.safetyproxy.handler.NukkitMessageHandler;
import me.indian.safetyproxy.listener.PlayerPreLoginListener;
import me.indian.safetyproxy.listener.PlayerJoinListener;
import me.indian.safetyproxy.listener.PlayerQuitListener;
import me.indian.safetyproxy.manager.NukkitUserManager;
import me.indian.safetyproxy.communication.NatsMessageService;
import me.indian.safetyproxy.communication.RedisMessageService;
import me.indian.safetyproxy.messaging.UserJoinListener;
import me.indian.safetyproxy.messaging.UserLeaveListener;
import me.indian.safetyproxy.basic.Metrics;
import me.indian.safetyproxy.basic.SafetyProxyNukkitMetrics;
import me.indian.safetyproxy.util.JavaUtil;
import me.indian.safetyproxy.util.SystemUtil;
import me.indian.safetyproxy.util.ThreadUtil;

import java.util.Locale;

public final class SafetyProxyNukkit extends PluginBase {

    @Override
    public void onEnable() {
        System.out.println("działa 1");
        this.checkForRoot();
        this.saveDefaultConfig();

        final PluginManager pluginManager = this.getServer().getPluginManager();
        final Config config = this.getConfig();
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
            pluginManager.disablePlugin(this);
            return;
        }
        final IUserManager userManager = new NukkitUserManager();
        final MessageHandler messageHandler = new NukkitMessageHandler(userManager);
        try {
            messageService.addMessageListener(new UserJoinListener(messageHandler));
            messageService.addMessageListener(new UserLeaveListener(messageHandler));
        } catch (final Exception exception) {
            exception.printStackTrace();
            pluginManager.disablePlugin(this);
            return;
        }
        pluginManager.registerEvents(new PlayerQuitListener(this, userManager), this);
        pluginManager.registerEvents(new PlayerPreLoginListener(this, userManager), this);
        pluginManager.registerEvents(new PlayerJoinListener(this, userManager), this);

        new SafetyProxyNukkitMetrics(this, this.getLogger(), new Metrics(this)).run();
        this.checkForJava11();
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

    private void checkForJava11() {
        if (JavaUtil.isJavaVersionLessThan11()) {
            this.getLogger().warning("** UNSUPPORTED JAVA VERSION DETECTED **");
            this.getLogger().warning(" ");
            this.getLogger().warning("> Hey! You are using unsupported Java version");
            this.getLogger().warning("> Please update to Java 11 or higher");
            this.getLogger().warning(" ");
        }
    }
}