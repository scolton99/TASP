package tech.spencercolton.tasp.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tech.spencercolton.tasp.Events.PersonPunishEvent;

/**
 * @author Spencer Colton
 */
public class PersonPunishListener implements Listener {

    @EventHandler
    public void onPersonPunish (PersonPunishEvent e) {
        e.getPunishment().affect(e.getPunisher(), e.getPunished());
    }

}
