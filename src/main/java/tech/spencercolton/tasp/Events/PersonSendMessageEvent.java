package tech.spencercolton.tasp.Events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PersonSendMessageEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final CommandSender from;

    @Getter
    private final CommandSender to;

    @Getter @Setter
    private String message;

    public PersonSendMessageEvent(CommandSender from, CommandSender to, String message) {
        this.from = from;
        this.to = to;
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
