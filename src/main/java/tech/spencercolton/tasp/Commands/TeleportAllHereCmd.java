package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Events.PersonTeleportAllHereEvent;

import java.util.List;

import static java.util.Arrays.*;
import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Entity.Person.*;

/**
 * @author Spencer Colton
 */
public class TeleportAllHereCmd extends TASPCommand {

    @Getter
    private final String syntax = "/tpall";

    @Getter
    private static final String name = "tpall";

    @Getter
    private final String permission = "tasp.teleport.all";

    @Getter
    private final String consoleSyntax = null;

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);
        if (args.length != 0) {
            sendSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        Person p = get((Player) sender);

        getPluginManager().callEvent(new PersonTeleportAllHereEvent(p));

        return CommandResponse.SUCCESS;
    }

    @Override
    public List<String> getAliases() {
        return asList("tpallhere", "tpahere", "teleportallhere", "teleportall");
    }

}
