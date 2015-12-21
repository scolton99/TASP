package tech.spencercolton.tasp.Commands;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.bukkit.command.CommandSender;

public class TASPCmd extends TASPCommand {

    /**
     * String containing the command's name.
     */
    public static final String name = "tasp";


    /**
     * String containing the command's syntax.
     */
    public static final String syntax = "/tasp [option]";


    /**
     * String containing the command's console syntax.
     */
    public static final String consoleSyntax = syntax;

    public static final String permission = "tasp.tasp";

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String[] args) {

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
    public String getPermission() {
        return permission;
    }

    @Override
    public String getName() {
        return name;
    }

}
