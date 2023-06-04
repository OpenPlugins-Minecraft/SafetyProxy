package me.indian.safetyproxy.basic;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginLogger;
import java.util.HashMap;
import java.util.Map;
import me.indian.safetyproxy.SafetyProxyNukkit;
import me.indian.safetyproxy.util.ColorUtil;

public class SafetyProxyNukkitMetrics {

    private final SafetyProxyNukkit plugin;
    private final PluginLogger logger;
    private final Metrics metrics;

    public SafetyProxyNukkitMetrics(final SafetyProxyNukkit plugin, final PluginLogger logger, final Metrics metrics) {
        this.plugin = plugin;
        this.logger = logger;
        this.metrics = metrics;
    }

    public void run() {
        final Thread thread = new Thread(() -> {
            final boolean enabled = this.metrics.isEnabled();
            try {
                if (!enabled) {
                    this.logger.info(ColorUtil.color("&aMetrics is disabled"));
                    return;
                }
                this.customMetrics();
                this.logger.info(ColorUtil.color("&aLoaded Metrics"));
            } catch (final Exception e) {
                this.logger.info(ColorUtil.color("&cCan't load metrics"));
                this.logger.error(e.getMessage());
            }
        });
        thread.setName("SafetyProxy Metrics Thread");
        thread.start();
    }

    private void customMetrics() {
        this.metrics.addCustomChart(new Metrics.SimplePie("serverSoftware", () -> "Nukkit " + this.plugin.getServer().getVersion()));
        this.metrics.addCustomChart(new Metrics.SimplePie("nukkit_version", () -> Server.getInstance().getNukkitVersion()));
        /*
        Code from https://github.com/CloudburstMC/Nukkit/blob/master/src/main/java/cn/nukkit/metrics/NukkitMetrics.java#L47
         */
        this.metrics.addCustomChart(new Metrics.AdvancedPie("player_platform", () -> {
            final Map<String, Integer> valueMap = new HashMap<>();
            this.plugin.getServer().getOnlinePlayers().forEach((uuid, player) -> {
                final String deviceOS = this.mapDeviceOSToString(player.getLoginChainData().getDeviceOS());
                if (!valueMap.containsKey(deviceOS)) {
                    valueMap.put(deviceOS, 1);
                } else {
                    valueMap.put(deviceOS, valueMap.get(deviceOS) + 1);
                }
            });
            return valueMap;
        }));
    }

    private String mapDeviceOSToString(final int os) {
        switch (os) {
            case 1:
                return "Android";
            case 2:
                return "iOS";
            case 3:
                return "macOS";
            case 4:
                return "FireOS";
            case 5:
                return "Gear VR";
            case 6:
                return "Hololens";
            case 7:
                return "Windows 10";
            case 8:
                return "Windows";
            case 9:
                return "Dedicated";
            case 10:
                return "tvos";
            case 11:
                return "PlayStation";
            case 12:
                return "Switch";
            case 13:
                return "Xbox One";
            case 14:
                return "Windows Phone";
            case 15:
                return "Linux";
        }
        return "Unknown";
    }
}