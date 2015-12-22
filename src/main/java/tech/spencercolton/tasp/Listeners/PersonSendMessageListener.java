package tech.spencercolton.tasp.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Events.PersonSendMessageEvent;
import tech.spencercolton.tasp.Util.Config;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author Spencer Colton
 */
public class PersonSendMessageListener implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void onEvent(PersonSendMessageEvent e) {
        Predicate<Person> allStalkers = p -> p.getData().getBoolean("stalker") != null && p.getData().getBoolean("stalker");

        this.relay(Person.getPeople(), allStalkers, e);
    }

    private void relay(List<Person> pl, Predicate<Person> p, PersonSendMessageEvent e) {
        pl.stream().forEach(pa -> {
            if(p.test(pa) && !(pa.getPlayer().equals(e.getTo()) || pa.getPlayer().equals(e.getFrom())))
                pa.getPlayer().sendMessage(Config.c3() + "[" + Config.c1() + e.getFrom().getName() + Config.c3() + " -> " + Config.c1() + e.getTo().getName() + Config.c3() + "] " + ChatColor.WHITE + e.getMessage());

        });
    }

}
