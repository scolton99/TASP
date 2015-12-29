package tech.spencercolton.tasp.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tech.spencercolton.tasp.Events.PersonHelpmeEvent;
import tech.spencercolton.tasp.TASP;
import tech.spencercolton.tasp.Util.ChatFilter;
import tech.spencercolton.tasp.Util.ColorChat;
import tech.spencercolton.tasp.Util.Config;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * @author Spencer Colton
 */
public class PersonHelpmeListener implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void onEvent(PersonHelpmeEvent e) {
        String message = e.getMessage();

        e.setMessage(ColorChat.color(e.getMessage()));
        e.setMessage(ChatFilter.filter(e.getMessage()));

        Predicate<Player> pr = pz -> pz.hasPermission(TASP.getHelpMeRcvPrivilege()) && pz.isOnline();
        relay(Bukkit.getOnlinePlayers(), pr, e);
    }

    private void relay(Collection<? extends Player> people, Predicate<Player> pr, PersonHelpmeEvent e) {
        people.stream().forEach(p -> {
            if(pr.test(p)) {
                p.getPlayer().sendMessage(Config.c1() + "[" + Config.c4() + Config.getString("helpme-prefix") + Config.c1() + "::" + Config.c4() + e.getAsker().getPlayer().getDisplayName() + Config.c1() + "] " + e.getMessage());
            }
        });
    }

}
