package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;

import static org.bukkit.Bukkit.getPlayer;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Entity.Person.get;
import static tech.spencercolton.tasp.Util.Message.God.sendGodMessage;

public class GodCmd extends TASPCommand {

    @Getter
    private static final String name = "god";

    @Getter
    private final String syntax = "/god [player]";

    @Getter
    private final String consoleSyntax = "/god <player>";

    @Getter
    private final String permission = "tasp.god";

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
                if (p == null)
                    p = get((Player) sender);
                p.setGod(!p.isGod());
                sendGodMessage(sender, p.isGod(), p.getPlayer());
                return;
            }
            default: {
                sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... s) {
        return s.length > 0 && getPlayer(s[0]) != null && !getPlayer(s[0]).equals(sender) ? permission + ".others" : permission;
    }

}
