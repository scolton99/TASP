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
public class TeleportAllHereCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/tpall";

    public static final String name = "tpall";

    @Getter
    private static final String permission = "tasp.teleport.all";

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

        Bukkit.getPluginManager().callEvent(new PersonTeleportAllHereEvent(p));
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("tpallhere", "tpahere", "teleportallhere", "teleportall");
    }

}
