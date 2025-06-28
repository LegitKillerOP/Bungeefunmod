package me.legit.bungeefunmod.commands.mod;

import me.legit.bungeefunmod.Bungeefunmod;
import me.legit.bungeefunmod.discord.WebhookLogger;
import me.legit.bungeefunmod.managers.PunishmentManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.ChatColor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TempbanCommand extends Command {

    public TempbanCommand() {
        super("tempban", "bungeefunmod.mod");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /tempban <player> <duration> <reason>");
            return;
        }

        String targetName = args[0];
        String durationStr = args[1];
        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));
        String staffName = sender.getName();

        long durationMs = parseDuration(durationStr);
        if (durationMs <= 0) {
            sender.sendMessage(ChatColor.RED + "Invalid duration. Use formats like 5m, 2h, 1d.");
            return;
        }

        long until = System.currentTimeMillis() + durationMs;

        PunishmentManager pm = Bungeefunmod.getInstance().getPunishmentManager();
        pm.addTempBan(targetName, staffName, reason, until);

        ProxiedPlayer target = Bungeefunmod.getInstance().getProxy().getPlayer(targetName);
        if (target != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            target.disconnect(ChatColor.RED + "You are temporarily banned!\nReason: " + reason +
                    "\nUntil: " + sdf.format(new Date(until)));
        }

        String msg = ChatColor.RED + targetName + " has been tempbanned for: " + reason;
        Bungeefunmod.getInstance().getProxy().broadcast(msg);

        // Discord log
        WebhookLogger logger = Bungeefunmod.getInstance().getWebhookLogger();
        logger.sendEmbed("⏰ Tempban Issued", "**Player:** " + targetName +
                "\n**Reason:** " + reason + "\n**Duration:** " + durationStr +
                "\n**By:** " + staffName, 15844367);
    }

    private long parseDuration(String input) {
        try {
            char unit = input.charAt(input.length() - 1);
            long value = Long.parseLong(input.substring(0, input.length() - 1));

            switch (unit) {
                case 's': return value * 1000L;
                case 'm': return value * 60 * 1000L;
                case 'h': return value * 60 * 60 * 1000L;
                case 'd': return value * 24 * 60 * 60 * 1000L;
                default: return -1;
            }
        } catch (Exception e) {
            return -1;
        }
    }
}
