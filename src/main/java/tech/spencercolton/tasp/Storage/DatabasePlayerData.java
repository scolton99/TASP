package tech.spencercolton.tasp.Storage;

import org.bukkit.Bukkit;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.TASP;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Spencer Colton
 */
class DatabasePlayerData implements PlayerDataProvider {

    private final Person p;

    DatabasePlayerData(Person p) {
        this.p = p;
    }

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
            Bukkit.getLogger().severe("Unable to fetch data for player " + p.getName() + " from the database.");
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
            Bukkit.getLogger().severe("Unable to fetch data for player " + p.getName() + " from the database.");
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
            Bukkit.getLogger().severe("Unable to fetch data for player " + p.getName() + " from the database.");
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
            Bukkit.getLogger().severe("Unable to fetch data for player " + p.getName() + " from the database.");
            return null;
        }
    }

    @Override
    public List getList(String path) {
        try {
            ResultSet rs = getConn().createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "' AND `uid`='" + p.getUid() + "'");
            if (rs.next()) {
                String s = rs.getString("value");
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)));
                return (List) ois.readObject();
            } else {
                return null;
            }
        } catch (SQLException|IOException|ClassNotFoundException e) {
            Bukkit.getLogger().severe("Unable to fetch data for player " + p.getName() + " from the database.");
            return null;
        }
    }

    @Override
    public void setString(String path, String set) {
        try {
            ResultSet rs = getConn().createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "' AND `uid`='" + p.getUid() + "'");
            if(!rs.next()) {
                getConn().createStatement().executeUpdate("INSERT INTO `users` (`key`,`value`) VALUES ('" + path + "', '" + set + "')");
            } else {
                getConn().createStatement().executeUpdate("UPDATE `users` SET `value`='" + set + "' WHERE `uid`='" + p.getUid() + "' AND `key`='" + path + "'");
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Unable to fetch data for player " + p.getName() + " from the database.");
        }
    }

    @Override
    public void setInt(String path, Integer set) {
        try {
            ResultSet rs = getConn().createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "' AND `uid`='" + p.getUid() + "'");
            if(!rs.next()) {
                getConn().createStatement().executeUpdate("INSERT INTO `users` (`key`,`value`) VALUES ('" + path + "', '" + set.toString() + "')");
            } else {
                getConn().createStatement().executeUpdate("UPDATE `users` SET `value`='" + set.toString() + "' WHERE `uid`='" + p.getUid() + "' AND `key`='" + path + "'");
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Unable to fetch data for player " + p.getName() + " from the database.");
        }
    }

    @Override
    public void setMap(String path, Map set) {
        try {
            ResultSet rs = getConn().createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "' AND `uid`='" + p.getUid() + "'");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(set);
            String fin = baos.toString();

            if(!rs.next()) {
                getConn().createStatement().executeUpdate("INSERT INTO `users` (`key`,`value`) VALUES ('" + path + "', '" + fin + "')");
            } else {
                getConn().createStatement().executeUpdate("UPDATE `users` SET `value`='" + fin + "' WHERE `uid`='" + p.getUid() + "' AND `key`='" + path + "'");
            }
        } catch (SQLException|IOException e) {
            Bukkit.getLogger().severe("Unable to fetch data for player " + p.getName() + " from the database.");
        }
    }

    @Override
    public void setList(String path, List set) {
        try {
            ResultSet rs = getConn().createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "' AND `uid`='" + p.getUid() + "'");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(set);
            String fin = baos.toString();

            if(!rs.next()) {
                getConn().createStatement().executeUpdate("INSERT INTO `users` (`key`,`value`) VALUES ('" + path + "', '" + fin + "')");
            } else {
                getConn().createStatement().executeUpdate("UPDATE `users` SET `value`='" + fin + "' WHERE `uid`='" + p.getUid() + "' AND `key`='" + path + "'");
            }
        } catch (SQLException|IOException e) {
            Bukkit.getLogger().severe("Unable to fetch data for player " + p.getName() + " from the database.");
        }
    }

    @Override
    public void setBoolean(String path, Boolean set) {
        try {
            ResultSet rs = getConn().createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "' AND `uid`='" + p.getUid() + "'");

            if(!rs.next()) {
                getConn().createStatement().executeUpdate("INSERT INTO `users` (`key`,`value`) VALUES ('" + path + "', '" + set.toString() + "')");
            } else {
                getConn().createStatement().executeUpdate("UPDATE `users` SET `value`='" + set.toString() + "' WHERE `uid`='" + p.getUid() + "' AND `key`='" + path + "'");
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Unable to fetch data for player " + p.getName() + " from the database.");
        }
    }

}
