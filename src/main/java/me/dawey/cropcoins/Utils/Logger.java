package me.dawey.cropcoins.Utils;

import org.bukkit.Bukkit;

public class Logger {
    private static final String prefix = "[CropCoins] ";
    private static final Log logger = new Log();

    public static class Log {
        public void info(String message) {
            Bukkit.getLogger().info(prefix + message);
        }

        public void warn(String message) {
            Bukkit.getLogger().warning(prefix + message);
        }

        public void system(String message) {
            Bukkit.getLogger().info(message);
        }

        public void debug(String message) {
            Bukkit.getLogger().info(prefix + "[DEBUG] " + message);
        }
    }

    public static Log getLogger() {
        return logger;
    }

    public static void logo() {
        logger.system("");
        logger.system("       .                                                  ");
        logger.system("     .\\^/.                                               ");
        logger.system("   . |`|/| .                                              ");
        logger.system("   |\\|\\|'|/|                                            ");
        logger.system(".--'-\\`|/-''--.   CROP COINS | BY DAWEY                  ");
        logger.system(" \\`-._\\|./.-'/               |                          ");
        logger.system("  >`-._|/.-'<     LOADING... | VERSION 1.0              ");
        logger.system(" '~|/~~|~~\\|~'                                           ");
        logger.system("       |             \\$/           \\$/                  ");
        logger.system("       |              |             |                     ");
        logger.system("___/-------\\______/-------\\_____/-------\\____          ");
        logger.system("");
    }
}
