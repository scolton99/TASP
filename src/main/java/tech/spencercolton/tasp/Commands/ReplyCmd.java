package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static tech.spencercolton.tasp.Commands.Command.sendSyntaxError;
import static tech.spencercolton.tasp.Entity.Person.get;
import static tech.spencercolton.tasp.TASP.consoleLast;
import static tech.spencercolton.tasp.Util.Message.Reply.Error.sendNoLastMessage;

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
    public void execute(CommandSender sender, String... args) {
        if (args.length == 0) {
            sendSyntaxError(sender, this);
            return;
        }

        CommandSender last;
        if (sender instanceof ConsoleCommandSender) {
            last = consoleLast;
        } else {
            last = get((Player) sender).getLastMessaged();
        }

        if (last == null) {
            sendNoLastMessage(sender);
            return;
        }

        List<String> argz = new ArrayList<>(asList(args));
        argz.add(0, last.getName());
        new MessageCmd().execute(sender, argz.toArray(new String[argz.size()]));
    }

    @Override
    public List<String> getAliases() {
        return singletonList("r");
    }

}
