package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;

import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Entity.Person.*;
import static tech.spencercolton.tasp.Util.Message.Stalker.*;

/**
 * @author Spencer Colton
 */
public class StalkerCmd extends TASPCommand {

    @Getter
    private final String syntax = "/stalker [person]";

    @Getter
    private static final String name = "stalker";

    @Getter
    private final String permission = "tasp.msg.stalk";

    @Getter
    private final String consoleSyntax = "/stalker <person>";

    @Override
    public void execute(CommandSender sender, String... args) {
        if (sender instanceof ConsoleCommandSender && args.length != 1) {
            sendConsoleSyntaxError(sender, this);
            return;
        }

        Person p = null;
        switch (args.length) {
            case 1: {
                p = get(getPlayer(args[0]));
                if (p == null) {
                    sendPlayerMessage(sender, args[0]);
                    return;
                }
            }
            case 0: {
                if (p == null) {
                    assert sender instanceof ConsoleCommandSender;
                    p = get((Player) sender);
                }
                p.setStalker(!p.isStalker());
                sendStalkerMessage(sender, p.isStalker(), p.getPlayer());
                return;
            }
            default: {
                sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender s, String... args) {
        return args.length == 1 && getPlayer(args[0]) != null && !getPlayer(args[0]).equals(s) ? permission + ".others" : permission;
    }

}
