package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Util.Message.Shock.*;

/**
 * @author Spencer Colton
 */
public class ShockCmd extends TASPCommand {

    @Getter
    private final String syntax = "/shock [player]";

    @Getter
    private static final String name = "shock";

    @Getter
    private final String permission = "tasp.shock";

    @Getter
    private final String consoleSyntax = "/shock <player>";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ConsoleCommandSender && args.length != 1) {
            sendConsoleSyntaxError(sender, this);
            return;
        }

        Location l = null;
        Player p = null;
        switch (args.length) {
            case 1: {
                p = getPlayer(args[0]);
                if (p == null) {
                    sendPlayerMessage(sender, args[0]);
                    return;
                }
                l = p.getLocation();
            }
            case 0: {
                if (p == null)
                    p = (Player) sender;
                if (l == null)
                    l = p.getTargetBlock((Set<Material>) null, 1000).getLocation();
                l.getWorld().strikeLightning(l);
                sendShockMessage(sender, p);
                return;
            }
            default: {
                sendSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return (args.length == 1 && !getPlayer(args[0]).equals(sender)) ? permission + ".others" : permission;
    }

}
