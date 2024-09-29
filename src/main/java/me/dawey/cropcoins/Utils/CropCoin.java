package me.dawey.cropcoins.Utils;

public class CropCoin {
    private int min;
    private int max;
    private int chance;

    public CropCoin(int min, int max, int chance) {
        this.min = min;
        this.max = max;
        this.chance = chance;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getChance() {
        return chance;
    }

}
