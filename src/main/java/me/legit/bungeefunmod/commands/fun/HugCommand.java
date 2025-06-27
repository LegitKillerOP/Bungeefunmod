package me.legit.bungeefunmod.commands.fun;

import me.legit.bungeefunmod.Bungeefunmod;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.ChatColor;

public class HugCommand extends Command {

    public HugCommand() {
        super("hug");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage: /hug <player>");
            return;
        }

        ProxiedPlayer target = Bungeefunmod.getInstance().getProxy().getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return;
        }

        String msg = Bungeefunmod.getInstance().getConfigManager()
                .getMessages().getString("hug", "&d{sender} hugs {target}!");

        msg = msg.replace("{sender}", player.getName()).replace("{target}", target.getName());
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
}
