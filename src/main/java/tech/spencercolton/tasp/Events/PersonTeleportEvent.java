package tech.spencercolton.tasp.Events;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Enums.TeleportType;

public class PersonTeleportEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private Person requester;

    @Getter
    private Person requestee;

    @Getter
    private Location location;

    @Getter
    private boolean here;

    @Getter
    private boolean request;

    @Getter
    private final TeleportType type;

    public PersonTeleportEvent(Player requester, Player requestee, boolean here) {
        this(Person.get(requester), Person.get(requestee), here, false, TeleportType.PERSON);
    }

    public PersonTeleportEvent(Player requester, Player requestee, boolean here, boolean request) {
        this(Person.get(requester), Person.get(requestee), here, request, TeleportType.PERSON);
    }

    private PersonTeleportEvent(Person requester, Person requestee, boolean here, boolean request, TeleportType type) {
        this.requester = requester;
        this.requestee = requestee;
        this.here = here;
        this.request = request;
        this.type = type;
    }

    public PersonTeleportEvent(Player from, Player to) {
        this(Person.get(from), Person.get(to), false, false, TeleportType.PERSON);
    }

    public PersonTeleportEvent(Person teleporter, Location to) {
        this.requester = teleporter;
        this.location = to;
        this.type = TeleportType.LOCATION;
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
