package me.legit.bungeefunmod.commands.mod;

import me.legit.bungeefunmod.Bungeefunmod;
import me.legit.bungeefunmod.discord.WebhookLogger;
import me.legit.bungeefunmod.managers.PunishmentManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.ChatColor;

public class BanCommand extends Command {

    public BanCommand() {
        super("ban", "bungeefunmod.mod");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /ban <player> <reason>");
            return;
        }

        String targetName = args[0];
        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        String staffName = sender.getName();

        PunishmentManager pm = Bungeefunmod.getInstance().getPunishmentManager();

        // Add permanent ban (-1 = permanent)
        pm.addBan(targetName, staffName, reason);

        // Kick player if online
        ProxiedPlayer target = Bungeefunmod.getInstance().getProxy().getPlayer(targetName);
        if (target != null) {
            target.disconnect(ChatColor.RED + "You have been permanently banned!\nReason: " + reason);
        }

        String message = ChatColor.RED + targetName + " has been permanently banned for: " + reason;
        Bungeefunmod.getInstance().getProxy().broadcast(message);

        // Log to Discord
        WebhookLogger logger = Bungeefunmod.getInstance().getWebhookLogger();
        logger.sendEmbed("🔨 Ban Issued",
                "**Player:** " + targetName +
                        "\n**Reason:** " + reason +
                        "\n**By:** " + staffName,
                15105570);
    }
}
