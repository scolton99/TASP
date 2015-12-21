package tech.spencercolton.tasp.Commands;

import org.bukkit.command.CommandSender;

public class UnblockCmd extends TASPCommand {

    public static final String name = "unblock";
    public static final String syntax = "/unblock <player>";
    public static final String consoleSyntax = null;
    public static final String permission = "tasp.block";

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
