package me.legit.bungeefunmod.commands.fun;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.util.Random;

public class RandomColorCommand extends Command {

    private static final ChatColor[] COLORS = {
            ChatColor.RED, ChatColor.GREEN, ChatColor.BLUE,
            ChatColor.YELLOW, ChatColor.AQUA, ChatColor.LIGHT_PURPLE,
            ChatColor.GOLD, ChatColor.DARK_GREEN, ChatColor.DARK_BLUE,
            ChatColor.DARK_RED, ChatColor.DARK_AQUA, ChatColor.DARK_PURPLE
    };

    private final Random random = new Random();

    public RandomColorCommand() {
        super("randomcolor");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ChatColor color = COLORS[random.nextInt(COLORS.length)];
        sender.sendMessage(color + "Your name color is now " + color.name() + " (just this message)!");
    }
}
