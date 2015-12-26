package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import tech.spencercolton.tasp.TASP;
import tech.spencercolton.tasp.Util.Message;

import java.util.Arrays;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class TeleportToggleCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/tpt";

    public static final String name = "tpt";

    @Getter
    private static final String permission = "tasp.teleport.toggle";

    @Getter
    private static final String consoleSyntax = "/tpt";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 0) {
            Command.sendGenericSyntaxError(sender, this);
            return;
        }

        Message.Teleport.sendToggledMessage(sender, TASP.toggleTeleporting());
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("tptoggle", "teleporttoggle", "teleportationtoggle");
    }

}
