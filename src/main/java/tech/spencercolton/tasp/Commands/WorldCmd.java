package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;
import tech.spencercolton.tasp.Util.Message;

import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;

/**
 * @author Spencer Colton
 */
public class WorldCmd extends TASPCommand {

    @Getter
    private final String syntax = "/world [world]";

    @Getter
    private static final String name = "world";

    @Getter
    private final String permission = "tasp.world";

    @Getter
    private final String consoleSyntax = null;

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);
        if (args.length > 1) {
            sendSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        World w = null;
        switch (args.length) {
            case 1: {
                w = getWorld(args[0]);
                if (w == null) {
                    sendWorldMessage(sender, args[0]);
                    return CommandResponse.WORLD;
                }
            }
            case 0: {
                if (args.length == 0) {
                    Message.World.sendWorldMessage(sender);
                } else {
                    assert w != null;
                    Person p = Person.get((Player)sender);
                    Location l = p.getLocation(w);

                    if(l == null)
                        l = w.getSpawnLocation();

                    Bukkit.getPluginManager().callEvent(new PersonTeleportEvent(p, l));
                }
                return CommandResponse.SUCCESS;
            }
            default: {
                sendSyntaxError(sender, this);
                return CommandResponse.SYNTAX;
            }
        }
    }

}
