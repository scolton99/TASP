package tech.spencercolton.tasp.Storage;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.TASP;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Spencer Colton
 */
public class DatabasePlayerData implements PlayerDataProvider {

    private final UUID u;

    @Getter
    private boolean valid;

    public DatabasePlayerData(UUID u) {
        try (Connection c = getConn()) {
            ResultSet rs = c.createStatement().executeQuery("SELECT * FROM `users` WHERE `uid`='" + u.toString() + "'");

            if (!rs.next()) {
                genData(u);
            }
            this.valid = true;
        } catch (SQLException e) {
            this.valid = false;
        }

        this.u = u;
    }

    public DatabasePlayerData(UUID u, boolean load) {
        try (Connection c = getConn()) {
            ResultSet rs = c.createStatement().executeQuery("SELECT * FROM `users` WHERE `uid`='" + u.toString() + "'");

            if (!rs.next()) {
                if (load && Bukkit.getPlayer(u) != null) {
                    genData(u);
                    this.valid = true;
                } else {
                    this.valid = false;
                }
            }
        } catch (SQLException e) {
            this.valid = false;
        }

        this.u = u;
    }

    private void genData(UUID u) {
        Player p = Bukkit.getPlayer(u);
        assert p != null;

        try (Connection c = getConn()) {
            PreparedStatement ps = c.prepareStatement("INSERT INTO `users` (`uid`,`key`,`value`) VALUES ('?', '?', '?')");
            ps.setString(1, u.toString());
            ps.setString(2, "lastName");
            ps.setString(3, p.getName());
            ps.executeUpdate();

            PreparedStatement ps2 = c.prepareStatement("INSERT INTO `users` (`uid`,`key`,`value`) VALUES ('?', '?', '?')");
            ps2.setString(1, u.toString());
            ps2.setString(2, "firstSeen");
            ps2.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps2.executeUpdate();

            PreparedStatement ps3 = c.prepareStatement("INSERT INTO `users` (`uid`,`key`,`value`) VALUES ('?', '?', '?')");
            ps3.setString(1, u.toString());
            ps3.setString(2, "lastIP");
            ps3.setString(3, p.getAddress().getHostString());
            ps3.executeUpdate();
        } catch (SQLException e) {
            this.valid = false;
        }
    }

    private Connection getConn() {
        return TASP.getConfigDb();
    }

    @Override
    public Integer getInt(String path) {
        try (Connection c = getConn()) {
            ResultSet rs = c.createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "' AND `uid`='" + u.toString() + "'");
            if (rs.next()) {
                return rs.getInt("value");
            } else {
                return null;
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Unable to fetch data for player " + Person.get(u).getName() + " from the database.");
            return null;
        }
    }

    @Override
    public Boolean getBoolean(String path) {
        try (Connection c = getConn()) {
            ResultSet rs = c.createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "'");
            if (rs.next()) {
                return rs.getBoolean("value");
            } else {
                return null;
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Unable to fetch data for player " + Person.get(u).getName() + " from the database.");
            return null;
        }
    }

    @Override
    public String getString(String path) {
        try (Connection c = getConn()) {
            ResultSet rs = c.createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "'");
            if (rs.next()) {
                return rs.getString("value");
            } else {
                return null;
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Unable to fetch data for player " + Person.get(u).getName() + " from the database.");
            return null;
        }
    }

    @Override
    public Map getMap(String path) {
        try (Connection c = getConn()) {
            ResultSet rs = c.createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "'");
            if (rs.next()) {
                String s = rs.getString("value");
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)));
                return (Map) ois.readObject();
            } else {
                return null;
            }
        } catch (SQLException|IOException|ClassNotFoundException e) {
            Bukkit.getLogger().severe("Unable to fetch data for player " + Person.get(u).getName() + " from the database.");
            return null;
        }
    }

    @Override
    public List getList(String path) {
        try (Connection c = getConn()) {
            ResultSet rs = c.createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "' AND `uid`='" + u.toString() + "'");
            if (rs.next()) {
                String s = rs.getString("value");
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)));
                return (List) ois.readObject();
            } else {
                return null;
            }
        } catch (SQLException|IOException|ClassNotFoundException e) {
            Bukkit.getLogger().severe("Unable to fetch data for player " + Person.get(u).getName()+ " from the database.");
            return null;
        }
    }

    @Override
    public void setString(String path, String set) {
        try (Connection c = getConn()) {
            ResultSet rs = c.createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "' AND `uid`='" + u.toString() + "'");
            if(!rs.next()) {
                c.createStatement().executeUpdate("INSERT INTO `users` (`key`,`value`) VALUES ('" + path + "', '" + set + "')");
            } else {
                c.createStatement().executeUpdate("UPDATE `users` SET `value`='" + set + "' WHERE `uid`='" + u.toString() + "' AND `key`='" + path + "'");
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Unable to fetch data for player " + Person.get(u).getName() + " from the database.");
        }
    }

    @Override
    public void setInt(String path, Integer set) {
        try (Connection c = getConn()) {
            ResultSet rs = c.createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "' AND `uid`='" + u.toString() + "'");
            if(!rs.next()) {
                c.createStatement().executeUpdate("INSERT INTO `users` (`key`,`value`) VALUES ('" + path + "', '" + set.toString() + "')");
            } else {
                c.createStatement().executeUpdate("UPDATE `users` SET `value`='" + set.toString() + "' WHERE `uid`='" + u.toString() + "' AND `key`='" + path + "'");
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Unable to fetch data for player " + Person.get(u).getName() + " from the database.");
        }
    }

    @Override
    public void setMap(String path, Map set) {
        try (Connection c = getConn()) {
            ResultSet rs = c.createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "' AND `uid`='" + u.toString() + "'");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(set);
            String fin = baos.toString();

            if(!rs.next()) {
                c.createStatement().executeUpdate("INSERT INTO `users` (`key`,`value`) VALUES ('" + path + "', '" + fin + "')");
            } else {
                c.createStatement().executeUpdate("UPDATE `users` SET `value`='" + fin + "' WHERE `uid`='" + u.toString() + "' AND `key`='" + path + "'");
            }
        } catch (SQLException|IOException e) {
            Bukkit.getLogger().severe("Unable to fetch data for player " + u.toString() + " from the database.");
        }
    }

    @Override
    public void setList(String path, List set) {
        try (Connection c = getConn()) {
            ResultSet rs = c.createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "' AND `uid`='" + u.toString() + "'");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(set);
            String fin = baos.toString();

            if(!rs.next()) {
                c.createStatement().executeUpdate("INSERT INTO `users` (`key`,`value`) VALUES ('" + path + "', '" + fin + "')");
            } else {
                c.createStatement().executeUpdate("UPDATE `users` SET `value`='" + fin + "' WHERE `uid`='" + u.toString() + "' AND `key`='" + path + "'");
            }
        } catch (SQLException|IOException e) {
            Bukkit.getLogger().severe("Unable to fetch data for player " + Person.get(u).getName() + " from the database.");
        }
    }

    @Override
    public void setBoolean(String path, Boolean set) {
        try (Connection c = getConn()) {
            ResultSet rs = c.createStatement().executeQuery("SELECT `value` FROM `users` WHERE `key`='" + path + "' AND `uid`='" + u.toString() + "'");

            if(!rs.next()) {
                c.createStatement().executeUpdate("INSERT INTO `users` (`key`,`value`) VALUES ('" + path + "', '" + set.toString() + "')");
            } else {
                c.createStatement().executeUpdate("UPDATE `users` SET `value`='" + set.toString() + "' WHERE `uid`='" + u.toString() + "' AND `key`='" + path + "'");
            }
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Unable to fetch data for player " + Person.get(u).getName() + " from the database.");
        }
    }

}
