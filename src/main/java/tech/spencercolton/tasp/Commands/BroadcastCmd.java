package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import tech.spencercolton.tasp.Events.TASPBroadcastEvent;

import java.util.Arrays;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class BroadcastCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/broadcast <message>";

    public static final String name = "broadcast";

    @Getter
    private static final String permission = "tasp.broadcast";

    @Getter
    private static final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String... args) {
        if(args.length == 0) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Bukkit.getServer().getPluginManager().callEvent(new TASPBroadcastEvent(sender, Command.combineArgs(args)));
    }


}
