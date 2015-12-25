package tech.spencercolton.tasp.Listeners;

import com.oracle.jrockit.jfr.EventDefinition;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import tech.spencercolton.tasp.Entity.Person;

/**
 * @author Spencer Colton
 */
public class PlayerTeleportListener {

    @EventHandler
    public void onEvent(PlayerTeleportEvent e) {
        Person.get(e.getPlayer()).setLastLocation(e.getFrom());
    }

}
