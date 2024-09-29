package me.dawey.cropcoins.Database;

import me.dawey.cropcoins.CropCoins;
import me.dawey.cropcoins.Utils.Logger;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private CropCoins plugin;
    private boolean enableMySQL = false;
    private boolean UseSSL = false;
    private String host = null;
    private String port = null;
    private String database = null;
    private String username = null;
    private String password = null;
    private Connection connection;

    public Database(CropCoins plugin) {

        this.plugin = plugin;

        if (plugin.getConfig().getString("database.type").equalsIgnoreCase("mysql")) {
            this.enableMySQL = true;
            this.host = plugin.getConfig().getString("database.mysql.host");
            this.port = plugin.getConfig().getString("database.mysql.port");
            this.database = plugin.getConfig().getString("database.mysql.database");
            this.username = plugin.getConfig().getString("database.mysql.username");
            this.password = plugin.getConfig().getString("database.mysql.password");
            this.UseSSL = plugin.getConfig().getBoolean("database.mysql.useSSL");
        } else {
            this.enableMySQL = false;
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.connection != null) return connection;
        if (enableMySQL) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=" + UseSSL + "&allowMultiQueries=true", username, password);
            } catch (ClassNotFoundException | SQLException e) {
                Logger.getLogger().warn("Could not connect to MySQL database!");
                Logger.getLogger().warn("Error: " + e.getMessage());
            }
        } else {
            try {
                Class.forName("org.sqlite.JDBC");
                this.connection = DriverManager.getConnection("jdbc:sqlite:plugins/" + plugin.getName() + "/database.db");
            } catch (ClassNotFoundException | SQLException e) {
                Logger.getLogger().warn("Could not connect to SQLite database!");
                Logger.getLogger().warn("Error: " + e.getMessage());
            }
        }
        return this.connection;
    }

    public void init() {
        String command;
        if (isEnableMySQL()) {
            command = "CREATE TABLE IF NOT EXISTS cropcoins (id int NOT NULL AUTO_INCREMENT, name TEXT, amount BIGINT DEFAULT 0, PRIMARY KEY (id));";
        } else {
            command = "CREATE TABLE IF NOT EXISTS cropcoins (name TEXT, amount BIGINT DEFAULT 0);";
        }
        executeCommand(command);
    }

    public void executeCommand(String cmd) {
        try {
            Connection c = getConnection();
            Statement statement = c.createStatement();
            statement.execute(cmd);
        } catch (SQLException e) {
            Logger.getLogger().warn("Couldn't execute SQL command: " + cmd);
            Logger.getLogger().warn("Error: " + e.getMessage());
        }
    }

    public ResultSet getResult(String command) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(command);
            return statement.executeQuery();
        } catch (SQLException e) {
            Logger.getLogger().warn("Could not execute command: " + command);
            Logger.getLogger().warn("Error: " + e.getMessage());
            return null;
        }
    }

    public boolean isEnableMySQL() {
        return enableMySQL;
    }
}

