package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Message;

import java.util.Set;

/**
 * @author Spencer Colton
 */
public class ShockCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/shock [player]";

    public static final String name = "shock";

    @Getter
    private static final String permission = "tasp.shock";

    @Getter
    private static final String consoleSyntax = "/shock <player>";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender && args.length != 1) {
            Command.sendConsoleSyntaxError(sender, this);
            return;
        }

        Location l = null;
        Player p = null;
        switch(args.length) {
            case 1: {
                p = Bukkit.getPlayer(args[0]);
                if (p == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }
                l = p.getLocation();
            }
            case 0: {
                if(p == null)
                    p = (Player)sender;
                if(l == null)
                    l = p.getTargetBlock((Set<Material>)null, 1000).getLocation();
                l.getWorld().strikeLightning(l);
                Message.Shock.sendShockMessage(sender, p);
                return;
            }
            default: {
                Command.sendSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return (args.length == 1 && !Bukkit.getPlayer(args[0]).equals(sender)) ? permission + ".others" : permission;
    }

}
