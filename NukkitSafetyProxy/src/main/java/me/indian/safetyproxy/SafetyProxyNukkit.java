package  me.indian.safetyproxy;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import io.nats.client.Options;
import me.indian.safetyproxy.listener.PlayerLoginListener;
import me.indian.safetyproxy.message.NatsMessageService;
import me.indian.safetyproxy.message.RedisMessageService;

public class SafetyProxyNukkit extends PluginBase {

    private static SafetyProxyNukkit instance;
    private MessageService service;

    public static SafetyProxyNukkit getInstance() {
        return instance;
    }

    public MessageService getService() {
        return this.service;
    }

    @Override
    public void onLoad() {
        instance = this;
        this.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
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
    }
}