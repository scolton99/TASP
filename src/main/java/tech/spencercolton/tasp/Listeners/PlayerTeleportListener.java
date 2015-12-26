package tech.spencercolton.tasp.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import tech.spencercolton.tasp.Entity.Person;

/**
 * @author Spencer Colton
 */
public class PlayerTeleportListener implements Listener {

    @SuppressWarnings("unused")
    @EventHandler
    public void onEvent(PlayerTeleportEvent e) {
        Person.get(e.getPlayer()).setLastLocation(e.getFrom());
    }

}
