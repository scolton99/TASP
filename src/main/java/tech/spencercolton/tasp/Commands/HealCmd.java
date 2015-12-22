package tech.spencercolton.tasp.Commands;

import org.bukkit.command.CommandSender;

/**
 * Created by scolton17 on 12/22/15.
 */
public class HealCmd extends TASPCommand {

    private static final String syntax = "";
    public static final String name = "";
    private static final String consoleSyntax = "";
    private static final String permission = "";

    @Override
    public void execute(CommandSender sender, String[] args) {

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
