package tech.spencercolton.tasp.Events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import tech.spencercolton.tasp.Entity.Person;

/**
 * @author Spencer Colton
 */
public class PersonHelpmeEvent extends Event {

    private static HandlerList handlers = new HandlerList();

    @Getter
    @Setter
    private Person asker;

    @Getter
    @Setter
    private String message;

    public PersonHelpmeEvent(Person asker, String message) {
        this.asker = asker;
        this.message = message;
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
