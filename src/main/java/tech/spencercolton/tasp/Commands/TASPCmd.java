package tech.spencercolton.tasp.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import tech.spencercolton.tasp.TASP;
import tech.spencercolton.tasp.Util.Config;

public class TASPCmd extends TASPCommand {

    /**
     * String containing the command's name.
     */
    public static final String name = "tasp";


    /**
     * String containing the command's syntax.
     */
    private static final String syntax = "/tasp [option]";


    /**
     * String containing the command's console syntax.
     */
    private static final String consoleSyntax = syntax;

    private static final String permission = "tasp.tasp";

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String... args) {
        if(args.length == 0){
            Command.sendSyntaxError(sender, this);
            return;
        }

        switch(args[0]) {
            case "reload":
                TASP.reload();
                sender.sendMessage(Config.c3() + "" + ChatColor.ITALIC + "TASP" + ChatColor.RESET + "" + Config.c4() + " reloaded.");
                break;
            case "config":
                break;
            default:
                Command.sendSyntaxError(sender, this);
        }
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

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        if(args.length == 0)
            return permission;

        switch(args[0]) {
            case "reload":
                return permission + ".reload";
            case "version":
                return permission + ".version";
            case "config":
                if(args[1].equals("set"))
                    return permission + ".config.set";
                if(args[1].equals("get"))
                    return permission + ".config.get";
                return permission + ".config";
            default:
                return permission;
        }
    }

}
