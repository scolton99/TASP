package tech.spencercolton.tasp.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Listeners.PlayerTeleportListener;
import tech.spencercolton.tasp.Util.Config;

/**
 * @author Spencer Colton
 */
public class BackCmd extends TASPCommand {

    private static final String syntax = "/back";
    public static final String name = "back";
    private static final String consoleSyntax = null;
    private static final String permission = "tasp.teleport.back";

    @Override
    public void execute(CommandSender sender, String[] args) {
        Person p = Person.get((Player)sender);
        if(p.getLastLocation() == null) {
            p.getPlayer().sendMessage(Config.err() + "No location to teleport back to!");
        } else {
            p.getPlayer().teleport(p.getLastLocation());
        }
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    @Override
    public String getName() {
        return name;
    }

}
