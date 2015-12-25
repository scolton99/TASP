package tech.spencercolton.tasp.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Spencer Colton
 */
public class SpawnCmd extends TASPCommand {

    private static final String syntax = "/spawn";
    public static final String name = "spawn";
    private static final String consoleSyntax = null;
    private static final String permission = "tasp.spawn";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 0) {
            Command.sendSyntaxError(sender, this);
            return;
        }
        ((Player)sender).teleport(((Player)sender).getWorld().getSpawnLocation());
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
