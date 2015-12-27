package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.bukkit.Bukkit.getPlayer;
import static org.bukkit.Bukkit.getServer;
import static tech.spencercolton.tasp.Commands.Command.sendPlayerMessage;
import static tech.spencercolton.tasp.Commands.Command.sendSyntaxError;

/**
 * @author Spencer Colton
 */
public class TeleportCmd extends TASPCommand {

    @Getter
    private final String syntax = "/tp <player>";

    @Getter
    private static final String name = "tp";

    @Getter
    private final String permission = "tasp.teleport";

    @Getter
    private final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);

        if (args.length != 1) {
            sendSyntaxError(sender, this);
            return;
        }

        Player p = (Player) sender;
        Player other = getPlayer(args[0]);

        if (other == null) {
            sendPlayerMessage(sender, args[0]);
            return;
        }

        getServer().getPluginManager().callEvent(new PersonTeleportEvent(p, other));
    }

    @Override
    public List<String> getAliases() {
        return singletonList("teleport");
    }

}
