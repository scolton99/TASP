package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Events.PersonTeleportAllHereEvent;

import java.util.List;

import static java.util.Arrays.asList;
import static org.bukkit.Bukkit.getServer;
import static tech.spencercolton.tasp.Commands.Command.sendSyntaxError;
import static tech.spencercolton.tasp.Entity.Person.get;

/**
 * @author Spencer Colton
 */
public class TeleportAllHereRequestCmd extends TASPCommand {

    @Getter
    private final String syntax = "/tpar";

    @Getter
    private static final String name = "tpar";

    @Getter
    private final String permission = "tasp.teleport.request.all";

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

        getServer().getPluginManager().callEvent(new PersonTeleportAllHereEvent(p, true));
    }

    @Override
    public List<String> getAliases() {
        return asList("tpallrhere", "tparhere", "teleportallrhere", "teleportrall", "tpallherer", "tpaherer", "teleportallherer", "teleportallr", "tpallrequesthere", "tparequesthere", "teleportallrequesthere", "teleportrequestall", "tpallhererequest", "tpahererequest", "teleportallhererequest", "teleportallrequest");
    }


}
