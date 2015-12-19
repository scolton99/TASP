package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

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

    public static final String syntax = "/sethome [<x> <y> <z>] [world] [player] [<yaw> <pitch>]";
    public static final String consoleSyntax = "/sethome <player> <x> <y> <z> [world] [<yaw> <pitch>]";
    public static final String name = "sethome";
    public static final String permission = "tasp.sethome";

    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            if (args.length < 4 || args.length > 7) {
                Command.sendConsoleSyntaxError((ConsoleCommandSender) sender, this);
            }

            switch (args.length) {
                case 4:
                    try {
                        double x = Double.parseDouble(args[1]);
                        double y = Double.parseDouble(args[2]);
                        double z = Double.parseDouble(args[3]);
                        Player p = Bukkit.getPlayer(args[0]);

                        if (p == null) {
                            sendPlayerNotFoundMessage(sender, args[0]);
                            return;
                        }

                        Location l = new Location(p.getWorld(), x, y, z);
                        Person.get(p).setHome(l);
                        sendHomeMessage(sender, Person.get(p).getHome(), args[0]);
                        return;
                    } catch (NumberFormatException e) {
                        Command.sendConsoleSyntaxError((ConsoleCommandSender) sender, this);
                        return;
                    }
                case 5:
                    try {
                        double x = Double.parseDouble(args[1]);
                        double y = Double.parseDouble(args[2]);
                        double z = Double.parseDouble(args[3]);
                        World w = Bukkit.getWorld(args[4]);
                        Player p = Bukkit.getPlayer(args[0]);

                        if (p == null) {
                            sendPlayerNotFoundMessage(sender, args[0]);
                            return;
                        }

                        if (w == null) {
                            sendWorldNotFoundMessage(sender, args[4]);
                            return;
                        }

                        Location l = new Location(w, x, y, z);
                        Person.get(p).setHome(l);
                        sendHomeMessage(sender, Person.get(p).getHome(), args[0]);
                        return;
                    } catch (NumberFormatException e) {
                        Command.sendConsoleSyntaxError((ConsoleCommandSender) sender, this);
                        return;
                    }
                case 7:
                    try {
                        double x = Double.parseDouble(args[1]);
                        double y = Double.parseDouble(args[2]);
                        double z = Double.parseDouble(args[3]);
                        World w = Bukkit.getWorld(args[4]);
                        float yaw = Float.parseFloat(args[5]);
                        float pitch = Float.parseFloat(args[6]);
                        Player p = Bukkit.getPlayer(args[0]);

                        if (p == null) {
                            sendPlayerNotFoundMessage(sender, args[0]);
                            return;
                        }

                        if (w == null) {
                            sendWorldNotFoundMessage(sender, args[4]);
                            return;
                        }

                        Location l = new Location(w, x, y, z, yaw, pitch);
                        Person.get(p).setHome(l);
                        sendHomeMessage(sender, Person.get(p).getHome(), args[0]);
                        return;
                    } catch (NumberFormatException e) {
                        Command.sendConsoleSyntaxError((ConsoleCommandSender) sender, this);
                        return;
                    }
                default:
                    Command.sendConsoleSyntaxError((ConsoleCommandSender) sender, this);
                    return;
            }
        }

        if(args.length > 7) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        switch(args.length) {
            case 0:
                Person p = Person.get((Player)sender);
                p.setHome(((Player)sender).getLocation());

                sendHomeMessage(sender, p.getHome());
                return;
            case 3:
                Person p2 = Person.get((Player)sender);
                try {
                    Double x = Double.parseDouble(args[0]);
                    Double y = Double.parseDouble(args[1]);
                    Double z = Double.parseDouble(args[2]);
                    p2.setHome(new Location(((Player) sender).getWorld(), x, y, z));

                    sendHomeMessage(sender, p2.getHome());
                    return;
                } catch(NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                    return;
                }
            case 4:
                Person p3 = Person.get((Player)sender);
                try {
                    Double x = Double.parseDouble(args[0]);
                    Double y = Double.parseDouble(args[1]);
                    Double z = Double.parseDouble(args[2]);
                    World w = Bukkit.getWorld(args[3]);

                    if(w == null) {
                        sendWorldNotFoundMessage(sender, args[3]);
                        return;
                    }

                    p3.setHome(new Location(w, x, y, z));

                    sendHomeMessage(sender, p3.getHome());
                    return;
                } catch(NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                    return;
                }
            case 5:
                Person p4 = Person.get((Player)sender);
                try {
                    Double x = Double.parseDouble(args[0]);
                    Double y = Double.parseDouble(args[1]);
                    Double z = Double.parseDouble(args[2]);
                    World w = Bukkit.getWorld(args[3]);
                    Player v = Bukkit.getPlayer(args[4]);

                    if(w == null) {
                        sendWorldNotFoundMessage(sender, args[3]);
                        return;
                    }

                    if(v == null) {
                        sendPlayerNotFoundMessage(sender, args[4]);
                    }

                    Person.get(v).setHome(new Location(w, x, y, z));

                    assert v != null;

                    sendHomeMessage(sender, p4.getHome(), v.getName());
                    return;
                } catch(NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                    return;
                }
            case 7:
                Person p5 = Person.get((Player)sender);
                try {
                    Double x = Double.parseDouble(args[0]);
                    Double y = Double.parseDouble(args[1]);
                    Double z = Double.parseDouble(args[2]);
                    World w = Bukkit.getWorld(args[3]);
                    Player v = Bukkit.getPlayer(args[4]);
                    Float yaw = Float.parseFloat(args[5]);
                    Float pitch = Float.parseFloat(args[6]);

                    if(w == null) {
                        sendWorldNotFoundMessage(sender, args[3]);
                        return;
                    }

                    if(v == null) {
                        sendPlayerNotFoundMessage(sender, args[4]);
                    }

                    Person.get(v).setHome(new Location(w, x, y, z, yaw, pitch));

                    assert v != null;

                    sendHomeMessage(sender, p5.getHome(), v.getName());
                    return;
                } catch(NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                    return;
                }
            default:
                Command.sendSyntaxError(sender, this);
                break;
        }
    }

    private void sendHomeMessage(CommandSender s, Location l) {
        if(Command.messageEnabled(this, false))
            s.sendMessage(M.m("command-message-text.sethome", Integer.toString((int)l.getX()), Integer.toString((int)l.getY()), Integer.toString((int)l.getZ())));
    }

    private void sendHomeMessage(CommandSender s, Location l, String o) {
        Player p = Bukkit.getPlayer(o);

        assert p != null;

        if(p.equals(s)) {
            sendHomeMessage(s, l);
            return;
        }

        if(Command.messageEnabled(this, false))
            s.sendMessage(M.m("command-message-text.sethome-others-s", Integer.toString((int)l.getX()), Integer.toString((int)l.getY()), Integer.toString((int)l.getZ()), p.getDisplayName()));
        if(Command.messageEnabled(this, true))
            p.sendMessage(M.m("command-message-text.sethome-others-r", Integer.toString((int)l.getX()), Integer.toString((int)l.getY()), Integer.toString((int)l.getZ()), s.getName()));
    }

    private void sendPlayerNotFoundMessage(CommandSender s, String p) {
        s.sendMessage(Config.err() + "Couldn't find player \"" + p + "\"");
    }

    private void sendWorldNotFoundMessage(CommandSender s, String w) {
        s.sendMessage(Config.err() + "Couldn't find world \"" + w + "\"");
    }


    public boolean predictOthers(CommandSender sender, String[] args) {
        return (!(sender instanceof ConsoleCommandSender)) && args.length >= 5 && Bukkit.getPlayer(args[4]) != null && !Bukkit.getPlayer(args[4]).equals(sender);
    }

    public String getSyntax() {
        return syntax;
    }

    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    public String getPermission() {
        return permission;
    }

    public String getName() {
        return name;
    }

}
