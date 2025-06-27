package me.legit.bungeefunmod;

import me.legit.bungeefunmod.commands.mod.ModReloadCommand;
import me.legit.bungeefunmod.managers.ConfigManager;
import net.md_5.bungee.api.plugin.Plugin;

public final class Bungeefunmod extends Plugin {

    private static Bungeefunmod instance;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;

        configManager = new ConfigManager(this);

        getLogger().info("BungeeFunModeration enabled.");

        getProxy().getPluginManager().registerCommand(this, new ModReloadCommand());
    }

    @Override
    public void onDisable() {
        getLogger().info("BungeeFunModeration disabled.");
    }

    public static Bungeefunmod getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
