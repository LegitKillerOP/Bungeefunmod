package me.legit.bungeefunmod.commands.mod;

import me.legit.bungeefunmod.Bungeefunmod;
import me.legit.bungeefunmod.discord.WebhookLogger;
import me.legit.bungeefunmod.managers.PunishmentManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WarnCommand extends Command {

    public WarnCommand() {
        super("warn", "bungeefunmod.mod");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /warn <player> <reason>");
            return;
        }

        String targetName = args[0];
        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        String staffName = sender.getName();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        PunishmentManager pm = Bungeefunmod.getInstance().getPunishmentManager();
        pm.addWarn(targetName, staffName, reason, timestamp);

        sender.sendMessage(ChatColor.YELLOW + "You warned " + targetName + " for: " + reason);

        ProxiedPlayer target = Bungeefunmod.getInstance().getProxy().getPlayer(targetName);
        if (target != null) {
            target.sendMessage(ChatColor.RED + "⚠ You have received a warning!\n" +
                    ChatColor.GRAY + "Reason: " + reason + "\n" +
                    ChatColor.GRAY + "By: " + staffName);
        }

        WebhookLogger logger = Bungeefunmod.getInstance().getWebhookLogger();
        logger.sendEmbed("⚠️ Warning Issued",
                "**Player:** " + targetName +
                        "\n**Reason:** " + reason +
                        "\n**By:** " + staffName +
                        "\n**Time:** " + timestamp,
                16776960);
    }
}
