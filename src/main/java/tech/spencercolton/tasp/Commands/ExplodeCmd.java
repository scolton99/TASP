package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static java.lang.Boolean.*;
import static java.lang.Float.*;
import static tech.spencercolton.tasp.Commands.Command.*;

/**
 * @author Spencer Colton
 */
public class ExplodeCmd extends TASPCommand {

    @Getter
    private final String syntax = "/explode [power] [breakblocks]";

    @Getter
    private static final String name = "explode";

    @Getter
    private final String permission = "tasp.explode";

    @Getter
    private final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sendConsoleError(sender);
            return;
        }

        float z = 4.0F;
        boolean b = true;
        switch (args.length) {
            case 2:
                b = parseBoolean(args[1]);
            case 1:
                try {
                    float g = parseFloat(args[0]);
                    z *= g;
                } catch (NumberFormatException e) {
                    sendSyntaxError(sender, this);
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
                sendSyntaxError(sender, this);
        }
    }

}
