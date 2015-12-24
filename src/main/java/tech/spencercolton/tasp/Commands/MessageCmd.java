package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
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

        String msg = "";
        List<String> arg = Arrays.asList(args);

        for(int i = 1; i < arg.size(); i++) {
            msg += arg.get(i);
            if(!(i + 1 >= arg.size()))
                msg += " ";
        }

        Bukkit.getServer().getPluginManager().callEvent(new PersonSendMessageEvent(sender, p, msg));

    }

    public static void sendBlockedMessage(CommandSender s, String name) {
        s.sendMessage(Config.err() + name + " has blocked you.");
    }

    public static void sendYouBlockedMessage(CommandSender s, String name) {
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
