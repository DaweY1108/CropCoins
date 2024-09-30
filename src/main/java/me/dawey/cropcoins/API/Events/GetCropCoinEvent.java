package me.dawey.cropcoins.API.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.logging.Handler;

public class GetCropCoinEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private long amount;
    private boolean isCancelled;

    public GetCropCoinEvent(Player player, long amount) {
        this.player = player;
        this.amount = amount;
        this.isCancelled = false;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public boolean isCancelled() {
        return isCancelled;
    }
}
