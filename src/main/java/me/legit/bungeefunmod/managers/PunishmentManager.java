package me.legit.bungeefunmod.managers;

import me.legit.bungeefunmod.Bungeefunmod;
import net.md_5.bungee.config.Configuration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PunishmentManager {

    private final Bungeefunmod plugin;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Configuration punishments;
    private Configuration reports;

    public PunishmentManager(Bungeefunmod plugin) {
        this.plugin = plugin;
        loadConfigs();
    }

    public void loadConfigs() {
        this.punishments = plugin.getConfigManager().getPunishments();
        this.reports = plugin.getConfigManager().getReports();
    }

    public void savePunishments() {
        plugin.getConfigManager().save(punishments, "punishments.yml");
    }

    public void saveReports() {
        plugin.getConfigManager().save(reports, "reports.yml");
    }

    // ===== WARN =====

    public void addWarn(String player, String by, String reason, String timestamp) {
        Configuration warns = punishments.getSection("warns");
        if (warns == null) warns = new Configuration();

        List<String> warnList = warns.getStringList(player);
        if (warnList == null) warnList = new ArrayList<>();

        warnList.add("[" + timestamp + "] By: " + by + " - " + reason);
        warns.set(player, warnList);
        punishments.set("warns", warns);

        savePunishments();
    }

    public List<String> getWarns(String player) {
        List<String> warnList = punishments.getStringList("warns." + player);
        return warnList != null ? warnList : new ArrayList<>();
    }

    // ===== MUTE =====

    public void addMute(String player, String staff, String reason, long untilTimestamp) {
        punishments.set("mutes." + player + ".reason", reason);
        punishments.set("mutes." + player + ".by", staff);
        punishments.set("mutes." + player + ".until", untilTimestamp);
        savePunishments();
    }

    public boolean isMuted(String player) {
        if (!punishments.contains("mutes." + player)) return false;
        long until = punishments.getLong("mutes." + player + ".until", 0);
        return until > System.currentTimeMillis();
    }

    public void removeMute(String player) {
        punishments.set("mutes." + player, null);
        savePunishments();
    }

    public List<String> autoUnmuteExpired() {
        List<String> unmutedPlayers = new ArrayList<>();

        if (!punishments.contains("mutes")) return unmutedPlayers;

        Configuration mutesSection = punishments.getSection("mutes");
        if (mutesSection == null) return unmutedPlayers;

        for (String player : mutesSection.getKeys()) {
            long until = punishments.getLong("mutes." + player + ".until", 0);
            if (System.currentTimeMillis() > until) {
                // Mute expired → remove
                punishments.set("mutes." + player, null);
                unmutedPlayers.add(player);
            }
        }

        if (!unmutedPlayers.isEmpty()) {
            savePunishments();
        }

        return unmutedPlayers;
    }

    // ===== BAN =====

    public void addBan(String player, String staff, String reason) {
        punishments.set("bans." + player + ".reason", reason);
        punishments.set("bans." + player + ".by", staff);
        savePunishments();
    }

    public boolean isBanned(String player) {
        return punishments.contains("bans." + player);
    }

    public boolean removeBan(String player) {
        boolean found = false;

        if (punishments.contains("bans." + player)) {
            punishments.set("bans." + player, null);
            found = true;
        }

        if (punishments.contains("tempbans." + player)) {
            punishments.set("tempbans." + player, null);
            found = true;
        }

        if (found) {
            savePunishments();
        }
        return found;
    }

    public void addIpBan(String ip, String staff, String reason, long untilTimestamp) {
        punishments.set("ipbans." + ip + ".reason", reason);
        punishments.set("ipbans." + ip + ".by", staff);
        punishments.set("ipbans." + ip + ".until", untilTimestamp);
        savePunishments();
    }

    public boolean isIpBanned(String ip) {
        if (!punishments.contains("ipbans." + ip)) return false;
        long until = punishments.getLong("ipbans." + ip + ".until", 0);
        if (until != 0 && System.currentTimeMillis() > until) {
            removeIpBan(ip);
            return false;
        }
        return true;
    }

    public void removeIpBan(String ip) {
        punishments.set("ipbans." + ip, null);
        savePunishments();
    }

    public String getIpBanReason(String ip) {
        return punishments.getString("ipbans." + ip + ".reason", "No reason");
    }

    public String getIpBanBy(String ip) {
        return punishments.getString("ipbans." + ip + ".by", "Unknown");
    }

    public long getIpBanUntil(String ip) {
        return punishments.getLong("ipbans." + ip + ".until", 0);
    }

    public void setLastBannedIp(String player, String ip) {
        punishments.set("bannedIps." + player, ip);
        savePunishments();
    }

    public String getLastBannedIp(String player) {
        return punishments.getString("bannedIps." + player, "");
    }

    // ===== TEMPBAN =====

    public void addTempBan(String player, String staff, String reason, long untilTimestamp) {
        punishments.set("tempbans." + player + ".reason", reason);
        punishments.set("tempbans." + player + ".by", staff);
        punishments.set("tempbans." + player + ".until", untilTimestamp);
        savePunishments();
    }

    public boolean isTempBanned(String player) {
        if (!punishments.contains("tempbans." + player)) return false;
        long until = punishments.getLong("tempbans." + player + ".until", 0);
        if (until < System.currentTimeMillis()) {
            // expired → remove
            removeTempBan(player);
            return false;
        }
        return true;
    }

    public void removeTempBan(String player) {
        punishments.set("tempbans." + player, null);
        savePunishments();
    }

    // ===== REPORT =====

    public void addReport(String player, String reporter, String reason) {
        String timestamp = dateFormat.format(new Date());
        List<String> reportList = reports.getStringList(player);
        if (reportList == null) reportList = new ArrayList<>();
        reportList.add("[" + timestamp + "] By: " + reporter + " - " + reason);
        reports.set(player, reportList);
        saveReports();
    }

    public List<String> getReports(String player) {
        List<String> reportList = reports.getStringList(player);
        return reportList != null ? reportList : new ArrayList<>();
    }

    // ===== CLEAR ALL =====

    public void clearAll(String player) {
        boolean changed = false;

        if (punishments.contains("warns." + player)) {
            punishments.set("warns." + player, null);
            changed = true;
        }
        if (punishments.contains("mutes." + player)) {
            punishments.set("mutes." + player, null);
            changed = true;
        }
        if (punishments.contains("bans." + player)) {
            punishments.set("bans." + player, null);
            changed = true;
        }
        if (punishments.contains("tempbans." + player)) {
            punishments.set("tempbans." + player, null);
            changed = true;
        }
        if (reports.contains(player)) {
            reports.set(player, null);
            changed = true;
        }
        if (changed) {
            savePunishments();
            saveReports();
        }
    }

    // ===== GETTERS =====

    public String getBanReason(String player) {
        return punishments.getString("bans." + player + ".reason", "No reason");
    }

    public String getBanBy(String player) {
        return punishments.getString("bans." + player + ".by", "Unknown");
    }

    public String getTempBanReason(String player) {
        return punishments.getString("tempbans." + player + ".reason", "No reason");
    }

    public String getTempBanBy(String player) {
        return punishments.getString("tempbans." + player + ".by", "Unknown");
    }

    public long getTempBanUntil(String player) {
        return punishments.getLong("tempbans." + player + ".until", 0);
    }

    public String getMuteReason(String player) {
        return punishments.getString("mutes." + player + ".reason", "No reason");
    }

    public String getMuteBy(String player) {
        return punishments.getString("mutes." + player + ".by", "Unknown");
    }

    public long getMuteUntil(String player) {
        return punishments.getLong("mutes." + player + ".until", 0);
    }
}
