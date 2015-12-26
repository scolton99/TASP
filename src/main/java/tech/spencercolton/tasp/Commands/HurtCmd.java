package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Message;

/**
 * @author Spencer Colton
 * @since 0.0.3
 */
public class HurtCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/hurt [player] [amount]";

    public static final String name = "hurt";

    @Getter
    private static final String permission = "tasp.heal";

    @Getter
    private static final String consoleSyntax = "/hurt <player> [amount]";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender && (args.length < 1 || args.length > 2)) {
            Command.sendConsoleSyntaxError(sender, this);
            return;
        }

        Player p = null;
        Double amount = 20.0D;
        switch(args.length) {
            case 2: {
                try {
                    amount = Double.parseDouble(args[1]);
                    if(amount < 0) {
                        Command.sendNegativeMessage(sender);
                        return;
                    }
                } catch(NumberFormatException e) {
                    Command.sendGenericSyntaxError(sender, this);
                    return;
                }
            }
            case 1: {
                p = Bukkit.getPlayer(args[0]);
                if(p == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }
            }
            case 0: {
                if(p == null)
                    p = (Player)sender;
                if(amount > p.getHealth() - 0.5D)
                    amount = p.getHealth() - 0.5D;
                p.setHealth(p.getHealth() - amount);
                Message.Hurt.sendHurtMessage(sender, amount, p);
                break;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
            }
        }
    }

}
