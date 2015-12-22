package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * @author Spencer Colton
 */
public class StahpCmd extends TASPCommand {

    private static final String syntax = "/stahp";
    public static final String name = "stahp";
    private static final String permission = "tasp.stop";
    private static final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 0){
            Command.sendSyntaxError(sender, this);
            return;
        }
        Bukkit.shutdown();
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


}
