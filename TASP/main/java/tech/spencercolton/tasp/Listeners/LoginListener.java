package tech.spencercolton.tasp.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tech.spencercolton.tasp.Entity.Person;

public class LoginListener implements Listener {

    @EventHandler
    public void onEvent(PlayerJoinEvent e) {
        new Person(e.getPlayer());
    }

}
