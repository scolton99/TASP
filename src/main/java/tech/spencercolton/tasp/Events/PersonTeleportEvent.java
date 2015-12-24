package tech.spencercolton.tasp.Events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import tech.spencercolton.tasp.Entity.Person;

public class PersonTeleportEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter @Setter
    private Person teleporter;

    @Getter @Setter
    private Person to;

    @Getter
    private final boolean here;

    @Getter @Setter
    private boolean request;

    public PersonTeleportEvent(Player from, Player to, boolean here) {
        this(Person.get(from), Person.get(to), here, false);
    }

    public PersonTeleportEvent(Player from, Player to, boolean here, boolean request) {
        this(Person.get(from), Person.get(to), here, request);
    }

    private PersonTeleportEvent(Person from, Person to, boolean here, boolean request) {
        this.teleporter = from;
        this.to = to;
        this.here = here;
        this.request = request;
    }

    public PersonTeleportEvent(Player from, Player to) {
        this(Person.get(from), Person.get(to), false, false);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
