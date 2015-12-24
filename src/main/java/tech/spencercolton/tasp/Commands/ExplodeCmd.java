package tech.spencercolton.tasp.Commands;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * @author Spencer Colton
 */
public class ExplodeCmd extends TASPCommand {

    private static final String syntax = "/explode [power]";
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
        switch(args.length) {
            case 1:
                try {
                    float g = Float.parseFloat(args[0]);
                    z *= g;
                } catch(NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                    return;
                }
            case 0:
                Player p = (Player)sender;
                World w = p.getWorld();
                w.createExplosion(p.getLocation(), z);
                return;
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
