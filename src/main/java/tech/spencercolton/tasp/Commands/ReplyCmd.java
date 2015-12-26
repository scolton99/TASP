package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.TASP;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReplyCmd extends TASPCommand {

    public static final String name = "reply";

    @Getter
    private static final String permission = "tasp.msg";

    @Getter
    private static final String syntax = "/reply <message>";

    @Getter
    private static final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String... args) {
        if (args.length == 0) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        CommandSender last;
        if(sender instanceof ConsoleCommandSender) {
            last = TASP.consoleLast;
        } else {
            last = Person.get((Player)sender).getLastMessaged();
        }

        if(last == null) {
            Message.Reply.Error.sendNoLastMessage(sender);
            return;
        }

        List<String> argz = new ArrayList<>(Arrays.asList(args));
        argz.add(0, last.getName());
        new MessageCmd().execute(sender, argz.toArray(new String[argz.size()]));
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("r");
    }

}
