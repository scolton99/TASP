package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Config;

import java.io.Console;
import java.util.Arrays;
import java.util.List;

public class MessageCmd extends TASPCommand {

    public static final String syntax = "/msg <player> <message>";
    public static final String consoleSyntax = syntax;
    public static final String permission = "tasp.msg";
    public static final String name = "msg";

    public static CommandSender consoleLast;

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length < 2) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        CommandSender p;
        if (args[0].equalsIgnoreCase("console"))
            p = Bukkit.getConsoleSender();
        else
            p = Bukkit.getPlayer(args[0]);

        if (p == null) {
            Command.sendPlayerMessage(sender, args[0]);
            return;
        }

        if (!(p instanceof ConsoleCommandSender) && !(sender instanceof ConsoleCommandSender)) {
            Person p2 = Person.get((Player)p);
            Person p1 = Person.get((Player) sender);

            if (p1.isPlayerBlocked(p2)) {
                sendBlockedMessage(sender, p2.getPlayer().getDisplayName());
                return;
            }
        }

        String msg = Config.c1() + "Message: " + Config.c2();
        List<String> arg = Arrays.asList(args);

        for(int i = 1; i < arg.size(); i++) {
            msg += arg.get(i);
            if(!((i + 1) >= arg.size()))
                msg += " ";
        }

        p.sendMessage(Config.c1() + "From: " + Config.c2() + sender.getName());
        p.sendMessage(msg);

        sender.sendMessage(Config.c1() + "To: " + Config.c2() + p.getName());
        sender.sendMessage(msg);

        if(sender instanceof ConsoleCommandSender) {
            consoleLast = p;
        } else {
            Person px = Person.get((Player)sender);
            px.setLastMessaged(p);
        }

        if(p instanceof ConsoleCommandSender) {
            consoleLast = sender;
        } else {
            Person pz = Person.get((Player)p);
            pz.setLastMessaged(sender);
        }

    }

    private void sendBlockedMessage(CommandSender s, String name) {
        s.sendMessage(Config.err() + name + " has blocked you.");
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("message", "send", "tell");
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public boolean predictOthers(CommandSender sender, String[] s) {
        return false;
    }

    @Override
    public String getName() {
        return name;
    }
}
