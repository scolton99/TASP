package tech.spencercolton.tasp.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tech.spencercolton.tasp.Events.PersonPunishEvent;
import tech.spencercolton.tasp.Scheduler.SyncPunishment;
import tech.spencercolton.tasp.Util.PunishManager;

/**
 * @author Spencer Colton
 */
public class PersonPunishListener implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void onPersonPunish (PersonPunishEvent e) {
        e.getPunishment().affect(e.getPunisher(), e.getPunished().getUniqueId(), PunishManager.getPsp().previousInfractions(e.getPunished().getUniqueId(), e.getPunishment().getName()));

        new SyncPunishment(e.getPunisher(), e.getPunished(), e.getPunishment(), PunishManager.getPsp().previousInfractions(e.getPunished().getUniqueId(), e.getPunishment().getName()));
    }

}
