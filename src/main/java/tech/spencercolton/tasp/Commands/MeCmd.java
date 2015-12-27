package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;

import static org.bukkit.Bukkit.broadcastMessage;
import static tech.spencercolton.tasp.Commands.Command.combineArgs;
import static tech.spencercolton.tasp.Commands.Command.getDisplayName;
import static tech.spencercolton.tasp.Util.Config.c1;
import static tech.spencercolton.tasp.Util.Config.c2;

public class MeCmd extends TASPCommand {

    @Getter
    private final String syntax = "/me <action>";

    @Getter
    private final String consoleSyntax = syntax;

    @Getter
    private final String permission = "tasp.me";

    @Getter
    private static final String name = "me";

    @Override
    public void execute(CommandSender sender, String... args) {
        String msg = combineArgs(args);

        broadcastMessage(c1() + " * " + c2() + getDisplayName(sender) + c1() + " " + msg);
    }

}
