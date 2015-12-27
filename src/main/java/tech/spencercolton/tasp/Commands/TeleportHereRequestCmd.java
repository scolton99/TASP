package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;

import java.util.List;

import static java.util.Arrays.asList;
import static org.bukkit.Bukkit.getPlayer;
import static org.bukkit.Bukkit.getServer;
import static tech.spencercolton.tasp.Commands.Command.sendPlayerMessage;
import static tech.spencercolton.tasp.Commands.Command.sendSyntaxError;

/**
 * @author Spencer Colton
 */
public class TeleportHereRequestCmd extends TASPCommand {

    @Getter
    private final String syntax = "/tprhere <player>";

    @Getter
    private static final String name = "tprhere";

    @Getter
    private final String permission = "tasp.teleport.request.here";

    @Getter
    private final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);

        if (args.length != 1) {
            sendSyntaxError(sender, this);
            return;
        }

        Player p = getPlayer(args[0]);
        if (p == null) {
            sendPlayerMessage(sender, args[0]);
            return;
        }

        getServer().getPluginManager().callEvent(new PersonTeleportEvent((Player) sender, p, true, true));
    }

    @Override
    public List<String> getAliases() {
        return asList("tprequesthere", "teleportrequesthere", "teleporthererequest", "tphererequest", "tpherer");
    }

}
