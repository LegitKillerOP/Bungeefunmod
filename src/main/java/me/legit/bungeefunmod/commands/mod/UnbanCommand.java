package me.legit.bungeefunmod.commands.mod;

import me.legit.bungeefunmod.Bungeefunmod;
import me.legit.bungeefunmod.discord.WebhookLogger;
import me.legit.bungeefunmod.managers.PunishmentManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.ChatColor;

public class UnbanCommand extends Command {

    public UnbanCommand() {
        super("unban", "bungeefunmod.mod");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /unban <player>");
            return;
        }

        String targetName = args[0];
        PunishmentManager pm = Bungeefunmod.getInstance().getPunishmentManager();

        boolean removed = pm.removeBan(targetName);

        if (removed) {
            sender.sendMessage(ChatColor.GREEN + "Unbanned player: " + targetName);

            // Discord log
            WebhookLogger logger = Bungeefunmod.getInstance().getWebhookLogger();
            logger.sendEmbed("✅ Player Unbanned",
                    "**Player:** " + targetName + "\n**By:** " + sender.getName(),
                    5763719);
        } else {
            sender.sendMessage(ChatColor.RED + "That player is not banned.");
        }
    }
}
