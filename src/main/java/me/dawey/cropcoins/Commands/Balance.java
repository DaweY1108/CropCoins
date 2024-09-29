package me.dawey.cropcoins.Commands;

import me.dawey.cropcoins.CropCoins;
import me.dawey.cropcoins.Utils.ChatManager;
import me.dawey.cropcoins.Utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Balance {

    private CropCoins plugin;

    public Balance(CropCoins plugin) {
        this.plugin = plugin;
    }

    public void handle(Player player) {
        long balance = plugin.getCropCoinData().getCoins(player.getName());
        player.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("balance-self")
                .replace("{amount}", String.valueOf(balance))
        ));
        Logger.getLogger().debug(player.getName() + " checked his/her CropCoin balance");
    }

    public void handle(Player sender, String targetString) {
        if (plugin.getCropCoinData().isExists(targetString)) {
            long balance = plugin.getCropCoinData().getCoins(targetString);
            sender.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("balance-other")
                    .replace("{player}", targetString)
                    .replace("{amount}", String.valueOf(balance))
            ));
            Logger.getLogger().info(sender.getName() + "megtekintette a cropcoin egyenlegét a következő játékosnak: " + targetString + "!");
        } else {
            sender.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("invalid-player")
                    .replace("{player}", targetString)
            ));
        }
    }
}

