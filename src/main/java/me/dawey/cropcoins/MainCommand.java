package me.dawey.cropcoins;

import me.dawey.cropcoins.Utils.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MainCommand implements CommandExecutor, TabCompleter {
    private CropCoins plugin;
    private List<String> args1 = Arrays.asList("give","take", "set", "send", "reset", "balance", "top", "help", "reload");
    private List<String> numbers = Arrays.asList("1", "10", "100", "1000", "10000", "100000", "1000000", "10000000", "100000000", "1000000000");

    public MainCommand(CropCoins plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                plugin.help.handle(player);
                return false;
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("help")) {
                    plugin.help.handle(player);
                    return false;
                }

                if (args[0].equalsIgnoreCase("reload")) {
                    if (player.hasPermission("cropcoins.reload")) {
                        plugin.reload.handle(player);
                        return false;
                    } else {
                        player.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("no-permission")));
                        return false;
                    }
                }

                if (args[0].equalsIgnoreCase("balance")) {
                    if (player.hasPermission("cropcoins.balance")) {
                        plugin.balance.handle(player);
                        return false;
                    } else {
                        player.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("no-permission")));
                        return false;
                    }
                }

                if (args[0].equalsIgnoreCase("top")) {
                    if (player.hasPermission("cropcoins.top")) {
                        plugin.top.handle(player, "1");
                        return false;
                    } else {
                        player.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("no-permission")));
                        return false;
                    }
                }
            }

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("balance")) {
                    if (player.hasPermission("cropcoins.balance.others")) {
                        plugin.balance.handle(player, args[1]);
                        return false;
                    } else {
                        player.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("no-permission")));
                        return false;
                    }
                }

                if (args[0].equalsIgnoreCase("reset")) {
                    if (player.hasPermission("cropcoins.reset")) {
                        plugin.reset.handle(player, args[1]);
                        return false;
                    } else {
                        player.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("no-permission")));
                        return false;
                    }
                }

                if (args[0].equalsIgnoreCase("top")) {
                    if (player.hasPermission("cropcoins.top")) {
                        plugin.top.handle(player, args[1]);
                        return false;
                    } else {
                        player.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("no-permission")));
                        return false;
                    }
                }
            }

            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("give")) {
                    if (player.hasPermission("cropcoins.give")) {
                        plugin.give.handle(player, args[1], args[2]);
                        return false;
                    } else {
                        player.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("no-permission")));
                        return false;
                    }
                }

                if (args[0].equalsIgnoreCase("take")) {
                    if (player.hasPermission("cropcoins.take")) {
                        plugin.take.handle(player, args[1], args[2]);
                        return false;
                    } else {
                        player.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("no-permission")));
                        return false;
                    }
                }

                if (args[0].equalsIgnoreCase("set")) {
                    if (player.hasPermission("cropcoins.set")) {
                        plugin.set.handle(player, args[1], args[2]);
                        return false;
                    } else {
                        player.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("no-permission")));
                        return false;
                    }
                }

                if (args[0].equalsIgnoreCase("send")) {
                    if (player.hasPermission("cropcoins.send")) {
                        plugin.send.handle(player, args[1], args[2]);
                        return false;
                    } else {
                        player.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("no-permission")));
                        return false;
                    }
                }
            }

            if (args.length >= 1) {
                List<String> helpList = plugin.getLangFile().getStringList("help-" + args[0].toLowerCase());
                if (helpList.size() == 0) {
                    player.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("invalid-arguments")));
                    return false;
                }
                if (player.hasPermission("cropcoins." + args[0].toLowerCase())) {
                    for (String s : helpList) {
                        player.sendMessage(ChatManager.format(s));
                    }
                    return false;
                } else {
                    player.sendMessage(ChatManager.format(plugin.getLangFile().getString("prefix") + plugin.getLangFile().getString("no-permission")));
                    return false;
                }
            }
            plugin.help.handle(player);
        } else {
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("reset")) {
                    plugin.reset.handleConsole(args[1]);
                    return false;
                }
            }

            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("give")) {
                    plugin.give.handleConsole(args[1], args[2]);
                    return false;
                }

                if (args[0].equalsIgnoreCase("take")) {
                    plugin.take.handleConsole(args[1], args[2]);
                    return false;
                }

                if (args[0].equalsIgnoreCase("set")) {
                    plugin.set.handleConsole(args[1], args[2]);
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        if (args.length == 1) {
            List<String> newList = new ArrayList<>();
            for (String s : args1) {
                if (p.hasPermission("cropcoins." + s)) {
                    newList.add(s);
                }
            }
            Collections.sort(newList);
            List<String> tab = new ArrayList<>();
            for (String s : newList) {
                if (s.toLowerCase().startsWith(args[0].toLowerCase())) {
                    tab.add(s);
                }
            }
            return tab;
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("top")) {
                List<String> newList = new ArrayList<>();
                int maxPage = (plugin.getCropCoinData().getTopPlayers().size() / plugin.getTopAmount());
                if ((plugin.getCropCoinData().getTopPlayers().size() % plugin.getTopAmount()) != 0) {
                    maxPage++;
                }
                for (int i = 1; i <= maxPage; i++) {
                    if (p.hasPermission("cropcoins.top")) {
                        newList.add(String.valueOf(i));
                    }
                }
                Collections.sort(newList);
                List<String> tab = new ArrayList<>();
                for (String s : newList) {
                    if (s.toLowerCase().startsWith(args[1].toLowerCase())) {
                        tab.add(s);
                    }
                }
                return tab;
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("take") || args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("send")) {
                List<String> newList = new ArrayList<>();
                for (String s : this.numbers) {
                    if (p.hasPermission("cropcoins." + args[0].toLowerCase())) {
                        newList.add(String.valueOf(s));
                    }
                }
                Collections.sort(newList);
                List<String> tab = new ArrayList<>();
                for (String s : newList) {
                    if (s.toLowerCase().startsWith(args[2].toLowerCase())) {
                        tab.add(s);
                    }
                }
                return tab;
            }
        }


        return null;
    }
}
