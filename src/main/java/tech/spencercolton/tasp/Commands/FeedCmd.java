package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static java.lang.Integer.*;
import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Util.Message.Feed.*;

/**
 * @author Spencer Colton
 */
public class FeedCmd extends TASPCommand {

    @Getter
    private final String syntax = "/feed [player] [amount]";

    @Getter
    private static final String name = "feed";

    @Getter
    private final String permission = "tasp.feed";

    @Getter
    private final String consoleSyntax = "/feed <player> [amount]";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if ((args.length == 0 || args.length > 2) && sender instanceof ConsoleCommandSender) {
            sendConsoleSyntaxError(sender, this);
            return;
        }

        Integer amount = null;
        Player p = null;
        switch (args.length) {
            case 2: {
                try {
                    amount = parseInt(args[1]);
                    if (amount < 0) {
                        sendNegativeMessage(sender);
                    }
                } catch (NumberFormatException e) {
                    sendGenericSyntaxError(sender, this);
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
                if (p == null) {
                    p = (Player) sender;
                }
                if (amount == null) {
                    amount = 20 - p.getFoodLevel();
                }

                if (amount > (20 - p.getFoodLevel()))
                    amount = 20 - p.getFoodLevel();

                p.setFoodLevel(p.getFoodLevel() + amount);
                p.setSaturation(20.0F);

                sendFedMessage(sender, amount / 2.0F, p.getPlayer());
                return;
            }
            default: {
                sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return (args.length == 2 && !sender.equals(getPlayer(args[1]))) ? permission + ".others" : permission;
    }

}
