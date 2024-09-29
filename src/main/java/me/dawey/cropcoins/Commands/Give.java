package me.dawey.cropcoins.Commands;

import me.dawey.cropcoins.CropCoins;
import me.dawey.cropcoins.Utils.Calculator;
import me.dawey.cropcoins.Utils.ChatManager;
import me.dawey.cropcoins.Utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Give {

    private CropCoins plugin;

    public Give(CropCoins plugin) {
        this.plugin = plugin;
    }

    public void handle(Player sender, String targetString, String amountString) {
        if (Calculator.isNumber(amountString)) {
            long amount = Long.parseLong(amountString);
            if (amount > 0) {
                if (plugin.getCropCoinData().isExists(targetString)) {
                    long toCoins = plugin.getCropCoinData().getCoins(targetString);
                    long newbalance = toCoins + amount;
                    if (newbalance > plugin.getMaxCropCoins()) {
                        amount = plugin.getMaxCropCoins() - toCoins;
                        newbalance = plugin.getMaxCropCoins();
                    }
                    sender.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("give-success")
                            .replace("{player}", targetString)
                            .replace("{amount}", String.valueOf(amount))
                            .replace("{newbalance}", String.valueOf(newbalance))
                    ));
                    Logger.getLogger().debug(sender.getName() + " gave some CropCoins to: " + targetString + " (" + amount + ")");
                    if (plugin.getServer().getPlayer(targetString) != null && !sender.getName().equalsIgnoreCase(targetString)) {
                        Player target = plugin.getServer().getPlayer(targetString);
                        target.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("cropcoin-given")
                                .replace("{player}", target.getName())
                                .replace("{amount}", String.valueOf(amount))
                        ));
                    }
                    plugin.getCropCoinData().setCoins(targetString, newbalance);
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
            if (amount > 0) {
                if (plugin.getCropCoinData().isExists(targetString)) {
                    long toCoins = plugin.getCropCoinData().getCoins(targetString);
                    long newbalance = toCoins + amount;
                    if (newbalance > plugin.getMaxCropCoins()) {
                        amount = plugin.getMaxCropCoins() - toCoins;
                        newbalance = plugin.getMaxCropCoins();
                    }
                    Logger.getLogger().info(ChatManager.format(plugin.getLangFile().getString("give-success")
                            .replace("{player}", targetString)
                            .replace("{amount}", String.valueOf(amount))
                            .replace("{newbalance}", String.valueOf(newbalance))
                    ));
                    if (plugin.getServer().getPlayer(targetString) != null) {
                        Player target = Bukkit.getPlayer(targetString);
                        target.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("cropcoin-given")
                                .replace("{player}", target.getName())
                                .replace("{amount}", String.valueOf(amount))
                                .replace("{newbalance}", String.valueOf(newbalance))
                        ));
                    }
                    plugin.getCropCoinData().setCoins(targetString, newbalance);
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
