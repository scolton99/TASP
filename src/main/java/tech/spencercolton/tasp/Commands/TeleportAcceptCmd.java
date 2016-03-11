package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;

import java.util.List;

import static java.util.Collections.*;
import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Entity.Person.get;
import static tech.spencercolton.tasp.Util.Config.*;

/**
 * @author Spencer Colton
 */
public class TeleportAcceptCmd extends TASPCommand {

    @Getter
    private final String syntax = "/tpa";

    @Getter
    private static final String name = "tpa";

    @Getter
    private final String permission = "tasp.teleport.request.respond";

    @Getter
    private final String consoleSyntax = null;

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        Person p = get((Player) sender);
        if (p.getLastTeleportRequester() == null) {
            p.getPlayer().sendMessage(err() + "You have no existing teleport requests.");
            return CommandResponse.FAILURE;
        }

        Person pa = p.getLastTeleportRequester();
        p.clearTeleportRequests();

        PersonTeleportEvent e = (p.isLastTeleportHere() ? new PersonTeleportEvent(p.getPlayer(), pa.getPlayer(), true, false) : new PersonTeleportEvent(pa.getPlayer(), p.getPlayer(), false, false));
        getServer().getPluginManager().callEvent(e);

        return CommandResponse.SUCCESS;
    }

    @Override
    public List<String> getAliases() {
        return singletonList("tpaccept");
    }

}
