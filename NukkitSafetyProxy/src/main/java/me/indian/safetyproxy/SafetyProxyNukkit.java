package  me.indian.safetyproxy;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.utils.Config;
import io.nats.client.Options;
import me.indian.safetyproxy.listener.PlayerPreLoginListener;
import me.indian.safetyproxy.message.NatsMessageService;
import me.indian.safetyproxy.message.RedisMessageService;
import me.indian.safetyproxy.util.JavaUtil;
import me.indian.safetyproxy.util.SystemUtil;
import me.indian.safetyproxy.util.ThreadUtil;

import java.util.Locale;

public class SafetyProxyNukkit extends PluginBase {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        if (SystemUtil.isRoot()) {
            this.getLogger().error("** YOU ARE RUNNING AS ROOT USER **");
            this.getLogger().error(" ");
            this.getLogger().error("Minecraft server software does not require root privileges");
            this.getLogger().error("You should restart your server on different user account");
            this.getLogger().error(" ");
            this.getLogger().error("Loading will be continued in 5 seconds...");
        }
        ThreadUtil.sleep(5);

        final PluginManager pluginManager = this.getServer().getPluginManager();
        final Config config = this.getConfig();

        final String serviceType = config.getString("messaging-service.type");
        final MessageService messageService;
        switch (serviceType.toUpperCase(Locale.ROOT)) {
            case "NATS":
                final Options options = new Options.Builder()
                        .server("nats://" + config.getString("messaging-service.host") + ":" + config.getInt("messaging-service.port"))
                        .userInfo(config.getString("messaging-service.username"), config.getString("messaging-service.password"))
                        .maxReconnects(-1)
                        .build();
                messageService = new NatsMessageService(options);
                break;
            case "REDIS":
                messageService = new RedisMessageService(config.getString("messaging-service.host"), config.getInt("messaging-service.port"));
                break;
            default:
                this.getLogger().error("** INVALID MESSAGING SERVICE TYPE **");
                this.getLogger().error("Plugin is disabling....");
                pluginManager.disablePlugin(this);
                break;
        }

        pluginManager.registerEvents(new PlayerPreLoginListener(this), this);

        if (JavaUtil.isJavaVersionLessThan11()) {
            this.getLogger().warning("** UNSUPPORTED JAVA VERSION DETECTED **");
            this.getLogger().warning(" ");
            this.getLogger().warning("> Hey! You are using unsupported Java version");
            this.getLogger().warning("> Please update to Java 11 or higher");
            this.getLogger().warning(" ");
        }
    }
}