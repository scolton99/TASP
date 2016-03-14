package tech.spencercolton.tasp.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tech.spencercolton.tasp.Events.TASPBroadcastEvent;
import tech.spencercolton.tasp.Util.ChatFilter;
import tech.spencercolton.tasp.Util.ColorChat;
import tech.spencercolton.tasp.Configuration.Config;

/**
 * @author Spencer Colton
 * @since 0.0.3
 */
public class TASPBroadcastListener implements Listener {

    @SuppressWarnings("unused")
    @EventHandler
    public void onEvent(TASPBroadcastEvent e) {
        e.setMessage(ColorChat.color(e.getMessage()));
        e.setMessage(ChatFilter.filter(e.getMessage()));
        Bukkit.getServer().broadcastMessage(Config.c1() + "[" + Config.c4() + e.getPrefix() + Config.c1() + "] " + Config.c3() + e.getMessage());
    }

}
