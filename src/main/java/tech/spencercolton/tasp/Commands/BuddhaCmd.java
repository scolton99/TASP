package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.M;
import tech.spencercolton.tasp.Util.Message;

/**
 * @author Spencer Colton
 */
public class BuddhaCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/buddha [player]";

    public static final String name = "buddha";

    @Getter
    private static final String permission = "tasp.buddha";

    @Getter
    private static final String consoleSyntax = "/buddha <player>";

    @Override
    public void execute(CommandSender sender, String[] args) {
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
                if(p == null)
                    p = Person.get((Player)sender);
                assert p != null;

                p.setBuddha(!p.isBuddha());
                Message.Buddha.sendBuddhaMessage(sender, p.isBuddha(), p.getPlayer());
                return;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
            }
        }
    }
}
