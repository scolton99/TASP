package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;

import java.util.Collections;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class TeleportCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/tp <player>";

    public static final String name = "tp";

    @Getter
    private static final String permission = "tasp.teleport";

    @Getter
    private static final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);

        if(args.length != 1) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Player p = (Player)sender;
        Player other = Bukkit.getPlayer(args[0]);

        if(other == null) {
            Command.sendPlayerMessage(sender, args[0]);
            return;
        }

        Bukkit.getServer().getPluginManager().callEvent(new PersonTeleportEvent(p, other));
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("teleport");
    }

}
