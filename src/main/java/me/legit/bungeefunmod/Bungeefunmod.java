package me.legit.bungeefunmod;

import me.legit.bungeefunmod.commands.mod.*;
import me.legit.bungeefunmod.commands.fun.*;
import me.legit.bungeefunmod.discord.WebhookLogger;
import me.legit.bungeefunmod.managers.ConfigManager;
import net.md_5.bungee.api.plugin.Plugin;

public final class Bungeefunmod extends Plugin {

    private static Bungeefunmod instance;
    private ConfigManager configManager;
    private WebhookLogger webhookLogger;

    @Override
    public void onEnable() {
        instance = this;

        configManager = new ConfigManager(this);
        webhookLogger = new WebhookLogger();

        getProxy().getPluginManager().registerCommand(this, new ModReloadCommand());
        getProxy().getPluginManager().registerCommand(this, new HugCommand());
        getProxy().getPluginManager().registerCommand(this, new SlapCommand());
        getProxy().getPluginManager().registerCommand(this, new RoastCommand());
        getProxy().getPluginManager().registerCommand(this, new CoinFlipCommand());
        getProxy().getPluginManager().registerCommand(this, new EightBallCommand());

        getLogger().info("BungeeFunModeration enabled.");

        webhookLogger.sendEmbed(
                "Plugin Enabled",
                "BungeeFunModeration plugin has started successfully.",
                3066993
        );
    }

    @Override
    public void onDisable() {
        getLogger().info("BungeeFunModeration disabled.");

        webhookLogger.sendEmbed(
                "Plugin Disabled",
                "BungeeFunModeration plugin has been stopped.",
                15158332
        );
    }

    public static Bungeefunmod getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public WebhookLogger getWebhookLogger() {
        return webhookLogger;
}
}
