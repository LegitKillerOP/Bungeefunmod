package me.legit.bungeefunmod.commands.mod;

import me.legit.bungeefunmod.Bungeefunmod;
import me.legit.bungeefunmod.discord.WebhookLogger;
import me.legit.bungeefunmod.managers.PunishmentManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.ChatColor;

public class KickCommand extends Command {

    public KickCommand() {
        super("kick", "bungeefunmod.mod");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /kick <player> <reason>");
            return;
        }

        String targetName = args[0];
        ProxiedPlayer target = Bungeefunmod.getInstance().getProxy().getPlayer(targetName);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found or offline.");
            return;
        }

        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        String staff = sender.getName();

        // Kick the player
        target.disconnect(ChatColor.RED + "You were kicked from the server.\nReason: " + reason);
        sender.sendMessage(ChatColor.YELLOW + "You kicked " + target.getName() + " for: " + reason);

        // Optional broadcast to staff with permission
        for (ProxiedPlayer p : Bungeefunmod.getInstance().getProxy().getPlayers()) {
            if (p.hasPermission("bungeefunmod.kick.notify")) {
                p.sendMessage(ChatColor.RED + target.getName() + " was kicked by " + staff + " for: " + reason);
            }
        }

        // If you want to keep track of kicks in your PunishmentManager (optional)
        PunishmentManager pm = Bungeefunmod.getInstance().getPunishmentManager();
        // You could add a method like pm.addKick(...) here if you want to track kicks.

        // Log kick to Discord webhook
        WebhookLogger logger = Bungeefunmod.getInstance().getWebhookLogger();
        logger.sendEmbed("👢 Player Kicked",
                "**Player:** " + targetName + "\n**Reason:** " + reason + "\n**By:** " + staff,
                15548997);
    }
}
