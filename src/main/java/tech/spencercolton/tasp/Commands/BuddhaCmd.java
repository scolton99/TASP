package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;

import static org.bukkit.Bukkit.getPlayer;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Entity.Person.get;
import static tech.spencercolton.tasp.Util.Message.Buddha.sendBuddhaMessage;

/**
 * @author Spencer Colton
 */
public class BuddhaCmd extends TASPCommand {

    @Getter
    private final String syntax = "/buddha [player]";

    @Getter
    private static final String name = "buddha";

    @Getter
    private final String permission = "tasp.buddha";

    @Getter
    private final String consoleSyntax = "/buddha <player>";

    @Override
    public void execute(CommandSender sender, String[] args) {
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
                assert p != null;

                p.setBuddha(!p.isBuddha());
                sendBuddhaMessage(sender, p.isBuddha(), p.getPlayer());
                return;
            }
            default: {
                sendGenericSyntaxError(sender, this);
            }
        }
    }
}
