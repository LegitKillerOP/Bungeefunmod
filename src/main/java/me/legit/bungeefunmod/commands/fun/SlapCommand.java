package me.legit.bungeefunmod.commands.fun;

import me.legit.bungeefunmod.Bungeefunmod;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.ChatColor;

import java.util.Random;

public class SlapCommand extends Command {
    public SlapCommand() {
        super("slap");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage: /slap <player>");
            return;
        }

        ProxiedPlayer target = Bungeefunmod.getInstance().getProxy().getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return;
        }

        String[] items = {"a fish", "a sword", "a cactus", "a slime block"};
        String item = items[new Random().nextInt(items.length)];

        String message = ChatColor.translateAlternateColorCodes('&',
                "&c" + player.getName() + " slapped " + target.getName() + " with " + item + "!");

        player.sendMessage(message);
        target.sendMessage(message);
    }
}
