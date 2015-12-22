package tech.spencercolton.tasp.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;
import tech.spencercolton.tasp.Commands.AFKCmd;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Scheduler.AFKTimer;
import tech.spencercolton.tasp.Util.Config;

public class MoveListener implements Listener {

    @SuppressWarnings("unused")
    @EventHandler
    public void onEvent(PlayerMoveEvent e) {
        Person p = Person.get(e.getPlayer());
        if(p.isAfk()) {
            p.setAfk(false);
            AFKCmd.broadcastAFKMessage(p);
        } else {
            BukkitTask k = AFKTimer.timers.get(p);
            k.cancel();
            new AFKTimer(p, Config.afkTime());
        }
        if(p.isGod()) {
            p.getPlayer().setExhaustion(0.0F);
            p.getPlayer().setSaturation(20.0F);
        }
    }

}
