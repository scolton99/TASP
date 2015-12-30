package tech.spencercolton.tasp.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import tech.spencercolton.tasp.Entity.Person;

public class PlayerDamageListener implements Listener {

    @SuppressWarnings("unused")
    @EventHandler
    public void onEvent(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Person p = Person.get((Player) e.getEntity());

        if (p == null)
            return;

        if (p.isGod() && e.getCause() != EntityDamageEvent.DamageCause.CUSTOM && e.getCause() != EntityDamageEvent.DamageCause.VOID) {
            p.getPlayer().setExhaustion(0.0F);
            p.getPlayer().setSaturation(20.0F);
            e.setCancelled(true);
        }

        if (p.isBuddha() && e.getCause() != EntityDamageEvent.DamageCause.CUSTOM && e.getCause() != EntityDamageEvent.DamageCause.VOID) {
            double d = p.getPlayer().getHealth();
            if (e.getDamage() >= d)
                e.setDamage(d - 0.5D);
        }
    }

}
