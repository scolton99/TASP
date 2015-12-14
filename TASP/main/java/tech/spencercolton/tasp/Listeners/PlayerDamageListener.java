package tech.spencercolton.tasp.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import tech.spencercolton.tasp.Commands.GodCmd;
import tech.spencercolton.tasp.Entity.Person;

public class PlayerDamageListener implements Listener {

    public void onEvent(EntityDamageEvent e) {
        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        Person p = Person.get((Player)e.getEntity());

        if(p == null)
            return;

        if(GodCmd.gods.contains(p)) {
            e.setCancelled(true);
        }

    }

}
