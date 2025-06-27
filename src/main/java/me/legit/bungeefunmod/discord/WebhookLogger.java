package me.legit.bungeefunmod.discord;

import me.legit.bungeefunmod.Bungeefunmod;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class WebhookLogger {

    private final String webhookUrl;

    public WebhookLogger() {
        this.webhookUrl = Bungeefunmod.getInstance().getConfigManager()
                .getConfig().getString("webhook-url", "");
    }

    /**
     * Sends a simple embed message to Discord webhook.
     * @param title The embed title.
     * @param description The embed description.
     * @param color The embed color integer (decimal).
     */
    public void sendEmbed(String title, String description, int color) {
        if (webhookUrl.isEmpty()) {
            Bungeefunmod.getInstance().getLogger().warning("Discord webhook URL is not configured.");
            return;
        }

        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");

            String jsonPayload = "{"
                    + "\"embeds\":[{"
                    + "\"title\":\"" + escapeJson(title) + "\","
                    + "\"description\":\"" + escapeJson(description) + "\","
                    + "\"color\":" + color
                    + "}]"
                    + "}";

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            if (responseCode != 204 && responseCode != 200) {
                Bungeefunmod.getInstance().getLogger()
                        .warning("Failed to send webhook message. Response code: " + responseCode);
            }

            con.disconnect();
        } catch (Exception e) {
            Bungeefunmod.getInstance().getLogger().log(Level.SEVERE, "Error sending Discord webhook message", e);
        }
    }

    private String escapeJson(String text) {
        return text.replace("\"", "\\\"").replace("\n", "\\n");
    }
}
