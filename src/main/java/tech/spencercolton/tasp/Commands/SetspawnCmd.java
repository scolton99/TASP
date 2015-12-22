package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import tech.spencercolton.tasp.Util.M;

public class SetspawnCmd extends TASPCommand {

    private static final String syntax = "/setspawn [<x> <y> <z>] [world]";
    private static final String consoleSyntax = "/setspawn <x> <y> <z> <world>";
    public static final String name = "setspawn";
    private static final String permission = "tasp.setspawn";

    @Override
    public void execute(CommandSender sender, String... args) {
        if (sender instanceof ConsoleCommandSender) {
            if (args.length != 4) {
                Command.sendConsoleSyntaxError((ConsoleCommandSender) sender, this);
                return;
            }

            try {
                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);
                int z = Integer.parseInt(args[2]);

                World w = Bukkit.getWorld(args[3]);

                if (w == null) {
                    Command.sendWorldMessage(sender, args[3]);
                    return;
                }

                w.setSpawnLocation(x, y, z);
                this.sendSpawnSetMessage(sender, x, y, z, w.getName());
            } catch (NumberFormatException e) {
                Command.sendConsoleSyntaxError((ConsoleCommandSender) sender, this);
            }
        } else {
            switch (args.length) {
                case 0:
                    Location l = ((Entity) sender).getLocation();
                    World w = ((Entity) sender).getWorld();

                    w.setSpawnLocation(l.getBlockX(), l.getBlockY(), l.getBlockZ());
                    this.sendSpawnSetMessage(sender, l.getBlockX(), l.getBlockY(), l.getBlockZ(), w.getName());
                    break;
                case 3:
                    try {
                        int x = Integer.parseInt(args[0]);
                        int y = Integer.parseInt(args[1]);
                        int z = Integer.parseInt(args[2]);

                        World w2 = ((Entity) sender).getWorld();

                        w2.setSpawnLocation(x, y, z);
                        this.sendSpawnSetMessage(sender, x, y, z, w2.getName());
                    } catch (NumberFormatException e) {
                        Command.sendSyntaxError(sender, this);
                    }
                    break;
                case 4:
                    try {
                        int x = Integer.parseInt(args[0]);
                        int y = Integer.parseInt(args[1]);
                        int z = Integer.parseInt(args[2]);

                        World w2 = Bukkit.getWorld(args[3]);

                        if (w2 == null) {
                            Command.sendWorldMessage(sender, args[3]);
                            return;
                        }

                        w2.setSpawnLocation(x, y, z);
                        this.sendSpawnSetMessage(sender, x, y, z, w2.getName());
                    } catch (NumberFormatException e) {
                        Command.sendSyntaxError(sender, this);
                    }
                    break;
                default:
                    Command.sendSyntaxError(sender, this);
                    break;
            }
        }
    }

    private void sendSpawnSetMessage(CommandSender s, int x, int y, int z, String world) {
        if(Command.messageEnabled(this, false))
            s.sendMessage(M.m("command-message-text.setspawn", Integer.toString(x), Integer.toString(y), Integer.toString(z), world));
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getName() {
        return name;
    }

}
