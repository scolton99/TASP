package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Config;

public class AFKCmd extends TASPCommand {

    private static final String syntax = "/afk [person]";
    public static final String name = "afk";
    private static final String consoleSyntax = "/afk <person>";
    private static final String permission = "tasp.afk";

    @Override
    public void execute(CommandSender sender, String... args) {
        switch(args.length) {
            case 0:
                if(sender instanceof ConsoleCommandSender) {
                    Command.sendConsoleSyntaxError((ConsoleCommandSender) sender, this);
                    return;
                }

                Player p = (Player)sender;
                Person pers = Person.get(p);

                pers.setAfk(!pers.isAfk());

                if(Config.getBoolean("broadcast-afk"))
                    broadcastAFKMessage(pers);
                return;
            case 1:
                Player tmp = Bukkit.getPlayer(args[0]);

                if(tmp == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }
                Person p2 = Person.get(tmp);

                assert p2 != null;
                sender.sendMessage(Config.c2() + p2.getPlayer().getDisplayName() + Config.c1() + " is " + (!p2.isAfk() ? "not " : "") + "AFK.");
                return;
            default:
                if(sender instanceof ConsoleCommandSender)
                    Command.sendConsoleSyntaxError(sender, this);
                else
                    Command.sendSyntaxError(sender, this);
        }
    }

    public static void broadcastAFKMessage(Person p) {
        if(!Config.getBoolean("broadcast-afk"))
            return;

        if(p.isAfk())
            Bukkit.broadcastMessage(Config.c1() + " * " + Config.c2() + p.getPlayer().getDisplayName() + Config.c1() + " is AFK.");
        else
            Bukkit.broadcastMessage(Config.c1() + " * " + Config.c2() + p.getPlayer().getDisplayName() + Config.c1() + " has returned from being AFK.");
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return args.length == 1 && Bukkit.getPlayer(args[0]) != null ? permission + ".others" : permission;
    }

}
