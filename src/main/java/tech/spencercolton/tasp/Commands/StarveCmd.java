package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Message;

/**
 *
 * @author Spencer Colton
 * @since 0.0.3
 */
public class StarveCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/starve [player] [amount]";

    public static final String name = "starve";

    @Getter
    private static final String consoleSyntax = "/starve <player> [amount]";

    @Getter
    private static final String permission = "tasp.feed";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender && (args.length < 1 || args.length > 2)) {
            Command.sendConsoleSyntaxError(sender, this);
            return;
        }

        Player p = null;
        Integer amount = null;
        switch (args.length) {
            case 2: {
                try {
                    amount = Integer.parseInt(args[1]);
                    if (amount < 0) {
                        Command.sendNegativeMessage(sender);
                        return;
                    }
                } catch (NumberFormatException e) {
                    Command.sendGenericSyntaxError(sender, this);
                    return;
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
                    assert sender instanceof Player;
                    p = (Player) sender;
                }

                if (amount == null)
                    amount = p.getFoodLevel();
                if (amount > p.getFoodLevel())
                    amount = p.getFoodLevel();

                p.setFoodLevel(p.getFoodLevel() - amount);
                Message.Starve.sendStarvedMessage(sender, amount, p);
                return;
            }
            default: {
                Command.sendConsoleSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return (args.length == 2 && !sender.equals(Bukkit.getPlayer(args[1]))) ? permission + ".others" : permission;
    }

}
