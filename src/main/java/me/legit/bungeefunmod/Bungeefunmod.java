package me.legit.bungeefunmod;

import me.legit.bungeefunmod.commands.mod.*;
import me.legit.bungeefunmod.commands.fun.*;
import me.legit.bungeefunmod.discord.WebhookLogger;
import me.legit.bungeefunmod.listeners.*;
import me.legit.bungeefunmod.managers.ConfigManager;
import me.legit.bungeefunmod.managers.PunishmentManager;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.List;

public final class Bungeefunmod extends Plugin {

    private static Bungeefunmod instance;
    private ConfigManager configManager;
    private WebhookLogger webhookLogger;
    private PunishmentManager punishmentManager;

    @Override
    public void onEnable() {
        instance = this;

        configManager = new ConfigManager(this);
        webhookLogger = new WebhookLogger();
        punishmentManager = new PunishmentManager(this);

        getProxy().getPluginManager().registerListener(this, new ChatListener());
        getProxy().getPluginManager().registerListener(this, new LoginListener());
        getProxy().getPluginManager().registerListener(this, new BanEvadeListener());

        getProxy().getPluginManager().registerCommand(this, new ModReloadCommand());
        getProxy().getPluginManager().registerCommand(this, new HugCommand());
        getProxy().getPluginManager().registerCommand(this, new SlapCommand());
        getProxy().getPluginManager().registerCommand(this, new RoastCommand());
        getProxy().getPluginManager().registerCommand(this, new CoinFlipCommand());
        getProxy().getPluginManager().registerCommand(this, new EightBallCommand());
        getProxy().getPluginManager().registerCommand(this, new DanceCommand());
        getProxy().getPluginManager().registerCommand(this, new PetCommand());
        getProxy().getPluginManager().registerCommand(this, new RandomColorCommand());

        getProxy().getPluginManager().registerCommand(this, new BanCommand());
        getProxy().getPluginManager().registerCommand(this, new TempbanCommand());
        getProxy().getPluginManager().registerCommand(this, new UnbanCommand());
        getProxy().getPluginManager().registerCommand(this, new IpBanCommand());
        getProxy().getPluginManager().registerCommand(this, new UnIpBanCommand());
        getProxy().getPluginManager().registerCommand(this, new MuteCommand());
        getProxy().getPluginManager().registerCommand(this, new KickCommand());
        getProxy().getPluginManager().registerCommand(this, new WarnCommand());
        getProxy().getPluginManager().registerCommand(this, new ReportCommand());
        getProxy().getPluginManager().registerCommand(this, new ModLogCommand());
        getProxy().getPluginManager().registerCommand(this, new ModReloadCommand());
        getProxy().getPluginManager().registerCommand(this, new ClearPunishmentsCommand());

        getProxy().getScheduler().schedule(this, () -> {
            List<String> autoUnmuted = punishmentManager.autoUnmuteExpired();
            for (String player : autoUnmuted) {
                getLogger().info("Player: " + player + " has been automatically unmuted.");
            }
        }, 0, 1, java.util.concurrent.TimeUnit.MINUTES);

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

    public PunishmentManager getPunishmentManager() {
        return punishmentManager;
    }
}
