package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.M;
import tech.spencercolton.tasp.Util.Message;

public class SetspawnCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/setspawn [<x> <y> <z>] [world]";

    @Getter
    private static final String consoleSyntax = "/setspawn <x> <y> <z> <world>";

    public static final String name = "setspawn";

    @Getter
    private static final String permission = "tasp.setspawn";

    @Override
    public void execute(CommandSender sender, String... args) {
        assert (sender instanceof ConsoleCommandSender || sender instanceof Player);

        if(sender instanceof ConsoleCommandSender && args.length != 4) {
            Command.sendConsoleSyntaxError(sender, this);
            return;
        }

        Integer x = null, y = null, z = null;
        World w = null;
        switch(args.length) {
            case 4: {
                try {
                    w = Bukkit.getWorld(args[3]);
                    x = Integer.parseInt(args[0]);
                    y = Integer.parseInt(args[1]);
                    z = Integer.parseInt(args[2]);
                    if(w == null) {
                        Command.sendWorldMessage(sender, args[3]);
                        return;
                    }
                    if (sender instanceof ConsoleCommandSender) {
                        w.setSpawnLocation(x, y, z);
                        Message.Setspawn.sendSpawnSetMessage(sender, x, y, z, w.getName());
                        return;
                    }
                } catch(NumberFormatException e) {
                    Command.sendGenericSyntaxError(sender, this);
                    return;
                }
            }
            case 3: {
                assert sender instanceof Player;
                try {
                    x = Integer.parseInt(args[0]);
                    y = Integer.parseInt(args[1]);
                    z = Integer.parseInt(args[2]);
                } catch(NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                    return;
                }
            }
            case 0: {
                assert sender instanceof Player;
                w = ((Player)sender).getWorld();
                if(x == null)
                    x = ((Player)sender).getLocation().getBlockX();
                if(y == null)
                    y = ((Player)sender).getLocation().getBlockY();
                if(z == null)
                    z = ((Player)sender).getLocation().getBlockZ();

                w.setSpawnLocation(x, y, z);
                Message.Setspawn.sendSpawnSetMessage(sender, x, y, z, w.getName());
                return;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
                return;
            }
        }
    }

}
