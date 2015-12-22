package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import tech.spencercolton.tasp.Util.Config;

/**
 * @author Spencer Colton
 */
public class SrvInfoCmd extends TASPCommand {

    private static final String syntax = "/srvinfo";
    public static final String name = "srvinfo";
    private static final String permission = "tasp.info.server";
    private static final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 0) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        sender.sendMessage(Config.c1() + "This server is running " + Config.c3() + Bukkit.getServer().getVersion() + Config.c1() + " (" +
        Config.c4() + Bukkit.getServer().getBukkitVersion() + Config.c1() + ") ");
        sender.sendMessage(Config.c1() + " * Max Player Count: " + Config.c2() + Integer.toString(Bukkit.getServer().getMaxPlayers()));
        sender.sendMessage(Config.c1() + " * Whitelist: " + (Bukkit.getServer().hasWhitelist() ? Config.c3() : Config.c4()) + Boolean.toString(Bukkit.getServer().hasWhitelist()));
        sender.sendMessage(Config.c1() + " * Online Mode: " + (Bukkit.getServer().getOnlineMode() ? Config.c3() : Config.c4()) + Boolean.toString(Bukkit.getServer().getOnlineMode()));
        sender.sendMessage(Config.c1() + " * Flight Allowed: "+ (Bukkit.getServer().getAllowFlight() ? Config.c3() : Config.c4()) + Boolean.toString(Bukkit.getServer().getAllowFlight()));
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
