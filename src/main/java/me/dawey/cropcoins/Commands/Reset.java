package me.dawey.cropcoins.Commands;

import me.dawey.cropcoins.CropCoins;
import me.dawey.cropcoins.Utils.ChatManager;
import me.dawey.cropcoins.Utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Reset {

    private CropCoins plugin;

    public Reset(CropCoins plugin) {
        this.plugin = plugin;
    }

    public void handle(Player sender, String targetString) {
        if (plugin.getCropCoinData().isExists(targetString)) {
            plugin.getCropCoinData().setCoins(targetString, 0);
            sender.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("reset-success")
                    .replace("{player}", targetString)
            ));
            Logger.getLogger().debug(sender.getName() + " has reset " + targetString + "'s CropCoin balance");
            if (Bukkit.getPlayer(targetString) != null && !sender.getName().equalsIgnoreCase(targetString)) {
                Player target = Bukkit.getPlayer(targetString);
                target.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("cropcoin-reseted")));
            }
        } else {
            sender.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("invalid-player")
                    .replace("{player}", targetString)
            ));
        }
    }

    public void handleConsole(String targetString) {
        if (plugin.getCropCoinData().isExists(targetString)) {
            plugin.getCropCoinData().setCoins(targetString, 0);
            Logger.getLogger().info(ChatManager.format(plugin.getLangFile().getString("reset-success")
                    .replace("{player}", targetString)
            ));
            if (Bukkit.getPlayer(targetString) != null) {
                Player target = Bukkit.getPlayer(targetString);
                target.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("cropcoin-reseted")));
            }
        } else {
            Logger.getLogger().info(ChatManager.format(plugin.getLangFile().getString("invalid-player")
                    .replace("{player}", targetString)
            ));
        }
    }
}
