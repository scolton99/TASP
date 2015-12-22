package tech.spencercolton.tasp.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tech.spencercolton.tasp.Commands.AFKCmd;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Scheduler.AFKTimer;
import tech.spencercolton.tasp.Util.Config;

public class ChatListener implements Listener {

    @SuppressWarnings("unused")
    @EventHandler
    public void onEvent(AsyncPlayerChatEvent e) {
        Person p = Person.get(e.getPlayer());
        assert p != null;

        if(p.isMuted()) {
            e.setCancelled(true);
            p.getPlayer().sendMessage(Config.err() + "You are muted.  Other players will not see your message.");
            return;
        }

        if(p.isAfk()) {
            p.setAfk(false);
            AFKCmd.broadcastAFKMessage(p);
        } else {
            AFKTimer.timers.get(p).cancel();
            new AFKTimer(p);
        }
    }

}
