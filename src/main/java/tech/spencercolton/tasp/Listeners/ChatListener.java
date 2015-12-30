package tech.spencercolton.tasp.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Scheduler.AFKTimer;
import tech.spencercolton.tasp.Util.ChatFilter;
import tech.spencercolton.tasp.Util.ColorChat;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.Message;

public class ChatListener implements Listener {

    @SuppressWarnings("unused")
    @EventHandler
    public void onEvent(AsyncPlayerChatEvent e) {
        Person p = Person.get(e.getPlayer());
        assert p != null;

        if (p.isMuted()) {
            e.setCancelled(true);
            p.getPlayer().sendMessage(Config.err() + "You are muted.  Other players will not see your message.");
            return;
        }

        e.setMessage(ColorChat.color(e.getMessage()));
        e.setMessage(ChatFilter.filter(e.getMessage()));

        if (p.isAfk()) {
            p.setAfk(false);
            Message.AFK.broadcastAFKMessage(p.getPlayer());
        } else {
            AFKTimer.timers.get(p).cancel();
            new AFKTimer(p);
        }
    }

}
