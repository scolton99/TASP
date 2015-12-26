package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Message;

/**
 * @author Spencer Colton
 */
public class StalkerCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/stalker [person]";

    public static final String name = "stalker";

    @Getter
    private static final String permission = "tasp.msg.stalk";

    @Getter
    private static final String consoleSyntax = "/stalker <person>";

    @Override
    public void execute(CommandSender sender, String... args) {
        if(sender instanceof ConsoleCommandSender && args.length != 1) {
            Command.sendConsoleSyntaxError(sender, this);
            return;
        }

        Person p = null;
        switch(args.length) {
            case 1: {
                p = Person.get(Bukkit.getPlayer(args[0]));
                if (p == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }
            }
            case 0: {
                if (p == null) {
                    assert sender instanceof ConsoleCommandSender;
                    p = Person.get((Player) sender);
                }
                p.setStalker(!p.isStalker());
                Message.Stalker.sendStalkerMessage(sender, p.isStalker(), p.getPlayer());
                return;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender s, String... args) {
        return args.length == 1 && Bukkit.getPlayer(args[0]) != null && !Bukkit.getPlayer(args[0]).equals(s) ? permission + ".others" : permission;
    }

}
