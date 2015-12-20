package tech.spencercolton.tasp.Commands;

import org.bukkit.command.CommandSender;

public class BlockCmd extends TASPCommand {

    /**
     * String containing the command's name.
     */
    public static final String name = "fly";


    /**
     * String containing the command's syntax.
     */
    public static final String syntax = "/fly [user]";


    /**
     * String containing the command's console syntax.
     */
    public static final String consoleSyntax = "/fly <user>";

    public static final String permission = "tasp.block";

    @Override
    public void execute(CommandSender sender, String[] args) {

    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public String getConsoleSyntax() {
        return null;
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public boolean predictOthers(CommandSender sender, String[] s) {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }
}
