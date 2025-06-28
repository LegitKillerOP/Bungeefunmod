package me.legit.bungeefunmod.listeners;

import me.legit.bungeefunmod.Bungeefunmod;
import me.legit.bungeefunmod.managers.PunishmentManager;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginListener implements Listener {

    private final PunishmentManager pm = Bungeefunmod.getInstance().getPunishmentManager();

    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        PendingConnection connection = event.getConnection();
        String ip = connection.getAddress().getAddress().getHostAddress();

        if (pm.isIpBanned(ip)) {
            String reason = pm.getIpBanReason(ip);
            String bannedBy = pm.getIpBanBy(ip);
            long until = pm.getIpBanUntil(ip);

            String untilStr = (until == 0) ? "Permanent" : new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(until));
            String kickMsg = "You are IP banned!\nReason: " + reason + "\nBanned by: " + bannedBy + "\nExpires: " + untilStr;

            event.setCancelReason(kickMsg);
            event.setCancelled(true);
        }
    }
}
