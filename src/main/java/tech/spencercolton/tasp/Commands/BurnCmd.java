package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.Message;

/**
 * @author Spencer Colton
 */
public class BurnCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/burn <player> [time]";

    public static final String name = "burn";

    @Getter
    private static final String permission = "tasp.burn";

    @Getter
    private static final String consoleSyntax = syntax;

    private static final int TICKS_PER_SECOND = 20;
    private static final int DEFAULT = Config.getInt("default-burn-time") * TICKS_PER_SECOND;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0 || args.length > 2) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if(p == null) {
            Command.sendPlayerMessage(sender, args[0]);
            return;
        }

        int x;
        if(args.length == 2) {
            try {
                x = Integer.parseInt(args[1]) * TICKS_PER_SECOND;
                if(x < 0) {
                    sender.sendMessage(Config.err() + "Amount must be positive.");
                    return;
                }
            } catch(NumberFormatException e) {
                Command.sendSyntaxError(sender, this);
                return;
            }
        } else {
            x = DEFAULT;
        }

        p.setFireTicks(x + p.getFireTicks());

        Message.Burn.sendFireMessage(sender, x / TICKS_PER_SECOND, p);
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return (args.length >= 1 && !Bukkit.getPlayer(args[0]).equals(sender)) ? permission + ".others" : permission;
    }

}
