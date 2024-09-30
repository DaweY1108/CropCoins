package me.dawey.cropcoins;

import me.dawey.cropcoins.API.CropCoinsAPI;
import me.dawey.cropcoins.Commands.*;
import me.dawey.cropcoins.Database.CropCoinData;
import me.dawey.cropcoins.Database.Database;
import me.dawey.cropcoins.Listeners.CropListener;
import me.dawey.cropcoins.Listeners.PlayerJoinListener;
import me.dawey.cropcoins.Utils.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class CropCoins extends JavaPlugin {

    private Database db;
    private CropCoinData cropCoinData;
    private CropCoinsAPI cropCoinsAPI;
    private CropBreakHandler cropBreakHandler;
    private Map<String, CropCoin> cropCoinValues;
    private Language language;
    private long maxCropCoins;
    private int saveTimeout;
    private int topAmount;
    public Balance balance;
    public Set set;
    public Send send;
    public Give give;
    public Help help;
    public Reload reload;
    public Top top;
    public Take take;
    public Reset reset;

    @Override
    public void onEnable() {
        Logger.logo();
        loadConfig();
        loadLangFile();
        loadDatabase();
        loadCommands();
        loadListeners();
        registerPapi();
    }


    private void loadListeners() {
        Bukkit.getPluginManager().registerEvents(new CropListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        cropBreakHandler = new CropBreakHandler(this);
        cropBreakHandler.startCounter();
    }

    private void loadLangFile() {
        language = new Language(this);
    }

    private void registerPapi() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Logger.getLogger().info("PlaceholderAPI found! Enabling PAPI Hook");
            new PAPI(this).register();
        } else {
            Logger.getLogger().warn("PlaceholderAPI not found! Disabling PAPI Hook");
        }
    }

    private void loadConfig() {
        this.saveDefaultConfig();
        this.reloadConfig();
        this.maxCropCoins = this.getConfig().getLong("max-crop-coin-value");
        this.saveTimeout = this.getConfig().getInt("value-database-write-wait");
        this.topAmount = this.getConfig().getInt("top-amount");
        cropCoinValues = new HashMap<>();
        for (String key : this.getConfig().getConfigurationSection("minecraft").getKeys(false)) {
            ConfigurationSection cropSection = this.getConfig().getConfigurationSection("minecraft." + key);
            for (String world : cropSection.getKeys(false)) {
                ConfigurationSection worldSection = cropSection.getConfigurationSection(world);
                cropCoinValues.put(world + ":minecraft:" + key, new CropCoin(worldSection.getInt("min"), worldSection.getInt("max"), worldSection.getInt("chance")));
            }

        }
        if (Bukkit.getPluginManager().getPlugin("CustomCrops") != null) {
            for (String key : this.getConfig().getConfigurationSection("custom-crops").getKeys(false)) {
                ConfigurationSection cropSection = this.getConfig().getConfigurationSection("custom-crops." + key);
                for (String world : cropSection.getKeys(false)) {
                    ConfigurationSection worldSection = cropSection.getConfigurationSection(world);
                    cropCoinValues.put(world + ":custom-crops:" + key, new CropCoin(worldSection.getInt("min"), worldSection.getInt("max"), worldSection.getInt("chance")));
                }
            }
        }
    }

    private void loadDatabase() {
        this.db = new Database(this);
        this.db.init();
        cropCoinData = new CropCoinData(this);
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.getCropCoinData().checkPlayer(player.getName());
        }
    }

    private void loadCommands() {
        MainCommand mainCommand = new MainCommand(this);
        Objects.requireNonNull(Bukkit.getPluginCommand("cropcoins")).setExecutor(mainCommand);
        Objects.requireNonNull(Bukkit.getPluginCommand("cropcoins")).setTabCompleter(mainCommand);

        balance = new Balance(this);
        set = new Set(this);
        send = new Send(this);
        give = new Give(this);
        help = new Help(this);
        reload = new Reload(this);
        top = new Top(this);
        take = new Take(this);
        reset = new Reset(this);

    }

    public void reload() {
        loadConfig();
        loadDatabase();
        language.reload();
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.getCropCoinData().checkPlayer(player.getName());
        }
    }

    public Database getDatabase() {
        return this.db;
    }

    public CropCoinData getCropCoinData() {
        return this.cropCoinData;
    }

    public Map<String, CropCoin> getCropCoinValues() {
        return this.cropCoinValues;
    }

    public long getMaxCropCoins() {
        return this.maxCropCoins;
    }

    public int getSaveTimeout() {
        return this.saveTimeout;
    }

    public CropBreakHandler getCropBreakHandler() {
        return this.cropBreakHandler;
    }

    public YamlConfiguration getLangFile() {
        return this.language.get();
    }

    public int getTopAmount() {
        return this.topAmount;
    }

    public void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatManager.format(message)));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
