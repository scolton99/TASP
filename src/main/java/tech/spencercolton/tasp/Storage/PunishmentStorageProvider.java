package tech.spencercolton.tasp.Storage;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Punishment;

import java.util.UUID;

/**
 * @author Spencer Colton
 */
public interface PunishmentStorageProvider {

    boolean storePunishment(CommandSender punisher, Player punished, Punishment p, int punishNo);
    int previousInfractions(UUID u, String name);

}
