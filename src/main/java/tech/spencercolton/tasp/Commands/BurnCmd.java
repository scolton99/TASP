package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static java.lang.Integer.*;
import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Util.Config.*;
import static tech.spencercolton.tasp.Util.Message.Burn.*;

/**
 * @author Spencer Colton
 */
public class BurnCmd extends TASPCommand {

    @Getter
    private final String syntax = "/burn <player> [time]";

    @Getter
    private static final String name = "burn";

    @Getter
    private final String permission = "tasp.burn";

    @Getter
    private final String consoleSyntax = syntax;

    private static final int TICKS_PER_SECOND = 20;
    private static final int DEFAULT = getInt("default-burn-time") * TICKS_PER_SECOND;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0 || args.length > 2) {
            sendSyntaxError(sender, this);
            return;
        }

        Player p = getPlayer(args[0]);
        if (p == null) {
            sendPlayerMessage(sender, args[0]);
            return;
        }

        int x;
        if (args.length == 2) {
            try {
                x = parseInt(args[1]) * TICKS_PER_SECOND;
                if (x < 0) {
                    sender.sendMessage(err() + "Amount must be positive.");
                    return;
                }
            } catch (NumberFormatException e) {
                sendSyntaxError(sender, this);
                return;
            }
        } else {
            x = DEFAULT;
        }

        p.setFireTicks(x + p.getFireTicks());

        sendFireMessage(sender, x / TICKS_PER_SECOND, p);
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return (args.length >= 1 && !getPlayer(args[0]).equals(sender)) ? permission + ".others" : permission;
    }

}
