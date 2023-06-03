package  me.indian.safetyproxy;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import io.nats.client.Options;
import me.indian.safetyproxy.listener.PlayerLoginListener;
import me.indian.safetyproxy.message.NatsMessageService;
import me.indian.safetyproxy.message.RedisMessageService;
import me.indian.safetyproxy.util.JavaUtil;
import me.indian.safetyproxy.util.SystemUtil;
import me.indian.safetyproxy.util.ThreadUtil;

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

        final PluginManager pm = this.getServer().getPluginManager();
        final String serviceType = this.getConfig().getString("service.type");
        final String ip = this.getConfig().getString("service.ip");
        final int port = this.getConfig().getInt("service.port");

        if (serviceType.equalsIgnoreCase("redis")) {
            this.service = new RedisMessageService(ip, port);
        } else if (serviceType.equalsIgnoreCase("nats")) {
            this.service = new NatsMessageService(new Options.Builder()
                    .server("nats://" + ip + ":" + port)
                    .maxReconnects(-1)
                    .build());
        } else {
            this.getLogger().error("Cant get service type, disabling plugin...");
            pm.disablePlugin(this);
        }
        pm.registerEvents(new PlayerLoginListener(this), this);

        if (JavaUtil.isJavaVersionLessThan11()) {
            this.getLogger().warning("** UNSUPPORTED JAVA VERSION DETECTED **");
            this.getLogger().warning(" ");
            this.getLogger().warning("> Hey! You are using unsupported Java version");
            this.getLogger().warning("> Please update to Java 11 or higher");
            this.getLogger().warning(" ");
        }
    }
}