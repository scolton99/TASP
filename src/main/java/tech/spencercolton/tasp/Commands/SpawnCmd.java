package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;

/**
 * @author Spencer Colton
 */
public class SpawnCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/spawn";

    public static final String name = "spawn";

    @Getter
    private static final String consoleSyntax = null;

    @Getter
    private static final String permission = "tasp.spawn";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 0) {
            Command.sendSyntaxError(sender, this);
            return;
        }
        assert sender instanceof Player;

        Bukkit.getPluginManager().callEvent(new PersonTeleportEvent(Person.get((Player)sender), ((Player)sender).getWorld().getSpawnLocation()));
    }

}
