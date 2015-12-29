package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.List;

import static java.util.Collections.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.TASP.*;
import static tech.spencercolton.tasp.Util.Message.PowertoolToggle.*;

/**
 * @author Spencer Colton
 */
public class PowertoolToggleCmd extends TASPCommand {

    @Getter
    private final String syntax = "/ptt";

    @Getter
    private static final String name = "powertooltoggle";

    @Getter
    private final String permission = "tasp.powertool.toggle";

    @Getter
    private final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String... args) {
        if (args.length != 0) {
            sendGenericSyntaxError(sender, this);
            return;
        }

        sendToggledMessage(sender, togglePowertools());
        broadcastToggledMessage(sender, powertoolsEnabled());
    }

    @Override
    public List<String> getAliases() {
        return singletonList("ptt");
    }

}
