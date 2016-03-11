package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Message;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.*;
import static java.util.Collections.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Entity.Person.get;
import static tech.spencercolton.tasp.TASP.*;

public class ReplyCmd extends TASPCommand {

    @Getter
    private static final String name = "reply";

    @Getter
    private final String permission = "tasp.msg";

    @Getter
    private final String syntax = "/reply <message>";

    @Getter
    private final String consoleSyntax = syntax;

    @Override
    public CommandResponse execute(CommandSender sender, String... args) {
        if (args.length == 0) {
            sendSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        CommandSender last;
        if (sender instanceof ConsoleCommandSender) {
            last = consoleLast;
        } else {
            last = get((Player) sender).getLastMessaged();
        }

        if (last == null) {
            Message.Reply.Error.sendNoLastMessage(sender);
            return CommandResponse.FAILURE;
        }

        List<String> argz = new ArrayList<>(asList(args));
        argz.add(0, last.getName());
        new MessageCmd().execute(sender, argz.toArray(new String[argz.size()]));

        return CommandResponse.SUCCESS;
    }

    @Override
    public List<String> getAliases() {
        return singletonList("r");
    }

}
