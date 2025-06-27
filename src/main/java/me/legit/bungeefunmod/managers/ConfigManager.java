package me.legit.bungeefunmod.managers;

import net.md_5.bungee.api.plugin.Plugin;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;
import net.md_5.bungee.config.*;

public class ConfigManager {

    private final Plugin plugin;

    private Configuration config;
    private Configuration messages;
    private Configuration punishments;
    private Configuration reports;

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
        createAndLoad("config.yml");
        createAndLoad("messages.yml");
        createAndLoad("punishments.yml");
        createAndLoad("reports.yml");
    }

    private void createAndLoad(String name) {
        File file = new File(plugin.getDataFolder(), name);
        if (!file.exists()) {
            try {
                plugin.getDataFolder().mkdirs();
                InputStream in = plugin.getResourceAsStream(name);
                Files.copy(in, file.toPath());
                plugin.getLogger().info("Created default: " + name);
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not create " + name, e);
            }
        }

        try {
            ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
            switch (name) {
                case "config.yml":
                    config = provider.load(file); break;
                case "messages.yml":
                    messages = provider.load(file); break;
                case "punishments.yml":
                    punishments = provider.load(file); break;
                case "reports.yml":
                    reports = provider.load(file); break;
            }
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not load " + name, e);
        }
    }

    public Configuration getConfig() {
        return config;
    }

    public Configuration getMessages() {
        return messages;
    }

    public Configuration getPunishments() {
        return punishments;
    }

    public Configuration getReports() {
        return reports;
    }

    public void save(Configuration config, String name) {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(plugin.getDataFolder(), name));
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save " + name, e);
        }
    }

    public void reload() {
        createAndLoad("config.yml");
        createAndLoad("messages.yml");
        createAndLoad("punishments.yml");
        createAndLoad("reports.yml");
        plugin.getLogger().info("Configs reloaded.");
    }
}
