package me.dawey.cropcoins.Commands;

import me.dawey.cropcoins.CropCoins;
import me.dawey.cropcoins.Utils.ChatManager;
import org.bukkit.entity.Player;

public class Help {

    private CropCoins plugin;

    public Help(CropCoins plugin) {
        this.plugin = plugin;
    }


    public void handle(Player player) {
        player.sendMessage(ChatManager.format(plugin.getLangFile().getString("help-header")));
        if (player.hasPermission("cropcoins.give")) {
            for (String s : plugin.getLangFile().getStringList("help-give")) {
                player.sendMessage(ChatManager.format(s));
            }
        }
        if (player.hasPermission("cropcoins.take")) {
            for (String s : plugin.getLangFile().getStringList("help-take")) {
                player.sendMessage(ChatManager.format(s));
            }
        }
        if (player.hasPermission("cropcoins.set")) {
            for (String s : plugin.getLangFile().getStringList("help-set")) {
                player.sendMessage(ChatManager.format(s));
            }
        }
        if (player.hasPermission("cropcoins.reset")) {
            for (String s : plugin.getLangFile().getStringList("help-reset")) {
                player.sendMessage(ChatManager.format(s));
            }
        }
        if (player.hasPermission("cropcoins.balance")) {
            for (String s : plugin.getLangFile().getStringList("help-balance")) {
                player.sendMessage(ChatManager.format(s));
            }
        }
        if (player.hasPermission("cropcoins.balance.others")) {
            for (String s : plugin.getLangFile().getStringList("help-balance-others")) {
                player.sendMessage(ChatManager.format(s));
            }
        }
        if (player.hasPermission("cropcoins.top")) {
            for (String s : plugin.getLangFile().getStringList("help-top")) {
                player.sendMessage(ChatManager.format(s));
            }
        }
        if (player.hasPermission("cropcoins.reload")) {
            for (String s : plugin.getLangFile().getStringList("help-reload")) {
                player.sendMessage(ChatManager.format(s));
            }
        }
        if (player.hasPermission("cropcoins.send")) {
            for (String s : plugin.getLangFile().getStringList("help-send")) {
                player.sendMessage(ChatManager.format(s));
            }
        }
        player.sendMessage(ChatManager.format(plugin.getLangFile().getString("help-footer")));
    }
}
