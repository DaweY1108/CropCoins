package me.dawey.cropcoins.Listeners;

import me.dawey.cropcoins.CropCoins;
import me.dawey.cropcoins.Utils.CropCoin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class CropListener implements Listener {
    private CropCoins plugin;

    public CropListener(CropCoins plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCustomCropBreak(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            if (plugin.getConfig().getBoolean("debug")) Bukkit.getLogger().info("CCDEBUG: BlockBreakEvent was passed! Player: " + event.getPlayer().getName());
            String type = event.getBlock().getLocation().getWorld().getName() + ":minecraft:" + event.getBlock().getType().name().toLowerCase();
            if (plugin.getCropCoinValues().containsKey(type)) {
                if (isRipe(event.getBlock()) || !plugin.getConfig().getBoolean("only-ripe-crops")) {
                    int amount = blockCount(event.getBlock());
                    CropCoin cropCoin = plugin.getCropCoinValues().get(type);
                    plugin.getCropBreakHandler().handleBreak(cropCoin.getMin(), cropCoin.getMax(), cropCoin.getChance(), amount, event.getPlayer());
                }
            }
        } else if (plugin.getConfig().getBoolean("debug")) {
            Bukkit.getLogger().info("CCDEBUG: BlockBreakEvent was cancelled! Player: " + event.getPlayer().getName());
        }
    }

    public boolean isRipe(Block block) {
        String type = block.getType().name().toLowerCase();
        if (
                type.equalsIgnoreCase("potatoes")
                || type.equalsIgnoreCase("carrots")
                || type.equalsIgnoreCase("wheat")
                || type.equalsIgnoreCase("beetroots")
                || type.equalsIgnoreCase("nether_wart")
                || type.equalsIgnoreCase("cocoa")
                || type.equalsIgnoreCase("sugar_cane")
                || type.equalsIgnoreCase("melon")
                || type.equalsIgnoreCase("pumpkin")
                || type.equalsIgnoreCase("cactus")

        ) {
            if (block.getBlockData() instanceof Ageable && !(block.getType().name().equalsIgnoreCase("sugar_cane") || block.getType().name().equalsIgnoreCase("cactus"))) {
                Ageable ageable = (Ageable) block.getBlockData();
                return ageable.getAge() == ageable.getMaximumAge();
            } else {
                return true;
            }
        }
        return false;
    }

    private int blockCount(Block block) {
        if (block.getType().name().equalsIgnoreCase("sugar_cane") || block.getType().name().equalsIgnoreCase("cactus")) {
            int count = 1;
            Location location = block.getLocation().add(0, 1, 0);
            while (location.getBlock().getType() == block.getType()) {
                count++;
                location = location.add(0, 1, 0);
            }
            return count;
        } else {
            return 1;
        }
    }
}
