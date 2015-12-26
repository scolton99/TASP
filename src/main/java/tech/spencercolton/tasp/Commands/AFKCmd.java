package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.Message;

public class AFKCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/afk [person]";

    public static final String name = "afk";

    @Getter
    private static final String consoleSyntax = "/afk <person>";

    @Getter
    private static final String permission = "tasp.afk";

    @Override
    public void execute(CommandSender sender, String... args) {
        if(sender instanceof ConsoleCommandSender && args.length != 0) {
            Command.sendConsoleSyntaxError(sender, this);
        }

        switch(args.length) {
            case 1: {
                Person p = Person.get(Bukkit.getPlayer(args[0]));
                if (p == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }
                sender.sendMessage(Config.c2() + p.getPlayer().getDisplayName() + Config.c1() + " is " + (!p.isAfk() ? "not " : "") + "AFK.");
            }
            case 0: {
                Person pers = Person.get((Player)sender);
                pers.setAfk(!pers.isAfk());
                Message.AFK.broadcastAFKMessage(pers.getPlayer());
                return;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return args.length == 1 && Bukkit.getPlayer(args[0]) != null ? permission + ".others" : permission;
    }

}
