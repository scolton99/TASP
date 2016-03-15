package tech.spencercolton.tasp.Storage;

import org.bukkit.Bukkit;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.TASP;
import tech.spencercolton.tasp.Util.MailObj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author Spencer Colton
 */
public class DatabaseMailStorage implements MailStorageProvider {

    private static Connection getConn() {
        return TASP.getConfigDb();
    }

    @Override
    public boolean delete(UUID uid) {
        try (Connection c = getConn()) {
            c.createStatement().executeUpdate("DELETE FROM `mail` WHERE `uid`='" + uid.toString() + "'");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setRead(UUID uid) {
        try (Connection c = getConn()) {
            ResultSet rs = c.createStatement().executeQuery("SELECT * FROM `mail` WHERE `uid`='" + uid.toString() + "'");

            if (rs.next()) {
                c.createStatement().executeUpdate("UPDATE `mail` SET `read`=true WHERE `uid`='" + uid.toString() + "'");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            Bukkit.getLogger().warning("An error occurred while setting a mail as 'read.'");
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean send(Person from, UUID to, String msg) {
        try (Connection c = getConn()) {
            MailObj mo = new MailObj(from, to, msg);

            PreparedStatement g = c.prepareStatement("INSERT INTO `mail` (`uid`, `sent`, `currname`, `from`, `to`, `message`) VALUES ('?', '?', '?', '?', '?', '?')");
            g.setString(1, mo.getId().toString());
            g.setTimestamp(2, mo.getSent());
            g.setString(3, mo.getCurrname());
            g.setString(4, mo.getFrom().toString());
            g.setString(5, mo.getTo().toString());
            g.setString(6, mo.getMessage());
            g.executeUpdate();
            return true;
        } catch (SQLException e) {
            Bukkit.getLogger().warning("An error occurred while sending mail.");
            return false;
        }
    }

    @Override
    public List<MailObj> fetch(Person to) {
        try (Connection c = getConn()) {
            String uid = to.getUid().toString();

            ResultSet rs = c.createStatement().executeQuery("SELECT * FROM `mail` WHERE `to`='" + uid + "'");

            List<MailObj> obs = new ArrayList<>();

            while (rs.next()) {
                obs.add(MailObj.fromRS(rs));
            }

            Collections.sort(obs, (o1, o2) -> o1.getSent().compareTo(o2.getSent()));

            return obs;
        } catch (SQLException e) {
            Bukkit.getLogger().warning("Failed to fetch mails for player '" + to.getName() + "' (UUID: " + to.getUid().toString() + ")");
            return null;
        }
    }

}
