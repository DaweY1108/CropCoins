package me.dawey.cropcoins.Utils;

import me.dawey.cropcoins.CropCoins;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Language {

    private CropCoins plugin;
    private YamlConfiguration config;

    public Language(CropCoins plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        if (!file.exists()) {
            plugin.saveResource("messages.yml", false);
            file = new File(plugin.getDataFolder(), "messages.yml");
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void reload() {
        load();
    }

    public YamlConfiguration get() {
        return config;
    }

}
