package me.dawey.cropcoins.Commands;

import me.dawey.cropcoins.CropCoins;
import me.dawey.cropcoins.Utils.Calculator;
import me.dawey.cropcoins.Utils.ChatManager;
import org.bukkit.entity.Player;

import java.util.List;

public class Top {

    private CropCoins plugin;

    public Top(CropCoins plugin) {
        this.plugin = plugin;
    }

    public void handle(Player player, String pageString) {
        int page = 1;
        if (Calculator.isNumberInteger(pageString)) {
            page = Integer.parseInt(pageString);
        }
        int maxPage = (plugin.getCropCoinData().getTopPlayers().size() / plugin.getTopAmount());
        if ((plugin.getCropCoinData().getTopPlayers().size() % plugin.getTopAmount()) != 0) {
            maxPage++;
        }
        if (page > maxPage) {
            page = maxPage;
        }
        if (page < 1) {
            page = 1;
        }
        player.sendMessage(ChatManager.format(plugin.getLangFile().getString("top-header")
                .replace("{current}", String.valueOf(page))
                .replace("{max}", String.valueOf(maxPage))
        ));
        showTopList(player, page);
        player.sendMessage(ChatManager.format(plugin.getLangFile().getString("top-footer")));
    }


    private void showTopList(Player player, int currentPage) {
        int start = (currentPage - 1) * plugin.getTopAmount();
        int end = start + plugin.getTopAmount();
        List<String> topPlayers = plugin.getCropCoinData().getTopPlayers();
        List<Long> topCoins = plugin.getCropCoinData().getTopCoins();
        for (int i = start; i < end; i++) {
            try {
                Long coins = topCoins.get(i);
                String name = topPlayers.get(i);
                player.sendMessage(ChatManager.format(plugin.getLangFile().getString("top-format")
                        .replace("{position}", String.valueOf(i + 1))
                        .replace("{player}", name)
                        .replace("{amount}", String.valueOf(coins))
                ));
            } catch (Exception e) {
                player.sendMessage(ChatManager.format(plugin.getLangFile().getString("top-format")
                        .replace("{position}", String.valueOf(i + 1))
                        .replace("{player}", "N/A")
                        .replace("{amount}", "N/A")
                ));
            }
        }
    }
}
