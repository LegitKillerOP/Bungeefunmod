package me.legit.bungeefunmod;

import me.legit.bungeefunmod.commands.mod.*;
import me.legit.bungeefunmod.commands.fun.*;
import me.legit.bungeefunmod.managers.ConfigManager;
import net.md_5.bungee.api.plugin.Plugin;

public final class Bungeefunmod extends Plugin {

    private static Bungeefunmod instance;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;

        configManager = new ConfigManager(this);

        getProxy().getPluginManager().registerCommand(this, new ModReloadCommand());
        getProxy().getPluginManager().registerCommand(this, new HugCommand());
        getProxy().getPluginManager().registerCommand(this, new SlapCommand());
        getProxy().getPluginManager().registerCommand(this, new RoastCommand());
        getProxy().getPluginManager().registerCommand(this, new CoinFlipCommand());
        getProxy().getPluginManager().registerCommand(this, new EightBallCommand());

        getLogger().info("BungeeFunModeration enabled.");
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
