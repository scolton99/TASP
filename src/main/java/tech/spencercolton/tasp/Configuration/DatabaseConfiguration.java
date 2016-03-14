package tech.spencercolton.tasp.Configuration;

import org.bukkit.Bukkit;
import tech.spencercolton.tasp.TASP;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Spencer Colton
 */
class DatabaseConfiguration implements Configuration {

    DatabaseConfiguration() {}

    private Connection getConn() {
        return TASP.getConfigDb();
    }

    @Override
    public Integer getInt(String path) {
        try {
            ResultSet rs = getConn().createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "'");
            if (rs.next()) {
                return rs.getInt("value");
            } else {
                return null;
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Unable to fetch configuration from the database.");
            return null;
        }
    }

    @Override
    public String getString(String path) {
        try {
            ResultSet rs = getConn().createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "'");
            if (rs.next()) {
                return rs.getString("value");
            } else {
                return null;
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Unable to fetch configuration from the database.");
            return null;
        }
    }

    @Override
    public Boolean getBoolean(String path) {
        try {
            ResultSet rs = getConn().createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "'");
            if (rs.next()) {
                return rs.getBoolean("value");
            } else {
                return null;
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Unable to fetch configuration from the database.");
            return null;
        }
    }

    @Override
    public List getList(String path) {
        try {
            ResultSet rs = getConn().createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "'");
            if (rs.next()) {
                String s = rs.getString("value");
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)));
                return (List) ois.readObject();
            } else {
                return null;
            }
        } catch (SQLException|IOException|ClassNotFoundException e) {
            Bukkit.getLogger().severe("Unable to configuration from the database.");
            return null;
        }
    }

    @Override
    public Map getMap(String path) {
        try {
            ResultSet rs = getConn().createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "'");
            if (rs.next()) {
                String s = rs.getString("value");
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)));
                return (Map) ois.readObject();
            } else {
                return null;
            }
        } catch (SQLException|IOException|ClassNotFoundException e) {
            Bukkit.getLogger().severe("Unable to fetch configuration from the database.");
            return null;
        }
    }
}
