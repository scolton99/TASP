package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Message;
import tech.spencercolton.tasp.Util.Warp;

import java.util.List;

/**
 * @author Spencer Colton
 */
public class SetwarpCmd extends TASPCommand {

    @Getter
    private final String syntax = "/setwarp <name>";

    @Getter
    private static final String name = "setwarp";

    @Getter
    private final String permission = "tasp.warp.set";

    @Getter
    private final String consoleSyntax = null;

    @Override
    public CommandResponse execute(CommandSender sender, String[] argsg) {
        assert sender instanceof Player;

        List<String> args = Command.processQuotedArguments(argsg);

        if(args.size() != 1) {
            Command.sendSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        Location l = ((Player)sender).getLocation();

        if(Warp.setWarp(l, args.get(0))) {
            Message.Setwarp.sendWarpSetMessage(sender, l, args.get(0));
        } else {
            Message.Setwarp.Error.warpAlreadyExists(sender);
        }

        return CommandResponse.SUCCESS;
    }

}
