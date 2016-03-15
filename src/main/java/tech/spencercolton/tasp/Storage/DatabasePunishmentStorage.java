package tech.spencercolton.tasp.Storage;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.TASP;
import tech.spencercolton.tasp.Util.Punishment;

import java.sql.*;
import java.util.UUID;

/**
 * @author Spencer Colton
 */
public class DatabasePunishmentStorage implements PunishmentStorageProvider {

    @Override
    public boolean storePunishment(CommandSender punisher, Player punished, Punishment p, int punishNo) {
        try (Connection c = TASP.getConfigDb()) {
            assert c != null;

            PreparedStatement ps = c.prepareStatement("INSERT INTO `punishments` (`type`,`punished`,`punisher`,`reason`,`created_at`,`end_date`) VALUES ('?', '?', '?', '?', '?', '?'");
            ps.setString(1, p.getType(punishNo).toString());
            ps.setString(2, punished.getUniqueId().toString());

            String punisherd = punisher instanceof Player ? ((Player) punisher).getUniqueId().toString() : punisher.getName();

            ps.setString(3, punisherd);
            ps.setString(4, p.getName());
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            Timestamp ts = p.getEffect(punishNo).getDuration() != null ? new Timestamp(System.currentTimeMillis() + p.getEffect(punishNo).getDuration() * 60000) : new Timestamp(0);

            ps.setTimestamp(6, ts);
            return true;
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Couldn't sync punishment to the database!");
            return false;
        }
    }

    @Override
    public int previousInfractions(UUID u, String name) {
        try (Connection c = TASP.getConfigDb()){
            assert c != null;

            ResultSet rs = c.createStatement().executeQuery("SELECT COUNT(*) AS NoRecords FROM `punishments` WHERE `uid`='" + u.toString() + "' AND `reason`='" + name + "'");
            if (rs.next()) {
                return rs.getInt("NoRecords");
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            String say;
            if (Bukkit.getPlayer(u) != null)
                say = Bukkit.getPlayer(u).getName();
            else
                say = u.toString();
            Bukkit.getLogger().severe("Unable to load previous punishments for player " + say + "!");
            return -1;
        }
    }

}
