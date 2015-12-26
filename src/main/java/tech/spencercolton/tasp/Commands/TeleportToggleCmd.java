package tech.spencercolton.tasp.Commands;

import org.bukkit.command.CommandSender;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

import java.util.Arrays;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class TeleportToggleCmd extends TASPCommand {

    private static final String syntax = "/tpt";
    public static final String name = "tpt";
    private static final String permission = "tasp.teleport.toggle";
    private static final String consoleSyntax = "/tpt";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 0) {
            Command.sendGenericSyntaxError(sender, this);
            return;
        }

        sendToggledMessage(sender, TeleportCmd.toggleTeleporting());
    }

    private void sendToggledMessage(CommandSender sender, boolean enabled) {
        sender.sendMessage(M.m("command-message-text.teleporttoggle", (enabled ? "enabled" : "disabled")));
        if(Config.getBoolean("broadcast-teleport-toggle")) {
            String[] x = {"Teleportation has been", (enabled ? "enabled" : "disabled") + "."};
            new BroadcastCmd().execute(sender, x);
        }
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
        return Arrays.asList("tptoggle", "teleporttoggle", "teleportationtoggle");
    }

}
