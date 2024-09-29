package me.dawey.cropcoins.Commands;

import me.dawey.cropcoins.CropCoins;
import me.dawey.cropcoins.Utils.Calculator;
import me.dawey.cropcoins.Utils.ChatManager;
import me.dawey.cropcoins.Utils.Logger;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Set {

    private CropCoins plugin;

    public Set(CropCoins plugin) {
        this.plugin = plugin;
    }

    public void handle(Player sender, String targetString, String amountString) {
        if (Calculator.isNumber(amountString)) {
            long amount = Long.parseLong(amountString);
            if (amount >= 0) {
                if (amount > plugin.getMaxCropCoins()) amount = plugin.getMaxCropCoins();
                if (plugin.getCropCoinData().isExists(targetString)) {
                    plugin.getCropCoinData().setCoins(targetString, amount);
                    sender.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("set-success")
                            .replace("{player}", targetString)
                            .replace("{amount}", String.valueOf(amount))
                    ));
                    Logger.getLogger().debug(sender.getName() + " has set " + targetString + "'s CropCoin balance (" + amount + ")");
                    if (plugin.getServer().getPlayer(targetString) != null && !sender.getName().equalsIgnoreCase(targetString)) {
                        Player target = plugin.getServer().getPlayer(targetString);
                        target.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("cropcoin-set")
                                .replace("{amount}", String.valueOf(amount))
                        ));
                    }
                } else {
                    sender.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("invalid-player")
                            .replace("{player}", targetString)
                    ));
                }
            } else {
                sender.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("invalid-amount")
                        .replace("{amount}", String.valueOf(amount))
                ));
            }
        } else {
            sender.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("invalid-amount")
                    .replace("{amount}", amountString)
            ));
        }
    }

    public void handleConsole(String targetString, String amountString) {
        if (Calculator.isNumber(amountString)) {
            long amount = Long.parseLong(amountString);
            if (amount >= 0) {
                if (amount > plugin.getMaxCropCoins()) amount = plugin.getMaxCropCoins();
                if (plugin.getCropCoinData().isExists(targetString)) {
                    plugin.getCropCoinData().setCoins(targetString, amount);
                    Logger.getLogger().info(ChatManager.format(plugin.getLangFile().getString("set-success")
                            .replace("{player}", targetString)
                            .replace("{amount}", String.valueOf(amount))
                    ));
                    if (plugin.getServer().getPlayer(targetString) != null) {
                        Player target = plugin.getServer().getPlayer(targetString);
                        target.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("cropcoin-set")
                                .replace("{amount}", String.valueOf(amount))
                        ));
                    }
                } else {
                    Logger.getLogger().info(ChatManager.format(plugin.getLangFile().getString("invalid-player")
                            .replace("{player}", targetString)
                    ));
                }
            } else {
                Logger.getLogger().info(ChatManager.format(plugin.getLangFile().getString("invalid-amount")
                        .replace("{amount}", String.valueOf(amount))
                ));
            }
        } else {
            Logger.getLogger().info(ChatManager.format(plugin.getLangFile().getString("invalid-amount")
                    .replace("{amount}", amountString)
            ));
        }
    }
}
