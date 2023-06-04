package me.indian.safetyproxy.util;

import cn.nukkit.Player;
import java.net.InetSocketAddress;

public final class TransferUtil {

    public static void transfer(final Player player, final String address) {
        final String[] addressSplit = address.split(":");
        final InetSocketAddress socketAddress = new InetSocketAddress(addressSplit[0],
                Integer.parseInt(addressSplit[1]));
        player.transfer(socketAddress);
    }
}