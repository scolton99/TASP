package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Message;

/**
 * The {@link TASPCommand} object containing the runtime information for the {@code sethome} command.
 *
 * <table summary="Properties">
 *     <tr>
 *         <th style="font-weight:bold;">Property</th>
 *         <th style="font-weight:bold;">Value</th>
 *     </tr>
 *     <tr>
 *         <td>
 *             Name
 *         </td>
 *         <td>
 *             {@value name}
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *             Permission
 *         </td>
 *         <td>
 *             {@value permission}
 *             <br>
 *             {@code tasp.sethome.others}
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *             Syntax
 *         </td>
 *         <td>
 *             /sethome [player]
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *             Console Syntax
 *         </td>
 *         <td>
 *             null
 *         </td>
 *     </tr>
 * </table>
 */
public class SethomeCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/sethome [<x> <y> <z>] [world] [player] [<yaw> <pitch>]";

    @Getter
    private static final String consoleSyntax = "/sethome <player> <x> <y> <z> [world] [<yaw> <pitch>]";

    public static final String name = "sethome";

    @Getter
    private static final String permission = "tasp.sethome";

    @Override
    public void execute(CommandSender sender, String... args) {
        assert (sender instanceof ConsoleCommandSender || sender instanceof Player);

        if(sender instanceof ConsoleCommandSender && (args.length < 4 || args.length > 7 || args.length == 6)) {
            Command.sendConsoleSyntaxError(sender, this);
            return;
        }

        if(sender instanceof Player && (args.length > 7 || args.length == 6)) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        World w = null;
        Double x = null, y = null, z = null;
        Float yaw = null, pitch = null;
        Player p = null;

        switch(args.length) {
            case 7: {
                try {
                    yaw = Float.parseFloat(args[5]);
                    pitch = Float.parseFloat(args[6]);
                    if (yaw < 0.0F || yaw > 360.0F) {
                        Message.Sethome.Error.sendYawOOBMessage(sender);
                        return;
                    }
                    if (pitch < -90.0F || pitch > 90.0F) {
                        Message.Sethome.Error.sendPitchOOBMessage(sender);
                        return;
                    }
                } catch(NumberFormatException e) {
                    Command.sendGenericSyntaxError(sender, this);
                    return;
                }
            }
            case 5: {
                if(sender instanceof ConsoleCommandSender) {
                    w = Bukkit.getWorld(args[4]);
                    if (w == null) {
                        Command.sendWorldMessage(sender, args[4]);
                        return;
                    }
                } else {
                    p = Bukkit.getPlayer(args[4]);
                    if (p == null) {
                        Command.sendPlayerMessage(sender, args[4]);
                        return;
                    }
                }
            }
            case 4: {
                if(sender instanceof ConsoleCommandSender) {
                    try {
                        x = Double.parseDouble(args[1]);
                        y = Double.parseDouble(args[2]);
                        z = Double.parseDouble(args[3]);
                        p = Bukkit.getPlayer(args[0]);
                        if (p == null) {
                            Command.sendPlayerMessage(sender, args[0]);
                            return;
                        }
                        if(yaw == null)
                            yaw = 0.0F;
                        if(pitch == null)
                            pitch = 0.0F;
                        if(w == null)
                            w = Bukkit.getWorlds().get(0);

                        Location l = new Location(w, x, y, z, yaw, pitch);
                        Person.get(p).setHome(l);
                        Message.Sethome.sendHomeMessage(sender, l, p);
                        return;
                    } catch(NumberFormatException e) {
                        Command.sendConsoleSyntaxError(sender, this);
                        return;
                    }
                } else {
                    w = Bukkit.getWorld(args[3]);
                    if(w == null) {
                        Command.sendWorldMessage(sender, args[3]);
                        return;
                    }
                }
            }
            case 3: {
                if(sender instanceof Player) {
                    try {
                        x = Double.parseDouble(args[0]);
                        y = Double.parseDouble(args[1]);
                        z = Double.parseDouble(args[2]);
                    } catch (NumberFormatException e) {
                        Command.sendSyntaxError(sender, this);
                        return;
                    }
                }
            }
            case 0: {
                assert sender instanceof Player;

                if(p == null)
                    p = (Player)sender;
                if(yaw == null)
                    yaw = p.getLocation().getYaw();
                if(pitch == null)
                    pitch = p.getLocation().getPitch();
                if(x == null)
                    x = p.getLocation().getX();
                if(y == null)
                    y = p.getLocation().getY();
                if(z == null)
                    z = p.getLocation().getZ();
                if(w == null)
                    w = p.getWorld();

                Location l = new Location(w, x, y, z, yaw, pitch);
                Person.get(p).setHome(l);
                Message.Sethome.sendHomeMessage(sender, l, p);
                return;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return args.length >= 5 && Bukkit.getPlayer(args[4]) != null && !Bukkit.getPlayer(args[4]).equals(sender) ? permission + ".others" : permission;
    }

}
