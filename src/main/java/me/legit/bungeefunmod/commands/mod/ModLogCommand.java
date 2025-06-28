package me.legit.bungeefunmod.commands.mod;

import me.legit.bungeefunmod.Bungeefunmod;
import me.legit.bungeefunmod.managers.PunishmentManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.ChatColor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ModLogCommand extends Command {

    public ModLogCommand() {
        super("modlog", "bungeefunmod.modlog");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /modlog <player>");
            return;
        }

        String target = args[0];
        PunishmentManager pm = Bungeefunmod.getInstance().getPunishmentManager();

        sender.sendMessage(ChatColor.GREEN + "----- Mod Log for " + target + " -----");

        // Warns
        List<String> warns = pm.getWarns(target);
        if (warns.isEmpty()) {
            sender.sendMessage(ChatColor.YELLOW + "No warnings found.");
        } else {
            sender.sendMessage(ChatColor.GOLD + "Warnings:");
            warns.forEach(warn -> sender.sendMessage(ChatColor.GRAY + " - " + warn));
        }

        // Mutes
        if (pm.isMuted(target)) {
            String reason = pm.getMuteReason(target);
            String by = pm.getMuteBy(target);
            long until = pm.getMuteUntil(target);
            String untilStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(until));

            sender.sendMessage(ChatColor.GOLD + "Muted:");
            sender.sendMessage(ChatColor.GRAY + " - Reason: " + reason);
            sender.sendMessage(ChatColor.GRAY + " - By: " + by);
            sender.sendMessage(ChatColor.GRAY + " - Until: " + untilStr);
        } else {
            sender.sendMessage(ChatColor.YELLOW + "No active mute.");
        }

        // Bans
        if (pm.isBanned(target)) {
            sender.sendMessage(ChatColor.GOLD + "Banned (Permanent):");
            sender.sendMessage(ChatColor.GRAY + " - Reason: " + pm.getBanReason(target));
            sender.sendMessage(ChatColor.GRAY + " - By: " + pm.getBanBy(target));
        } else if (pm.isTempBanned(target)) {
            long until = pm.getTempBanUntil(target);
            String untilStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(until));
            sender.sendMessage(ChatColor.GOLD + "Banned (Temporary):");
            sender.sendMessage(ChatColor.GRAY + " - Reason: " + pm.getTempBanReason(target));
            sender.sendMessage(ChatColor.GRAY + " - By: " + pm.getTempBanBy(target));
            sender.sendMessage(ChatColor.GRAY + " - Until: " + untilStr);
        } else {
            sender.sendMessage(ChatColor.YELLOW + "No active ban.");
        }

        // Reports
        List<String> reports = pm.getReports(target);
        if (reports.isEmpty()) {
            sender.sendMessage(ChatColor.YELLOW + "No reports found.");
        } else {
            sender.sendMessage(ChatColor.GOLD + "Reports:");
            reports.forEach(report -> sender.sendMessage(ChatColor.GRAY + " - " + report));
        }
    }
}
