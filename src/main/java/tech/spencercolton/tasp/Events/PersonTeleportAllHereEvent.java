package tech.spencercolton.tasp.Events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import tech.spencercolton.tasp.Entity.Person;

/**
 * @author Spencer Colton
 */
public class PersonTeleportAllHereEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private Person requester;

    @Getter
    private boolean request;

    public PersonTeleportAllHereEvent(Person to) {
        this(to, false);
    }

    public PersonTeleportAllHereEvent(Person requester, boolean request) {
        this.requester = requester;
        this.request = request;
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
