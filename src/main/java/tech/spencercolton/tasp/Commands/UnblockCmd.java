package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

public class UnblockCmd extends TASPCommand {

    public static final String name = "unblock";
    private static final String syntax = "/unblock <player>";
    private static final String consoleSyntax = null;
    private static final String permission = "tasp.block";

    @Override
    public void execute(CommandSender sender, String... args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError((ConsoleCommandSender)sender);
            return;
        }

        if(args.length != 1) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if(p == null) {
            Command.sendPlayerMessage(sender, args[0]);
            return;
        }

        Person ps = Person.get(p);
        Person pa = Person.get((Player)sender);

        if(pa.isPlayerBlocked(ps))
            pa.unblockPlayer(ps);
        else {
            this.sendNotBlockedMessage(sender, ps);
            return;
        }


        this.sendUnblockedMessage(sender, ps);
    }

    private void sendUnblockedMessage(CommandSender sender, Person p) {
        if(Command.messageEnabled("unblock"))
            sender.sendMessage(M.m("command-message-text.unblock", p.getPlayer().getDisplayName()));
        if(Command.messageEnabled("unblock-r"))
            p.getPlayer().sendMessage(M.m("command-message-text.unblock-r", sender.getName()));
    }

    private void sendNotBlockedMessage(CommandSender sender, Person p) {
        sender.sendMessage(Config.err() + p.getPlayer().getDisplayName() + " is not blocked.");
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
