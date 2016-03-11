package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;

import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;

/**
 * @author Spencer Colton
 */
public class StahpCmd extends TASPCommand {

    @Getter
    private final String syntax = "/stahp";

    @Getter
    private static final String name = "stahp";

    @Getter
    private final String permission = "tasp.stop";

    @Getter
    private final String consoleSyntax = syntax;

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            sendSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }
        shutdown();
        return CommandResponse.SUCCESS;
    }

}
