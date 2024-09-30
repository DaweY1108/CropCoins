package me.dawey.cropcoins.API;

import me.dawey.cropcoins.CropCoins;
import me.dawey.cropcoins.Database.CropCoinData;
import me.dawey.cropcoins.Exceptions.InvalidCropCoinValueException;
import org.checkerframework.checker.units.qual.C;

public class CropCoinsAPI {

    private CropCoins plugin;

    public CropCoinsAPI(CropCoins plugin) {
        this.plugin = plugin;
    }

    public void addCoins(String playerName, long amount) throws InvalidCropCoinValueException {
        if (amount < 0) {
            throw new InvalidCropCoinValueException("Amount cannot be negative", null);
        }
        plugin.getCropCoinData().addCoins(playerName, amount);
    }

    public void takeCoins(String playerName, long amount) throws InvalidCropCoinValueException {
        if (amount < 0) {
            throw new InvalidCropCoinValueException("Amount cannot be negative", null);
        }
        plugin.getCropCoinData().addCoins(playerName, -amount);
    }

    public void setCoins(String playerName, long amount) throws InvalidCropCoinValueException {
        if (amount < 0) {
            throw new InvalidCropCoinValueException("Amount cannot be negative", null);
        }
        plugin.getCropCoinData().setCoins(playerName, amount);
    }

    public int getCoins(String playerName) {
        // Get player's coins
        return 0;
    }
}
