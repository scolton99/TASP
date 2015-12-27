package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static java.lang.Integer.parseInt;
import static org.bukkit.Bukkit.getWorld;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Util.Message.Setspawn.sendSpawnSetMessage;

public class SetspawnCmd extends TASPCommand {

    @Getter
    private final String syntax = "/setspawn [<x> <y> <z>] [world]";

    @Getter
    private final String consoleSyntax = "/setspawn <x> <y> <z> <world>";

    @Getter
    private static final String name = "setspawn";

    @Getter
    private final String permission = "tasp.setspawn";

    @Override
    public void execute(CommandSender sender, String... args) {
        assert (sender instanceof ConsoleCommandSender || sender instanceof Player);

        if (sender instanceof ConsoleCommandSender && args.length != 4) {
            sendConsoleSyntaxError(sender, this);
            return;
        }

        Integer x = null, y = null, z = null;
        World w;
        switch (args.length) {
            case 4: {
                try {
                    w = getWorld(args[3]);
                    x = parseInt(args[0]);
                    y = parseInt(args[1]);
                    z = parseInt(args[2]);
                    if (w == null) {
                        sendWorldMessage(sender, args[3]);
                        return;
                    }
                    if (sender instanceof ConsoleCommandSender) {
                        w.setSpawnLocation(x, y, z);
                        sendSpawnSetMessage(sender, x, y, z, w.getName());
                        return;
                    }
                } catch (NumberFormatException e) {
                    sendGenericSyntaxError(sender, this);
                    return;
                }
            }
            case 3: {
                assert sender instanceof Player;
                try {
                    x = parseInt(args[0]);
                    y = parseInt(args[1]);
                    z = parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sendSyntaxError(sender, this);
                    return;
                }
            }
            case 0: {
                assert sender instanceof Player;
                w = ((Player) sender).getWorld();
                if (x == null)
                    x = ((Player) sender).getLocation().getBlockX();
                if (y == null)
                    y = ((Player) sender).getLocation().getBlockY();
                if (z == null)
                    z = ((Player) sender).getLocation().getBlockZ();

                w.setSpawnLocation(x, y, z);
                sendSpawnSetMessage(sender, x, y, z, w.getName());
                return;
            }
            default: {
                sendGenericSyntaxError(sender, this);
            }
        }
    }

}
