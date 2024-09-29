package me.dawey.cropcoins.Commands;

import me.dawey.cropcoins.CropCoins;
import me.dawey.cropcoins.Utils.Calculator;
import me.dawey.cropcoins.Utils.ChatManager;
import me.dawey.cropcoins.Utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Send {

    private CropCoins plugin;

    public Send(CropCoins plugin) {
        this.plugin = plugin;
    }

    public void handle(Player sender, String targetString, String amountString) {
        long fromCoins = plugin.getCropCoinData().getCoins(sender.getName());
        if (Calculator.isNumber(amountString)) {
            long amount = Long.parseLong(amountString);
            if (amount > 0) {
                if (fromCoins >= amount) {
                    if (plugin.getServer().getPlayer(targetString) != null) {
                        Player target = plugin.getServer().getPlayer(targetString);
                        if (sender.getName().equalsIgnoreCase(target.getName())) {
                            sender.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("cant-send-to-self")));
                            return;
                        }
                        if (plugin.getCropCoinData().isExists(target.getName())) {
                            long toCoins = plugin.getCropCoinData().getCoins(target.getName());
                            if (target.isOnline()) {
                                if (toCoins + amount <= plugin.getMaxCropCoins()) {
                                    sender.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("send-success")
                                            .replace("{player}", target.getName())
                                            .replace("{amount}", String.valueOf(amount))
                                    ));
                                    target.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("cropcoin-got-from")
                                            .replace("{player}", sender.getName())
                                            .replace("{amount}", String.valueOf(amount))
                                    ));
                                    plugin.getCropCoinData().addCoins(target.getName(), amount);
                                    plugin.getCropCoinData().addCoins(sender.getName(), -amount);
                                    Logger.getLogger().debug(sender.getName() + " has sent CropCoins to: " + target.getName() + "! (" + amount + ")");
                                } else {
                                    sender.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("limit-reached-other")
                                            .replace("{player}", target.getName())
                                            .replace("{amount}", String.valueOf(plugin.getMaxCropCoins()))
                                    ));
                                }
                            } else {
                                sender.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("player-offline")
                                        .replace("{player}", target.getName())
                                ));
                            }
                        } else {
                            sender.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("invalid-player")
                                    .replace("{player}", target.getName())
                            ));
                        }
                    } else {
                        sender.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("invalid-player")
                                .replace("{player}", targetString)
                        ));
                    }
                } else {
                    sender.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("not-enough-coins")
                            .replace("{amount}", String.valueOf(amount))
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
}
