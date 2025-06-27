package me.legit.bungeefunmod.commands.fun;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.ChatColor;

import java.util.Random;

public class CoinFlipCommand extends Command {

    public CoinFlipCommand() {
        super("coinflip");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String result = new Random().nextBoolean() ? "Heads" : "Tails";
        sender.sendMessage(ChatColor.YELLOW + "Coin landed on: " + ChatColor.GOLD + result);
    }
}
