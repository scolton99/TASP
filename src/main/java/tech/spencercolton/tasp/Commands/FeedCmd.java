package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;
import tech.spencercolton.tasp.Util.Message;

/**
 * @author Spencer Colton
 */
public class FeedCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/feed [player] [amount]";

    public static final String name = "feed";

    @Getter
    private static final String permission = "tasp.feed";

    @Getter
    private static final String consoleSyntax = "/feed <player> [amount]";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if((args.length == 0 || args.length > 2) && sender instanceof ConsoleCommandSender) {
            Command.sendConsoleSyntaxError(sender, this);
            return;
        }

        Integer amount = null;
        Player p = null;
        switch(args.length) {
            case 2: {
                try {
                    amount = Integer.parseInt(args[1]);
                    if (amount < 0) {
                        Message.Feed.Error.sendNegativeMessage(sender);
                    }
                } catch (NumberFormatException e) {
                    Command.sendGenericSyntaxError(sender, this);
                }
            }
            case 1: {
                p = Bukkit.getPlayer(args[0]);
                if (p == null) {
                    Command.sendPlayerMessage(sender, args[0]);
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

                if(p.equals(sender))
                    Message.Feed.sendFedMessage(sender, amount / 2.0F);
                else
                    Message.Feed.sendFedMessage(sender, amount / 2.0F, p);
                return;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return (args.length == 2 && !sender.equals(Bukkit.getPlayer(args[1]))) ? permission + ".others" : permission;
    }

}
