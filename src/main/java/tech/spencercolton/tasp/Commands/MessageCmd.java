package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import tech.spencercolton.tasp.Events.PersonSendMessageEvent;

import java.util.List;

import static java.util.Arrays.*;
import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;

public class MessageCmd extends TASPCommand {

    @Getter
    private final String syntax = "/msg <player> <message>";

    @Getter
    private final String consoleSyntax = syntax;

    @Getter
    private final String permission = "tasp.msg";

    @Getter
    private static final String name = "msg";

    @Override
    public CommandResponse execute(CommandSender sender, String... args) {
        if (args.length < 2) {
            sendSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        CommandSender p;
        if (args[0].equalsIgnoreCase("console"))
            p = getConsoleSender();
        else
            p = getPlayer(args[0]);

        if (p == null) {
            sendPlayerMessage(sender, args[0]);
            return CommandResponse.PLAYER;
        }

        String msg = combineArgs(1, args);

        getServer().getPluginManager().callEvent(new PersonSendMessageEvent(sender, p, msg));

        return CommandResponse.SUCCESS;
    }

    @Override
    public List<String> getAliases() {
        return asList("message", "send", "tell");
    }

}
