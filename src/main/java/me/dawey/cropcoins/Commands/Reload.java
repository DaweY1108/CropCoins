package me.dawey.cropcoins.Commands;

import me.dawey.cropcoins.CropCoins;
import me.dawey.cropcoins.Utils.ChatManager;
import org.bukkit.entity.Player;

public class Reload {

    private CropCoins plugin;

    public Reload(CropCoins plugin) {
        this.plugin = plugin;
    }


    public void handle(Player player) {
        plugin.reload();
        player.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("reload-success")));
    }
}
