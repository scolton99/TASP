package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import tech.spencercolton.tasp.TASP;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;
import tech.spencercolton.tasp.Util.Message;

import java.util.Collections;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class PowertoolToggleCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/ptt";

    public static final String name = "powertooltoggle";

    @Getter
    private static final String permission = "tasp.powertool.toggle";

    @Getter
    private static final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String... args) {
        if(args.length != 0) {
            Command.sendGenericSyntaxError(sender, this);
            return;
        }

        Message.PowertoolToggle.sendToggledMessage(sender, TASP.togglePowertools());
        Message.PowertoolToggle.broadcastToggledMessage(sender, TASP.powertoolsEnabled());
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("ptt");
    }

}
