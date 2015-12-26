package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Message;

import java.util.Collections;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class TeleportDenyCommand extends TASPCommand {

    @Getter
    private static final String syntax = "/tpd";

    public static final String name = "tpd";

    @Getter
    private static final String permission = "tasp.teleport.request.respond";

    @Getter
    private static final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);

        if(args.length != 0) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Person p = Person.get((Player)sender);
        if(p.getLastTeleportRequester() == null) {
            Message.Teleport.Error.sendNoTeleportRequestsMessage(sender);
            return;
        }

        Person pa = p.getLastTeleportRequester();
        p.clearTeleportRequests();

        Message.Teleport.sendTeleportDenyMessage(pa.getPlayer(), p.getPlayer());
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("tpdeny");
    }

}
