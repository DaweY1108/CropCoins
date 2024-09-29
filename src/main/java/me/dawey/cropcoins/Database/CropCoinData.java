package me.dawey.cropcoins.Database;

import me.dawey.cropcoins.CropCoins;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CropCoinData{

    private CropCoins plugin;

    public CropCoinData(CropCoins plugin) {
        this.plugin = plugin;
    }

    public boolean isExists(String playerName) {
        ResultSet rs = plugin.getDatabase().getResult("SELECT * FROM cropcoins WHERE name = '" + playerName + "'");
        try {
            int count = 0;
            while (rs.next()) {
                count++;
            }
            return count > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void checkPlayer(String playerName) {
        if (!isExists(playerName)) {
            plugin.getDatabase().executeCommand("INSERT INTO cropcoins (name) VALUES ('" + playerName + "')");
        }
    }

    public void addCoins(String playerName, int amount) {
        checkPlayer(playerName);
        plugin.getDatabase().executeCommand("UPDATE cropcoins SET amount = amount + " + amount + " WHERE name = '" + playerName + "' and amount + " + amount + " >= 0 and amount + " + amount + " <= " + plugin.getMaxCropCoins());
    }
    public void addCoins(String playerName, long amount) {
        checkPlayer(playerName);
        plugin.getDatabase().executeCommand("UPDATE cropcoins SET amount = amount + " + amount + " WHERE name = '" + playerName + "' and amount + " + amount + " >= 0 and amount + " + amount + " <= " + plugin.getMaxCropCoins());
    }

    public void setCoins(String playerName, int amount) {
        checkPlayer(playerName);
        plugin.getDatabase().executeCommand("UPDATE cropcoins SET amount = " + amount + " WHERE name = '" + playerName + "'");
    }

    public void setCoins(String playerName, long amount) {
        checkPlayer(playerName);
        plugin.getDatabase().executeCommand("UPDATE cropcoins SET amount = " + amount + " WHERE name = '" + playerName + "'");
    }

    public long getCoins(String playerName) {
        ResultSet rs = plugin.getDatabase().getResult("SELECT amount FROM cropcoins WHERE name = '" + playerName + "'");
        long amount = 0;
        try {
            while (rs.next()) {
                amount = rs.getLong("amount");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
        return amount;
    }

    public List<String> getTopPlayers() {
        ResultSet rs = plugin.getDatabase().getResult("SELECT name FROM cropcoins ORDER BY amount DESC");
        List<String> top100 = new ArrayList<>();
        try {
            while (rs.next()) {
                top100.add(rs.getString("name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return top100;
    }

    public List<Long> getTopCoins() {
        ResultSet rs = plugin.getDatabase().getResult("SELECT amount FROM cropcoins ORDER BY amount DESC");
        List<Long> top100 = new ArrayList<>();
        try {
            while (rs.next()) {
                top100.add(rs.getLong("amount"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return top100;
    }

}