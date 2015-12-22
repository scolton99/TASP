package tech.spencercolton.tasp.Listeners;

import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import tech.spencercolton.tasp.Entity.Person;

/**
 * @author Spencer Colton
 */
public class EntityTargetListener implements Listener {

    @EventHandler
    public void onEvent(EntityTargetEvent e) {
        if(e.getTarget() instanceof Player && e.getEntity() instanceof Monster) {
            Person p = Person.get((Player)e.getTarget());
            assert p != null;

            if(p.isFOM())
                e.setCancelled(true);
        }
    }

}
