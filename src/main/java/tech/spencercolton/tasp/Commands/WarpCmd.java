package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import lombok.Getter;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;
import tech.spencercolton.tasp.Util.Warp;

import java.util.List;

/**
 * @author Spencer Colton
 */
public class WarpCmd extends TASPCommand {

    @Getter
    private final String syntax = "/warp <warp-name>";

    @Getter
    private static final String name = "warp";

    @Getter
    private final String permission = "tasp.warp";

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

        Player p = (Player)sender;

        Location l = Warp.getWarp(args.get(0));
        if(l != null) {
            Bukkit.getServer().getPluginManager().callEvent(new PersonTeleportEvent(Person.get(p), l));
        } else {
            //TODO Add warp not found message here
        }

    }

}
