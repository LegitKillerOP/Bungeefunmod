package me.legit.bungeefunmod.listeners;

import me.legit.bungeefunmod.Bungeefunmod;
import me.legit.bungeefunmod.managers.PunishmentManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatListener implements Listener {

    private final PunishmentManager punishmentManager = Bungeefunmod.getInstance().getPunishmentManager();

    @EventHandler
    public void onPlayerChat(ChatEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer)) return;
        if (event.isCommand()) return;

        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        String name = player.getName();

        if (punishmentManager.isMuted(name)) {
            long until = punishmentManager.getMuteUntil(name);
            String reason = punishmentManager.getMuteReason(name);
            String staff = punishmentManager.getMuteBy(name);

            event.setCancelled(true);

            String untilFormatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(until));
            player.sendMessage(ChatColor.RED + "You are muted!\n" +
                    ChatColor.GRAY + "Reason: " + reason + "\n" +
                    ChatColor.GRAY + "By: " + staff + "\n" +
                    ChatColor.GRAY + "Until: " + untilFormatted);
        }
    }
}
