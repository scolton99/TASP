package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;

import java.util.List;

import static java.util.Collections.*;
import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;

/**
 * @author Spencer Colton
 */
public class TeleportCmd extends TASPCommand {

    @Getter
    private final String syntax = "/tp <player>";

    @Getter
    private static final String name = "tp";

    @Getter
    private final String permission = "tasp.teleport";

    @Getter
    private final String consoleSyntax = null;

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);

        if (args.length != 1) {
            sendSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        Player p = (Player) sender;
        Player other = getPlayer(args[0]);

        if (other == null) {
            sendPlayerMessage(sender, args[0]);
            return CommandResponse.PLAYER;
        }

        getServer().getPluginManager().callEvent(new PersonTeleportEvent(p, other));

        return CommandResponse.SUCCESS;
    }

    @Override
    public List<String> getAliases() {
        return singletonList("teleport");
    }

}
