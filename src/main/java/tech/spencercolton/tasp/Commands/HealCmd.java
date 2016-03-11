package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static java.lang.Double.*;
import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Util.Message.Heal.*;

public class HealCmd extends TASPCommand {

    @Getter
    private final String syntax = "/heal [person] [amount]";

    @Getter
    private static final String name = "heal";

    @Getter
    private final String consoleSyntax = "/heal <person> [amount]";

    @Getter
    private final String permission = "tasp.heal";

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        if (sender instanceof ConsoleCommandSender && (args.length < 1 || args.length > 2)) {
            sendConsoleSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        Player p = null;
        Double amount = 20.0D;
        switch (args.length) {
            case 2: {
                try {
                    amount = parseDouble(args[1]);
                    if (amount < 0.0D) {
                        sendNegativeMessage(sender);
                        return CommandResponse.FAILURE;
                    }
                } catch (NumberFormatException e) {
                    sendGenericSyntaxError(sender, this);
                }
            }
            case 1: {
                p = getPlayer(args[0]);
                if (p == null) {
                    sendPlayerMessage(sender, args[0]);
                    return CommandResponse.PLAYER;
                }
            }
            case 0: {
                if (p == null)
                    p = (Player) sender;
                if (amount > p.getMaxHealth() - p.getHealth())
                    amount = p.getMaxHealth() - p.getHealth();
                p.setHealth(p.getHealth() + amount);
                sendHealedMessage(sender, amount, p);
                return CommandResponse.SUCCESS;
            }
            default: {
                sendGenericSyntaxError(sender, this);
                return CommandResponse.SYNTAX;
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return (args.length >= 1 && args.length <= 2 && !sender.equals(getPlayer(args[0]))) ? permission + ".others" : permission;
    }

}
