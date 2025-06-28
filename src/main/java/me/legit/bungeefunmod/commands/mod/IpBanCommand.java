package me.legit.bungeefunmod.commands.mod;

import me.legit.bungeefunmod.Bungeefunmod;
import me.legit.bungeefunmod.managers.PunishmentManager;
import me.legit.bungeefunmod.discord.WebhookLogger;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.ChatColor;

public class IpBanCommand extends Command {

    public IpBanCommand() {
        super("ipban", "bungeefunmod.mod");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /ipban <ip> <duration> <reason>");
            return;
        }

        String ip = args[0];
        String durationStr = args[1];
        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));
        String staffName = sender.getName();

        long durationMs = parseDuration(durationStr);
        if (durationMs < 0) {
            sender.sendMessage(ChatColor.RED + "Invalid duration. Use formats like 5m, 1h, 1d or 0 for permanent.");
            return;
        }

        long until = durationMs == 0 ? 0 : System.currentTimeMillis() + durationMs;

        PunishmentManager pm = Bungeefunmod.getInstance().getPunishmentManager();
        pm.addIpBan(ip, staffName, reason, until);

        sender.sendMessage(ChatColor.YELLOW + "IP " + ip + " has been banned for: " + reason);

        // Log to Discord
        WebhookLogger logger = Bungeefunmod.getInstance().getWebhookLogger();
        logger.sendEmbed("⛔ IP Banned", "**IP:** " + ip + "\n**Duration:** " + durationStr +
                "\n**Reason:** " + reason + "\n**By:** " + staffName, 15158332);
    }

    private long parseDuration(String input) {
        if (input.equals("0")) return 0; // permanent
        try {
            long value = Long.parseLong(input.substring(0, input.length() - 1));
            char unit = input.charAt(input.length() - 1);
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
