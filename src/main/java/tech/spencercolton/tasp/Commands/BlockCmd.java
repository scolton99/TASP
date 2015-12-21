package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.M;

public class BlockCmd extends TASPCommand {

    /**
     * String containing the command's name.
     */
    public static final String name = "block";


    /**
     * String containing the command's syntax.
     */
    public static final String syntax = "/block <user>";


    /**
     * String containing the command's console syntax.
     */
    public static final String consoleSyntax = "/block <user>";

    public static final String permission = "tasp.block";

    @Override
    public void execute(CommandSender sender, String[] args) {
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
        pa.blockPlayer(ps);

        sendBlockedMessage(sender, ps);
    }

    private void sendBlockedMessage(CommandSender sender, Person p) {
        if(Command.messageEnabled("block"))
            sender.sendMessage(M.m("command-message-text.block", p.getPlayer().getDisplayName()));
        if(Command.messageEnabled("block-r"))
            p.getPlayer().sendMessage(M.m("command-message-text.block-r", sender.getName()));
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
