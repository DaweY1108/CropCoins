package me.dawey.cropcoins.API;

import me.dawey.cropcoins.CropCoins;
import me.dawey.cropcoins.Database.CropCoinData;
import me.dawey.cropcoins.Exceptions.InvalidCropCoinValueException;
import org.checkerframework.checker.units.qual.C;

public class CropCoinsAPI {

    public static void addCoins(String playerName, long amount) throws InvalidCropCoinValueException {
        if (amount < 0) {
            throw new InvalidCropCoinValueException("Amount cannot be negative", null);
        }
        CropCoins.getInstance().getCropCoinData().addCoins(playerName, amount);
    }

    public static void takeCoins(String playerName, long amount) throws InvalidCropCoinValueException {
        if (amount < 0) {
            throw new InvalidCropCoinValueException("Amount cannot be negative", null);
        }
        CropCoins.getInstance().getCropCoinData().addCoins(playerName, -amount);
    }

    public static void setCoins(String playerName, long amount) throws InvalidCropCoinValueException {
        if (amount < 0) {
            throw new InvalidCropCoinValueException("Amount cannot be negative", null);
        }
        CropCoins.getInstance().getCropCoinData().setCoins(playerName, amount);
    }

    public static long getCoins(String playerName) {
        return CropCoins.getInstance().getCropCoinData().getCoins(playerName);
    }
}
