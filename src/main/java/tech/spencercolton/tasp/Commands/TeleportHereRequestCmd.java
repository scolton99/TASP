package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;

import java.util.Arrays;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class TeleportHereRequestCmd extends TASPCommand {

    private static final String syntax = "/tprhere <player>";
    public static final String name = "tprhere";
    private static final String permission = "tasp.teleport.request.here";
    private static final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if(p == null) {
            Command.sendPlayerMessage(sender, args[0]);
            return;
        }

        Bukkit.getServer().getPluginManager().callEvent(new PersonTeleportEvent((Player)sender, p, true, true));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("tprequesthere", "teleportrequesthere", "teleporthererequest", "tphererequest", "tpherer");
    }

}
