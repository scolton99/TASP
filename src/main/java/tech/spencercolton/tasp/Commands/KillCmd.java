package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * @author Spencer Colton
 */
public class KillCmd extends TASPCommand {

    @Getter
    private final String syntax = "/kill [player]";

    @Getter
    public static final String name = "kill";

    @Getter
    private final String consoleSyntax = "/kill <player>";

    @Getter
    private final String permission = "tasp.kill";

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        if (sender instanceof ConsoleCommandSender && args.length != 1) {
            Command.sendConsoleSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        Player p = null;
        switch (args.length) {
            case 1: {
                p = Bukkit.getPlayer(args[0]);
                if (p == null) {
                    Command.sendGenericSyntaxError(sender, this);
                    return CommandResponse.SYNTAX;
                }
            }
            case 0: {
                if (p == null) {
                    assert sender instanceof Player;
                    p = (Player) sender;
                }

                p.damage(10000.0D);
                return CommandResponse.SUCCESS;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
                return CommandResponse.SYNTAX;
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return (args.length == 1 && !sender.equals(Bukkit.getPlayer(args[0]))) ? permission + ".others" : permission;
    }

}
