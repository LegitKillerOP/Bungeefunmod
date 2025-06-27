package me.legit.bungeefunmod.commands.mod;

import me.legit.bungeefunmod.Bungeefunmod;
import me.legit.bungeefunmod.managers.ConfigManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.ChatColor;

public class ModReloadCommand extends Command {

    public ModReloadCommand() {
        super("modreload", "bungeefunmod.admin");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ConfigManager configManager = Bungeefunmod.getInstance().getConfigManager();

        configManager.reload();

        sender.sendMessage(ChatColor.GREEN + "[BungeeFunMod] All configuration files reloaded.");
    }
}
