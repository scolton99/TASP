package tech.spencercolton.tasp.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Events.PersonSendMessageEvent;
import tech.spencercolton.tasp.TASP;
import tech.spencercolton.tasp.Util.ChatFilter;
import tech.spencercolton.tasp.Util.ColorChat;
import tech.spencercolton.tasp.Configuration.Config;
import tech.spencercolton.tasp.Util.Message;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author Spencer Colton
 */
public class PersonSendMessageListener implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void onEvent(PersonSendMessageEvent e) {
        if (!(e.getTo() instanceof ConsoleCommandSender) && !(e.getFrom() instanceof ConsoleCommandSender)) {
            Person p2 = Person.get((Player) e.getTo());
            Person p1 = Person.get((Player) e.getFrom());

            if (p2.isPlayerBlocked(p1)) {
                Message.MessageCmd.Error.sendBlockedMessage(e.getFrom(), p2.getPlayer().getDisplayName());
                return;
            }

            if (p1.isPlayerBlocked(p2)) {
                Message.MessageCmd.Error.sendYouBlockedMessage(e.getFrom(), p2.getPlayer().getDisplayName());
                return;
            }
        }

        if (e.getFrom() instanceof ConsoleCommandSender) {
            TASP.consoleLast = e.getTo();
        } else {
            Person px = Person.get((Player) e.getFrom());
            px.setLastMessaged(e.getFrom());
        }

        if (e.getTo() instanceof ConsoleCommandSender) {
            TASP.consoleLast = e.getFrom();
        } else {
            Person pz = Person.get((Player) e.getTo());
            pz.setLastMessaged(e.getFrom());
        }

        e.setMessage(ColorChat.color(e.getMessage()));
        e.setMessage(ChatFilter.filter(e.getMessage()));

        e.getTo().sendMessage(Config.c3() + "[ <- " + Config.c1() + e.getFrom().getName() + Config.c3() + "] " + ChatColor.WHITE + e.getMessage());

        e.getFrom().sendMessage(Config.c3() + "[ -> " + Config.c1() + e.getTo().getName() + Config.c3() + "] " + ChatColor.WHITE + e.getMessage());

        Predicate<Person> allStalkers = Person::isStalker;
        this.relay(Person.getPeople(), allStalkers, e);
    }

    private void relay(List<Person> pl, Predicate<Person> pg, PersonSendMessageEvent e) {
        pl.stream().forEach(pa -> {
            if (pg.test(pa) && !(pa.getPlayer().equals(e.getTo()) || pa.getPlayer().equals(e.getFrom())))
                pa.getPlayer().sendMessage(Config.c3() + "[" + Config.c1() + e.getFrom().getName() + Config.c3() + " -> " + Config.c1() + e.getTo().getName() + Config.c3() + "] " + ChatColor.WHITE + e.getMessage());

        });
    }

}
