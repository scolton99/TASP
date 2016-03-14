package tech.spencercolton.tasp.Events;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import tech.spencercolton.tasp.Util.Punishment;

/**
 * @author Spencer Colton
 */
public class PersonPunishEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private CommandSender punisher;

    @Getter
    private Player punished;

    @Getter
    private Punishment punishment;

    public PersonPunishEvent(CommandSender punisher, Player punished, Punishment punishment) {
        this.punished = punished;
        this.punisher = punisher;
        this.punishment = punishment;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
