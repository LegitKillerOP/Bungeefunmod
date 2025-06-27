package me.legit.bungeefunmod.commands.fun;

import me.legit.bungeefunmod.Bungeefunmod;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.Random;

public class RoastCommand extends Command {

    public RoastCommand() {
        super("roast");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage: /roast <player>");
            return;
        }

        ProxiedPlayer target = Bungeefunmod.getInstance().getProxy().getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return;
        }

        List<String> roasts = Bungeefunmod.getInstance().getConfigManager()
                .getMessages().getStringList("roasts");

        if (roasts.isEmpty()) {
            player.sendMessage(ChatColor.RED + "No roasts configured!");
            return;
        }

        String msg = roasts.get(new Random().nextInt(roasts.size()))
                .replace("{target}", target.getName());
        String finalMsg = ChatColor.translateAlternateColorCodes('&', msg);

        player.sendMessage(finalMsg);
        target.sendMessage(finalMsg);
    }
}
