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
public class TeleportRequestCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/tpr <player>";

    public static final String name = "tpr";

    @Getter
    private static final String permission = "tasp.teleport.request";

    @Getter
    private static final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);

        if(args.length != 1) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if(p == null) {
            Command.sendPlayerMessage(sender, args[0]);
            return;
        }

        Bukkit.getServer().getPluginManager().callEvent(new PersonTeleportEvent((Player)sender, p, false, true));
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("tprequest");
    }

}
