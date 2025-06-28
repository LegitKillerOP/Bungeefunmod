package me.legit.bungeefunmod.commands.fun;

import me.legit.bungeefunmod.Bungeefunmod;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;

public class PetCommand extends Command {

    private final List<String> pets;

    public PetCommand() {
        super("pet");

        pets = Bungeefunmod.getInstance().getConfigManager()
                .getMessages().getStringList("pets");

        if (pets.isEmpty()) {
            Bungeefunmod.getInstance().getLogger().warning("No pets configured in messages.yml!");
        }
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (pets.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "No pets are configured!");
            return;
        }

        String pet = pets.get((int) (Math.random() * pets.size()));
        sender.sendMessage(ChatColor.GOLD + "Your pet " + pet + " is happily following you!");
    }
}
