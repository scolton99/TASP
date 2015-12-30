package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Message;

/**
 * @author Spencer Colton
 */
public class SuCmd extends TASPCommand {

    @Getter
    private final String syntax = "/su <player> <command>";

    @Getter
    public static final String name = "su";

    @Getter
    private final String consoleSyntax = syntax;

    @Getter
    private final String permission = "tasp.su";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            Command.sendGenericSyntaxError(sender, this);
            return;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if (p == null) {
            Command.sendPlayerMessage(sender, args[0]);
            return;
        }

        String cmdLine = Command.combineArgs(1, args);

        p.performCommand(cmdLine);
        Message.Su.sendSuMessage(sender, cmdLine, p);
    }

}
