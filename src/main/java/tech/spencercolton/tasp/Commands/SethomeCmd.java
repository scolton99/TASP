package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Entity.Person.get;
import static tech.spencercolton.tasp.Util.Message.Sethome.Error.sendPitchOOBMessage;
import static tech.spencercolton.tasp.Util.Message.Sethome.Error.sendYawOOBMessage;
import static tech.spencercolton.tasp.Util.Message.Sethome.sendHomeMessage;

public class SethomeCmd extends TASPCommand {

    @Getter
    private final String syntax = "/sethome [<x> <y> <z>] [world] [player] [<yaw> <pitch>]";

    @Getter
    private final String consoleSyntax = "/sethome <player> <x> <y> <z> [world] [<yaw> <pitch>]";

    @Getter
    private static final String name = "sethome";

    @Getter
    private final String permission = "tasp.sethome";

    @Override
    public void execute(CommandSender sender, String... args) {
        assert (sender instanceof ConsoleCommandSender || sender instanceof Player);

        if (sender instanceof ConsoleCommandSender && (args.length < 4 || args.length > 7 || args.length == 6)) {
            sendConsoleSyntaxError(sender, this);
            return;
        }

        if (sender instanceof Player && (args.length > 7 || args.length == 6)) {
            sendSyntaxError(sender, this);
            return;
        }

        World w = null;
        Double x = null, y = null, z = null;
        Float yaw = null, pitch = null;
        Player p = null;

        switch (args.length) {
            case 7: {
                try {
                    yaw = parseFloat(args[5]);
                    pitch = parseFloat(args[6]);
                    if (yaw < 0.0F || yaw > 360.0F) {
                        sendYawOOBMessage(sender);
                        return;
                    }
                    if (pitch < -90.0F || pitch > 90.0F) {
                        sendPitchOOBMessage(sender);
                        return;
                    }
                } catch (NumberFormatException e) {
                    sendGenericSyntaxError(sender, this);
                    return;
                }
            }
            case 5: {
                if (sender instanceof ConsoleCommandSender) {
                    w = getWorld(args[4]);
                    if (w == null) {
                        sendWorldMessage(sender, args[4]);
                        return;
                    }
                } else {
                    p = getPlayer(args[4]);
                    if (p == null) {
                        sendPlayerMessage(sender, args[4]);
                        return;
                    }
                }
            }
            case 4: {
                if (sender instanceof ConsoleCommandSender) {
                    try {
                        x = parseDouble(args[1]);
                        y = parseDouble(args[2]);
                        z = parseDouble(args[3]);
                        p = getPlayer(args[0]);
                        if (p == null) {
                            sendPlayerMessage(sender, args[0]);
                            return;
                        }
                        if (yaw == null)
                            yaw = 0.0F;
                        if (pitch == null)
                            pitch = 0.0F;
                        if (w == null)
                            w = getWorlds().get(0);

                        Location l = new Location(w, x, y, z, yaw, pitch);
                        get(p).setHome(l);
                        sendHomeMessage(sender, l, p);
                        return;
                    } catch (NumberFormatException e) {
                        sendConsoleSyntaxError(sender, this);
                        return;
                    }
                } else {
                    w = getWorld(args[3]);
                    if (w == null) {
                        sendWorldMessage(sender, args[3]);
                        return;
                    }
                }
            }
            case 3: {
                if (sender instanceof Player) {
                    try {
                        x = parseDouble(args[0]);
                        y = parseDouble(args[1]);
                        z = parseDouble(args[2]);
                    } catch (NumberFormatException e) {
                        sendSyntaxError(sender, this);
                        return;
                    }
                }
            }
            case 0: {
                assert sender instanceof Player;

                if (p == null)
                    p = (Player) sender;
                if (yaw == null)
                    yaw = p.getLocation().getYaw();
                if (pitch == null)
                    pitch = p.getLocation().getPitch();
                if (x == null)
                    x = p.getLocation().getX();
                if (y == null)
                    y = p.getLocation().getY();
                if (z == null)
                    z = p.getLocation().getZ();
                if (w == null)
                    w = p.getWorld();

                Location l = new Location(w, x, y, z, yaw, pitch);
                get(p).setHome(l);
                sendHomeMessage(sender, l, p);
                return;
            }
            default: {
                sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return args.length >= 5 && getPlayer(args[4]) != null && !getPlayer(args[4]).equals(sender) ? permission + ".others" : permission;
    }

}
