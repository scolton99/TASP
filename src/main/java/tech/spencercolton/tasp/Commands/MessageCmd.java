package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import tech.spencercolton.tasp.Events.PersonSendMessageEvent;
import tech.spencercolton.tasp.Util.Config;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;

public class MessageCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/msg <player> <message>";

    @Getter
    private static final String consoleSyntax = syntax;

    @Getter
    private static final String permission = "tasp.msg";

    public static final String name = "msg";

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

        String msg = Command.combineArgs(1, args);

        Bukkit.getServer().getPluginManager().callEvent(new PersonSendMessageEvent(sender, p, msg));
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("message", "send", "tell");
    }

}
