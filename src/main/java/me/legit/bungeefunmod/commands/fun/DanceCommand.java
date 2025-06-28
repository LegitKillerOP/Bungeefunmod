package me.legit.bungeefunmod.commands.fun;

import me.legit.bungeefunmod.Bungeefunmod;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;

public class DanceCommand extends Command {

    private final List<String> danceMoves;

    public DanceCommand() {
        super("dance");
        danceMoves = Bungeefunmod.getInstance().getConfigManager()
                .getMessages().getStringList("dance");

        if (danceMoves.isEmpty()) {
            Bungeefunmod.getInstance().getLogger().warning("No dance moves configured in messages.yml!");
        }
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (danceMoves.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "No dance moves are configured!");
            return;
        }

        String playerName = sender.getName();
        String move = danceMoves.get((int) (Math.random() * danceMoves.size()));
        String message = ChatColor.LIGHT_PURPLE + playerName + " " + move;
        Bungeefunmod.getInstance().getProxy().broadcast(message);
    }
}
