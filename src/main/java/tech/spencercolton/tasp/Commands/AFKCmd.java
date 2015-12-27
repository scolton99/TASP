package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;

import static org.bukkit.Bukkit.getPlayer;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Entity.Person.get;
import static tech.spencercolton.tasp.Util.Config.c1;
import static tech.spencercolton.tasp.Util.Config.c2;
import static tech.spencercolton.tasp.Util.Message.AFK.broadcastAFKMessage;

public class AFKCmd extends TASPCommand {

    @Getter
    private final String syntax = "/afk [person]";

    @Getter
    private static final String name = "afk";

    @Getter
    private final String consoleSyntax = "/afk <person>";

    @Getter
    private final String permission = "tasp.afk";

    @Override
    public void execute(CommandSender sender, String... args) {
        if (sender instanceof ConsoleCommandSender && args.length != 0) {
            sendConsoleSyntaxError(sender, this);
        }

        switch (args.length) {
            case 1: {
                Person p = get(getPlayer(args[0]));
                if (p == null) {
                    sendPlayerMessage(sender, args[0]);
                    return;
                }
                sender.sendMessage(c2() + p.getPlayer().getDisplayName() + c1() + " is " + (!p.isAfk() ? "not " : "") + "AFK.");
            }
            case 0: {
                Person pers = get((Player) sender);
                pers.setAfk(!pers.isAfk());
                broadcastAFKMessage(pers.getPlayer());
                return;
            }
            default: {
                sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return args.length == 1 && getPlayer(args[0]) != null ? permission + ".others" : permission;
    }

}
