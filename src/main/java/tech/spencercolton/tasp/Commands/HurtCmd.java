package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static java.lang.Double.*;
import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Util.Message.Hurt.*;

/**
 * @author Spencer Colton
 * @since 0.0.3
 */
public class HurtCmd extends TASPCommand {

    @Getter
    private final String syntax = "/hurt [player] [amount]";

    @Getter
    private static final String name = "hurt";

    @Getter
    private final String permission = "tasp.heal";

    @Getter
    private final String consoleSyntax = "/hurt <player> [amount]";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ConsoleCommandSender && (args.length < 1 || args.length > 2)) {
            sendConsoleSyntaxError(sender, this);
            return;
        }

        Player p = null;
        Double amount = 20.0D;
        switch (args.length) {
            case 2: {
                try {
                    amount = parseDouble(args[1]);
                    if (amount < 0) {
                        sendNegativeMessage(sender);
                        return;
                    }
                } catch (NumberFormatException e) {
                    sendGenericSyntaxError(sender, this);
                    return;
                }
            }
            case 1: {
                p = getPlayer(args[0]);
                if (p == null) {
                    sendPlayerMessage(sender, args[0]);
                    return;
                }
            }
            case 0: {
                if (p == null)
                    p = (Player) sender;
                if (amount > p.getHealth() - 0.5D)
                    amount = p.getHealth() - 0.5D;
                p.setHealth(p.getHealth() - amount);
                sendHurtMessage(sender, amount, p);
                break;
            }
            default: {
                sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return (args.length >= 1 && args.length <= 2 && !getPlayer(args[0]).equals(sender)) ? permission + ".others" : permission;
    }

}
