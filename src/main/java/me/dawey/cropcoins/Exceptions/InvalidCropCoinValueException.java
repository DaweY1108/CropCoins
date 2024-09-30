package me.dawey.cropcoins.Exceptions;

public class InvalidCropCoinValueException extends Exception {
    public InvalidCropCoinValueException(String message, Throwable cause) {
        super("Invalid CropCoin value: " + message, cause);
    }
}
