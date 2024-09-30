package me.dawey.cropcoins.Utils;

import me.dawey.cropcoins.API.Events.GetCropCoinEvent;
import me.dawey.cropcoins.CropCoins;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CropBreakHandler {

    private CropCoins plugin;

    public CropBreakHandler(CropCoins plugin) {
        this.plugin = plugin;
    }

    private Map<String, Integer> cropBreaks = new HashMap<>();
    private Map<String, Integer> cropBreaksAmount = new HashMap<>();

    public void handleBreak(int min, int max, int chance, int amount, Player player) {
        if (!cropBreaks.containsKey(player.getName())) {
            cropBreaks.put(player.getName(), plugin.getSaveTimeout());
            cropBreaksAmount.put(player.getName(), 0);
        } else {
            cropBreaks.put(player.getName(), plugin.getSaveTimeout());
        }
        if (cropBreaksAmount.containsKey(player.getName())) {
            cropBreaksAmount.put(player.getName(), cropBreaksAmount.get(player.getName()) + Calculator.randomNumberBetween(min, max, chance) * amount);
        } else {
            cropBreaksAmount.put(player.getName(), Calculator.randomNumberBetween(min, max, chance) * amount);
        }
    }

    public void startCounter() {
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            try {
                for (String player : cropBreaks.keySet()) {
                    if (cropBreaks.get(player) > 0) {
                        cropBreaks.put(player, cropBreaks.get(player) - 1);
                    } else {
                        long balance = plugin.getCropCoinData().getCoins(player);
                        if (balance == plugin.getMaxCropCoins()) {
                            plugin.sendActionBar(Bukkit.getPlayer(player), plugin.getLangFile().getString("limit-reached"));
                            cropBreaks.remove(player);
                            cropBreaksAmount.remove(player);
                            return;
                        }
                        long amount = cropBreaksAmount.get(player);
                        long newAmount = amount + balance;
                        if (newAmount >= plugin.getMaxCropCoins()) {
                            long temp = newAmount;
                            newAmount = plugin.getMaxCropCoins();
                            plugin.getCropCoinData().setCoins(player, newAmount);
                            plugin.sendActionBar(Bukkit.getPlayer(player), plugin.getLangFile().getString("coin-get")
                                    .replace("{amount}", String.valueOf(amount - (temp - plugin.getMaxCropCoins()))));
                            Logger.getLogger().debug(player + " has reached the CropCoin limit");
                            cropBreaks.remove(player);
                            cropBreaksAmount.remove(player);
                            return;
                        }

                        GetCropCoinEvent event = new GetCropCoinEvent(Bukkit.getPlayer(player), amount);
                        Bukkit.getPluginManager().callEvent(event);
                        if (event.isCancelled()) {
                            cropBreaks.remove(player);
                            cropBreaksAmount.remove(player);
                            return;
                        }
                        plugin.getCropCoinData().addCoins(player, cropBreaksAmount.get(player));
                        plugin.sendActionBar(Bukkit.getPlayer(player), plugin.getLangFile().getString("coin-get")
                                .replace("{amount}", cropBreaksAmount.get(player).toString()));
                        Logger.getLogger().info(player + " has got " + cropBreaksAmount.get(player) + " CropCoin(s)");
                        Bukkit.getPlayer(player).playSound(Bukkit.getPlayer(player).getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        cropBreaks.remove(player);
                        cropBreaksAmount.remove(player);
                    }
                }
            } catch (Exception e) {}
        }, 0, 20);
    }
}
