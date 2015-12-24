package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;

import java.util.Collections;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class TeleportCmd extends TASPCommand {

    private static final String syntax = "/tp <player>";
    public static final String name = "tp";
    private static final String permission = "tasp.teleport";
    private static final String consoleSyntax = null;

    private static boolean enabled = true;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError((ConsoleCommandSender)sender);
            return;
        }

        if(args.length != 1) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Player p = (Player)sender;
        Player other = Bukkit.getPlayer(args[0]);

        Bukkit.getServer().getPluginManager().callEvent(new PersonTeleportEvent(p, other));
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

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("teleport");
    }

    public static boolean toggleTeleporting() {
        return enabled = !enabled;
    }

    public static boolean isEnabled() {
        return enabled;
    }

}
