package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Message;

public class HealCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/heal [person] [amount]";

    public static final String name = "heal";

    @Getter
    private static final String consoleSyntax = "/heal <person> [amount]";

    @Getter
    private static final String permission = "tasp.heal";

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
                    if(amount < 0.0D) {
                        Command.sendNegativeMessage(sender);
                        return;
                    }
                } catch(NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
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
                if (p == null)
                    p = (Player) sender;
                if(amount > p.getMaxHealth() - p.getHealth())
                    amount = p.getMaxHealth() - p.getHealth();
                p.setHealth(p.getHealth() + amount);
                Message.Heal.sendHealedMessage(sender, amount, p);
                return;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return (args.length >= 1 && args.length <= 2 && !sender.equals(Bukkit.getPlayer(args[0]))) ? permission + ".others" : permission;
    }

}
