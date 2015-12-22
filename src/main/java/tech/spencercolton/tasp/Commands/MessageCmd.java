package tech.spencercolton.tasp.Commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Events.PersonSendMessageEvent;
import tech.spencercolton.tasp.Util.Config;

import java.util.Arrays;
import java.util.List;

public class MessageCmd extends TASPCommand {

    private static final String syntax = "/msg <player> <message>";
    private static final String consoleSyntax = syntax;
    private static final String permission = "tasp.msg";
    public static final String name = "msg";

    public static CommandSender consoleLast;

    @Override
    public void execute(CommandSender sender, String... args) {

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

            if (p2.isPlayerBlocked(p1)) {
                this.sendBlockedMessage(sender, p2.getPlayer().getDisplayName());
                return;
            }

            if(p1.isPlayerBlocked(p2)) {
                this.sendYouBlockedMessage(sender, p2.getPlayer().getDisplayName());
                return;
            }
        }

        String msg = "";
        List<String> arg = Arrays.asList(args);

        for(int i = 1; i < arg.size(); i++) {
            msg += arg.get(i);
            if(!(i + 1 >= arg.size()))
                msg += " ";
        }

        p.sendMessage(Config.c3() + "[ <- " + Config.c1() + sender.getName() + Config.c3() + "] " + ChatColor.WHITE + msg);

        sender.sendMessage(Config.c3() + "[ -> " + Config.c1() + p.getName() + Config.c3() + "] " + ChatColor.WHITE + msg);

        Bukkit.getServer().getPluginManager().callEvent(new PersonSendMessageEvent(sender, p, msg));

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

    private void sendYouBlockedMessage(CommandSender s, String name) {
        s.sendMessage(Config.err() + "You have blocked " + name);
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
    public String getName() {
        return name;
    }

}
