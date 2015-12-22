package tech.spencercolton.tasp.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.TASP;

public class LogoutListener implements Listener {

    @SuppressWarnings("unused")
    @EventHandler
    public void onEvent(PlayerQuitEvent e) {
        TASP.unloadPerson(Person.get(e.getPlayer()));
    }

}
