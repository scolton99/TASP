package tech.spencercolton.tasp.Commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * @author Spencer Colton
 */
public class ExplodeCmd extends TASPCommand {

    private static final String syntax = "/explode [power] [breakblocks]";
    public static final String name = "explode";
    private static final String permission = "tasp.explode";
    private static final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError((ConsoleCommandSender)sender);
            return;
        }

        float z = 4.0F;
        boolean b = true;
        switch(args.length) {
            case 2:
                b = Boolean.parseBoolean(args[1]);
            case 1:
                try {
                    float g = Float.parseFloat(args[0]);
                    z *= g;
                } catch(NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                    return;
                }
            case 0: {
                Player p = (Player) sender;
                World w = p.getWorld();
                Location l = p.getLocation();
                w.createExplosion(l.getX(), l.getY(), l.getZ(), z, false, b);
                return;
            }
            default:
                Command.sendSyntaxError(sender, this);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getConsoleSyntax() {
        return consoleSyntax;
    }


}
