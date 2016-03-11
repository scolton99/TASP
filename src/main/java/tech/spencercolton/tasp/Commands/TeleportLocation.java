package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;

/**
 * @author Spencer Colton
 */
public class TeleportLocation extends TASPCommand {

    @Getter
    private final String syntax = "/tploc <x> <y> <z> [world]";

    @Getter
    public static final String name = "tploc";

    @Getter
    private final String consoleSyntax = null;

    @Getter
    private final String permission = "tasp.teleport.location";

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        assert sender instanceof Player;

        if (args.length < 3 || args.length > 4) {
            Command.sendSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        World w = null;
        Integer x, y, z;
        switch (args.length) {
            case 4: {
                w = Bukkit.getWorld(args[3]);
                if (w == null) {
                    Command.sendWorldMessage(sender, args[3]);
                    return CommandResponse.WORLD;
                }
            }
            case 3: {
                try {
                    x = Integer.parseInt(args[0]);
                    y = Integer.parseInt(args[1]);
                    z = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                    return CommandResponse.SYNTAX;
                }
                if (w == null)
                    w = ((Player) sender).getWorld();
                Location l = new Location(w, x, y, z);
                Bukkit.getPluginManager().callEvent(new PersonTeleportEvent(Person.get((Player) sender), l));
                return CommandResponse.SUCCESS;
            }
            default: {
                Command.sendSyntaxError(sender, this);
                return CommandResponse.SYNTAX;
            }
        }
    }

}
