package tech.spencercolton.tasp.Events;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PersonSendMessageEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final CommandSender from;
    private final CommandSender to;
    private String message;

    public PersonSendMessageEvent(CommandSender from, CommandSender to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public CommandSender getFrom() {
        return this.from;
    }

    public CommandSender getTo() {
        return this.to;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public void setMessage(String newMessage) {
        this.message = newMessage;
    }

}
