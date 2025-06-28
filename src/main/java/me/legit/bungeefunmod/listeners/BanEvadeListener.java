package me.legit.bungeefunmod.listeners;

import me.legit.bungeefunmod.Bungeefunmod;
import me.legit.bungeefunmod.managers.PunishmentManager;
import me.legit.bungeefunmod.discord.WebhookLogger;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BanEvadeListener implements Listener {

    private final PunishmentManager pm = Bungeefunmod.getInstance().getPunishmentManager();
    private final WebhookLogger webhook = Bungeefunmod.getInstance().getWebhookLogger();

    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        PendingConnection connection = event.getConnection();
        String playerName = connection.getName();
        String ip = connection.getAddress().getAddress().getHostAddress();

        boolean banned = pm.isBanned(playerName) || pm.isTempBanned(playerName);

        if (banned) {
            String lastIp = pm.getLastBannedIp(playerName);
            if (!ip.equals(lastIp)) {
                pm.setLastBannedIp(playerName, ip);

                String message = "**Ban Evade Detected!**\n" +
                        "Player: " + playerName + "\n" +
                        "New IP: " + ip;

                Bungeefunmod.getInstance().getLogger().warning(message);
                webhook.sendEmbed("⚠️ Ban Evade Alert", message, 16753920);
            }
        }
    }
}
