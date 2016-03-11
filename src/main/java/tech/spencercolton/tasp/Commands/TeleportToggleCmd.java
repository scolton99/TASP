package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.List;

import static java.util.Arrays.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.TASP.*;
import static tech.spencercolton.tasp.Util.Message.Teleport.*;

/**
 * @author Spencer Colton
 */
public class TeleportToggleCmd extends TASPCommand {

    @Getter
    private final String syntax = "/tpt";

    @Getter
    private static final String name = "tpt";

    @Getter
    private final String permission = "tasp.teleport.toggle";

    @Getter
    private final String consoleSyntax = "/tpt";

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            sendGenericSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        sendToggledMessage(sender, toggleTeleporting());

        return CommandResponse.SUCCESS;
    }

    @Override
    public List<String> getAliases() {
        return asList("tptoggle", "teleporttoggle", "teleportationtoggle");
    }

}
