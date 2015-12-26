package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Events.PersonTeleportAllHereEvent;

import java.util.Arrays;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class TeleportAllHereRequestCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/tpar";

    public static final String name = "tpar";

    @Getter
    private static final String permission = "tasp.teleport.request.all";

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

        Bukkit.getServer().getPluginManager().callEvent(new PersonTeleportAllHereEvent(p, true));
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("tpallrhere", "tparhere", "teleportallrhere", "teleportrall", "tpallherer", "tpaherer", "teleportallherer", "teleportallr", "tpallrequesthere", "tparequesthere", "teleportallrequesthere", "teleportrequestall", "tpallhererequest", "tpahererequest", "teleportallhererequest", "teleportallrequest");
    }


}
