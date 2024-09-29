package me.dawey.cropcoins.Commands;

import me.dawey.cropcoins.CropCoins;
import me.dawey.cropcoins.Utils.Calculator;
import me.dawey.cropcoins.Utils.ChatManager;
import me.dawey.cropcoins.Utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Take {

    private CropCoins plugin;

    public Take(CropCoins plugin) {
        this.plugin = plugin;
    }

    public void handle(Player sender, String targetString, String amountString) {
        if (Calculator.isNumber(amountString)) {
            long amount = Long.parseLong(amountString);
            if (amount > 0) {
                if (plugin.getCropCoinData().isExists(targetString)) {
                    long balance = plugin.getCropCoinData().getCoins(targetString);
                    long newBalance = balance - amount;
                    if (newBalance < 0) {
                        newBalance = 0;
                        amount = balance;
                    }
                    plugin.getCropCoinData().setCoins(targetString, newBalance);
                    sender.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("take-success")
                            .replace("{player}", targetString)
                            .replace("{amount}", String.valueOf(amount))
                            .replace("{newbalance}", String.valueOf(newBalance))
                    ));
                    Logger.getLogger().debug(sender.getName() + " has took CropCoins from: " + targetString + "! (" + amount + ")");
                    if (plugin.getServer().getPlayer(targetString) != null && !sender.getName().equalsIgnoreCase(targetString)) {
                        Player target = plugin.getServer().getPlayer(targetString);
                        target.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("cropcoin-taken")
                                .replace("{amount}", String.valueOf(amount))
                                .replace("{newbalance}", String.valueOf(newBalance))
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
            if (amount > 0) {
                if (plugin.getCropCoinData().isExists(targetString)) {
                    long balance = plugin.getCropCoinData().getCoins(targetString);
                    long newBalance = balance - amount;
                    if (newBalance < 0) {
                        newBalance = 0;
                        amount = balance;
                    }
                    plugin.getCropCoinData().setCoins(targetString, newBalance);
                    Logger.getLogger().info(ChatManager.format(plugin.getLangFile().getString("take-success")
                            .replace("{player}", targetString)
                            .replace("{amount}", String.valueOf(amount))
                            .replace("{newbalance}", String.valueOf(newBalance))
                    ));
                    if (plugin.getServer().getPlayer(targetString) != null) {
                        Player target = plugin.getServer().getPlayer(targetString);
                        target.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("cropcoin-taken")
                                .replace("{amount}", String.valueOf(amount))
                                .replace("{newbalance}", String.valueOf(newBalance))
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
