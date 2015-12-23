package tech.spencercolton.tasp.Commands;

import org.bukkit.command.CommandSender;

/**
 * @author Spencer Colton
 */
public class TeleportAllHereCmd extends TASPCommand {

    public static final String syntax = "";
    public static final String name = "";
    public static final String permission = "";
    public static final String consoleSyntax = "";

    @Override
    public void execute(CommandSender sender, String[] args) {

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
