package me.dawey.cropcoins.Utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.dawey.cropcoins.CropCoins;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PAPI extends PlaceholderExpansion {

    private CropCoins plugin;
    private List<String> top10ScoreNames;
    private List<Long> top10ScoreValues;

    public PAPI(CropCoins plugin) {
        this.plugin = plugin;
        refresh();
        refreshCounter();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "cropcoins";
    }

    @Override
    public @NotNull String getAuthor() {
        return "dawey";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, @NotNull String identifier) {
        if (p == null) {
            return "";
        }
        for (int i = 1; i <= this.top10ScoreNames.size(); i++) {
            if (identifier.equalsIgnoreCase("top_" + i + "_player")) {
                try {
                    return this.top10ScoreNames.get(i - 1);
                } catch (Exception e) {
                    return "N/A";
                }
            }
            if (identifier.equalsIgnoreCase("top_" + i + "_value")) {
                try {
                    return String.valueOf(this.top10ScoreValues.get(i - 1));
                } catch (Exception e) {
                    return "N/A";
                }
            }
        }
        if (identifier.equalsIgnoreCase("balance")) {
            return String.valueOf(plugin.getCropCoinData().getCoins(p.getName()));
        }
        return null;
    }

    private void refreshCounter() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, 0L, 20L * 10L);
    }

    private void refresh() {
        top10ScoreNames = plugin.getCropCoinData().getTopPlayers();
        top10ScoreValues = plugin.getCropCoinData().getTopCoins();
    }


}
