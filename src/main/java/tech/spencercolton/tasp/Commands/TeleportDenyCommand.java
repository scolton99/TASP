package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;

import java.util.List;

import static java.util.Collections.singletonList;
import static tech.spencercolton.tasp.Commands.Command.sendSyntaxError;
import static tech.spencercolton.tasp.Entity.Person.get;
import static tech.spencercolton.tasp.Util.Message.Teleport.Error.sendNoTeleportRequestsMessage;
import static tech.spencercolton.tasp.Util.Message.Teleport.sendTeleportDenyMessage;

/**
 * @author Spencer Colton
 */
public class TeleportDenyCommand extends TASPCommand {

    @Getter
    private final String syntax = "/tpd";

    @Getter
    private static final String name = "tpd";

    @Getter
    private final String permission = "tasp.teleport.request.respond";

    @Getter
    private final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);

        if (args.length != 0) {
            sendSyntaxError(sender, this);
            return;
        }

        Person p = get((Player) sender);
        if (p.getLastTeleportRequester() == null) {
            sendNoTeleportRequestsMessage(sender);
            return;
        }

        Person pa = p.getLastTeleportRequester();
        p.clearTeleportRequests();

        sendTeleportDenyMessage(pa.getPlayer(), p.getPlayer());
    }

    @Override
    public List<String> getAliases() {
        return singletonList("tpdeny");
    }

}
