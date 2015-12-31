package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Util.Message.Fly.*;

public class FlyCmd extends TASPCommand {

    /**
     * String containing the command's name.
     */
    @Getter
    private static final String name = "fly";


    /**
     * String containing the command's syntax.
     */
    @Getter
    private final String syntax = "/fly [user]";


    /**
     * String containing the command's console syntax.
     */
    @Getter
    private final String consoleSyntax = "/fly <user>";

    @Getter
    private final String permission = "tasp.fly";

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String... args) {
        if (sender instanceof ConsoleCommandSender && args.length != 1) {
            sendConsoleSyntaxError(sender, this);
            return;
        }

        Player p = null;
        switch (args.length) {
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
                p.setAllowFlight(true);
                p.setFlying(!p.isFlying());
                sendFlyingMessage(sender, p.isFlying(), p);
                return;
            }
            default: {
                sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return args.length == 1 && getPlayer(args[0]) != null && !getPlayer(args[0]).equals(sender) ? permission + ".others" : permission;
    }

}
