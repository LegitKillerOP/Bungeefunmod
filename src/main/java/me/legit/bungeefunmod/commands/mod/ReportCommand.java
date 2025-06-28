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

public class ReportCommand extends Command {

    public ReportCommand() {
        super("report", null); // no permission required to report
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /report <player> <reason>");
            return;
        }

        ProxiedPlayer reporter = (ProxiedPlayer) sender;
        String reportedName = args[0];
        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        PunishmentManager pm = Bungeefunmod.getInstance().getPunishmentManager();
        pm.addReport(reportedName, reporter.getName(), reason);

        reporter.sendMessage(ChatColor.GREEN + "✅ You reported " + reportedName + " for: " + reason);

        // Notify staff
        for (ProxiedPlayer p : Bungeefunmod.getInstance().getProxy().getPlayers()) {
            if (p.hasPermission("bungeefunmod.report.notify")) {
                p.sendMessage(ChatColor.RED + "📣 New Report: " + reportedName + " by " + reporter.getName());
                p.sendMessage(ChatColor.GRAY + "Reason: " + reason);
            }
        }

        // Discord webhook
        WebhookLogger logger = Bungeefunmod.getInstance().getWebhookLogger();
        logger.sendEmbed("📣 Player Reported",
                "**Reported Player:** " + reportedName +
                        "\n**Reason:** " + reason +
                        "\n**Reported By:** " + reporter.getName() +
                        "\n**Time:** " + timestamp,
                16098851);
    }
}
