package me.dawey.cropcoins.Utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import me.dawey.cropcoins.CropCoins;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {
    private String resourceID;
    private CropCoins plugin;

    public UpdateChecker(String resourceID, CropCoins plugin) {
        this.resourceID = resourceID;
        this.plugin = plugin;
    }

    public void hasUpdate(final Consumer<Boolean> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (InputStream is = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceID + "/~").openStream(); Scanner scann = new Scanner(is)) {
                if (scann.hasNext()) {
                    String version = scann.next();
                    consumer.accept(!plugin.getDescription().getVersion().equalsIgnoreCase(version));
                }
            } catch (IOException e) {
                plugin.getLogger().info("Unable to check for updates: " + e.getMessage());
            }
        });
    }
}
