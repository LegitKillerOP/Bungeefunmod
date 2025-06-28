package me.legit.bungeefunmod.commands.mod;

import me.legit.bungeefunmod.Bungeefunmod;
import me.legit.bungeefunmod.managers.PunishmentManager;
import me.legit.bungeefunmod.discord.WebhookLogger;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.ChatColor;

public class UnIpBanCommand extends Command {

    public UnIpBanCommand() {
        super("unipban", "bungeefunmod.mod");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /unipban <ip>");
            return;
        }

        String ip = args[0];
        PunishmentManager pm = Bungeefunmod.getInstance().getPunishmentManager();

        if (!pm.isIpBanned(ip)) {
            sender.sendMessage(ChatColor.YELLOW + "IP " + ip + " is not banned.");
            return;
        }

        pm.removeIpBan(ip);
        sender.sendMessage(ChatColor.GREEN + "IP " + ip + " has been unbanned.");

        // Log to Discord
        WebhookLogger logger = Bungeefunmod.getInstance().getWebhookLogger();
        logger.sendEmbed("✅ IP Unbanned",
                "**IP:** " + ip +
                        "\n**By:** " + sender.getName(),
                3066993);
    }
}
