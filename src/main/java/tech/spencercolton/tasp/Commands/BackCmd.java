package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.Message;

/**
 * @author Spencer Colton
 */
public class BackCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/back";

    public static final String name = "back";

    @Getter
    private static final String consoleSyntax = null;

    @Getter
    private static final String permission = "tasp.teleport.back";

    @Override
    public void execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);

        Person p = Person.get((Player)sender);
        if(p.getLastLocation() == null) {
            Message.Back.Error.sendNoBackMessage(sender);
        } else {
            Bukkit.getPluginManager().callEvent(new PersonTeleportEvent(p, p.getLastLocation()));
        }
    }

}
