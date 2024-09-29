package me.dawey.cropcoins.Utils;

import java.util.Random;

public class Calculator {
    public static int randomNumberBetween(int minAmount, int maxAmount, double chance) {

        if (maxAmount == 0) {
            return 0;
        }

        Random r = new Random();
        double probability = r.nextDouble() * 100;

        if (probability < chance) {

            double amountRandom = Math.random() * (1 - 0.5) + 0.5;
            double amount = (amountRandom * (maxAmount - minAmount) + minAmount);
            int roundedAmount = (int) Math.round(amount);

            return roundedAmount;

        } else {

            double amountRandom = Math.random() * (0.5 - 0) + 0;
            double amount = (int)(amountRandom * (maxAmount - minAmount) + minAmount);
            int roundedAmount = (int) Math.round(amount);

            return roundedAmount;

        }
    }

    public static boolean isNumber(String string) {
        try {
            Long.parseLong(string);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isNumberInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
