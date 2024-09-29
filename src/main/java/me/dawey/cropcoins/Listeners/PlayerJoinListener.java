package me.dawey.cropcoins.Listeners;

import me.dawey.cropcoins.CropCoins;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private CropCoins plugin;

    public PlayerJoinListener(CropCoins plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getCropCoinData().checkPlayer(player.getName());
    }
}
