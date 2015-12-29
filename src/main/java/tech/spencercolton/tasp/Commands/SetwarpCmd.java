package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
    public void execute(CommandSender sender, String[] argsg) {
        assert sender instanceof Player;

        List<String> args = Command.processQuotedArguments(argsg);

        if(args.size() != 1) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Location l = ((Player)sender).getLocation();

        Warp.setWarp(l, args.get(0));

        // TODO Give this commmand a message
    }

}
