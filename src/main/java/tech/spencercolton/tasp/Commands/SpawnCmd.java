package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;

import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Entity.Person.*;

/**
 * @author Spencer Colton
 */
public class SpawnCmd extends TASPCommand {

    @Getter
    private final String syntax = "/spawn";

    @Getter
    private static final String name = "spawn";

    @Getter
    private final String consoleSyntax = null;

    @Getter
    private final String permission = "tasp.spawn";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            sendSyntaxError(sender, this);
            return;
        }
        assert sender instanceof Player;

        getPluginManager().callEvent(new PersonTeleportEvent(get((Player) sender), ((Player) sender).getWorld().getSpawnLocation()));
    }

}
