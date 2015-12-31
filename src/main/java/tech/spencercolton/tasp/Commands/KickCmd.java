package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import lombok.Getter;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;

/**
 * @author Spencer Colton
 */
public class KickCmd extends TASPCommand {

    @Getter
    private final String syntax = "/kick <player> [reason]";

    @Getter
    private static final String name = "kick";

    @Getter
    private final String permission = "tasp.kick";

    @Getter
    private final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            Command.sendGenericSyntaxError(sender, this);
            return;
        }

        Player o = Bukkit.getPlayer(args[0]);
        if(o == null) {
            Command.sendPlayerMessage(sender, args[0]);
            return;
        }

        if(args.length >= 2) {
            String s = "";
            for(int i = 1; i < args.length; i++) {
                s += args[i];
                if(!((i + 1) >= args.length))
                    s += " ";
            }
            o.kickPlayer(s);
            if(Config.getBoolean("broadcast-kicks"))
                Bukkit.broadcastMessage(Config.c3() + o.getDisplayName() + Config.c4() + " was kicked from the server.");
            return;
        }

        o.kickPlayer("Kicked!");


    }

}
