package tech.spencercolton.tasp.Events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import tech.spencercolton.tasp.Util.M;

public class TASPBroadcastEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    @Setter
    private CommandSender p;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private String prefix;

    @Getter
    @Setter
    boolean console = false;

    public TASPBroadcastEvent(CommandSender p, String message) {
        this(p, message, M.u("broadcast-prefix"));
    }

    private TASPBroadcastEvent(CommandSender p, String message, String prefix) {
        if (p instanceof ConsoleCommandSender)
            this.console = true;
        this.p = p;
        this.message = message;
        this.prefix = prefix;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

}
