package tech.spencercolton.tasp.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReplyCmd extends TASPCommand {

    public static final String name = "reply";
    public static final String permission = "tasp.msg";
    public static final String syntax = "/reply <message>";
    public static final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length == 0) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        CommandSender last;

        if(sender instanceof ConsoleCommandSender) {
            last = MessageCmd.consoleLast;
        } else {
            last = Person.get((Player)sender).getLastMessaged();
        }

        if(last == null) {
            sendNoLastMessage(sender);
            return;
        }

        List<String> argz = new ArrayList<>(Arrays.asList(args));
        argz.add(0, last.getName());
        new MessageCmd().execute(sender, argz.toArray(new String[argz.size()]));
    }

    private void sendNoLastMessage(CommandSender sender) {
        sender.sendMessage(Config.err() + "You cannot reply because you have not been messaged nor have you messaged anyone.");
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("r");
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
