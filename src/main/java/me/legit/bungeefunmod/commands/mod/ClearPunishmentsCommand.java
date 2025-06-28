package me.legit.bungeefunmod.commands.mod;

import me.legit.bungeefunmod.Bungeefunmod;
import me.legit.bungeefunmod.discord.WebhookLogger;
import me.legit.bungeefunmod.managers.PunishmentManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Command;

public class ClearPunishmentsCommand extends Command {

    public ClearPunishmentsCommand() {
        super("clearpunishments", "bungeefunmod.mod");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /clearpunishments <player>");
            return;
        }

        String target = args[0];
        PunishmentManager pm = Bungeefunmod.getInstance().getPunishmentManager();

        // Clear all punishments & reports via manager
        pm.clearAll(target);

        sender.sendMessage(ChatColor.GREEN + "Cleared all punishments and reports for " + target + ".");

        // Discord log
        WebhookLogger logger = Bungeefunmod.getInstance().getWebhookLogger();
        logger.sendEmbed("🧹 Punishments Cleared",
                "**Player:** " + target +
                        "\n**Cleared by:** " + sender.getName(),
                8421504);
    }
}
