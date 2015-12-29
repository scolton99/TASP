package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import tech.spencercolton.tasp.Events.TASPBroadcastEvent;

import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;

/**
 * @author Spencer Colton
 */
public class BroadcastCmd extends TASPCommand {

    @Getter
    private final String syntax = "/broadcast <message>";

    @Getter
    private static final String name = "broadcast";

    @Getter
    private final String permission = "tasp.broadcast";

    @Getter
    private final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String... args) {
        if (args.length == 0) {
            sendSyntaxError(sender, this);
            return;
        }

        getServer().getPluginManager().callEvent(new TASPBroadcastEvent(sender, combineArgs(args)));
    }


}
