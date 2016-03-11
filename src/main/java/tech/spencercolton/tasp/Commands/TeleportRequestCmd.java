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
public class TeleportRequestCmd extends TASPCommand {

    @Getter
    private final String syntax = "/tpr <player>";

    @Getter
    private static final String name = "tpr";

    @Getter
    private final String permission = "tasp.teleport.request";

    @Getter
    private final String consoleSyntax = null;

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);

        if (args.length != 1) {
            sendSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        Player p = getPlayer(args[0]);
        if (p == null) {
            sendPlayerMessage(sender, args[0]);
            return CommandResponse.PLAYER;
        }

        getServer().getPluginManager().callEvent(new PersonTeleportEvent((Player) sender, p, false, true));

        return CommandResponse.SUCCESS;
    }

    @Override
    public List<String> getAliases() {
        return singletonList("tprequest");
    }

}
