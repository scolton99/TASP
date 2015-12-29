package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;
import tech.spencercolton.tasp.Util.Message;

import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Entity.Person.*;

/**
 * @author Spencer Colton
 */
public class BackCmd extends TASPCommand {

    @Getter
    private final String syntax = "/back";

    @Getter
    private static final String name = "back";

    @Getter
    private final String consoleSyntax = null;

    @Getter
    private final String permission = "tasp.teleport.back";

    @Override
    public void execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);

        Person p = get((Player) sender);
        if (p.getLastLocation() == null) {
            Message.Back.Error.sendNoBackMessage(sender);
        } else {
            getPluginManager().callEvent(new PersonTeleportEvent(p, p.getLastLocation()));
        }
    }

}
