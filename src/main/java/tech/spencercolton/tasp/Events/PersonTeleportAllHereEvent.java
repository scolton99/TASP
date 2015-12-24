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

    @Getter @Setter
    private Person to;

    @Getter
    private boolean request;

    public PersonTeleportAllHereEvent(Person to) {
        this(to, false);
    }

    public PersonTeleportAllHereEvent(Person to, boolean request) {
        this.to = to;
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
