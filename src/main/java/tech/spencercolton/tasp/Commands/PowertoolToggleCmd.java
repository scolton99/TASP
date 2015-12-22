package tech.spencercolton.tasp.Commands;

import org.bukkit.command.CommandSender;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

import java.util.Collections;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class PowertoolToggleCmd extends TASPCommand {

    private static final String syntax = "/powertooltoggle";
    public static final String name = "powertooltoggle";
    private static final String permission = "tasp.powertool.toggle";
    private static final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String... args) {
        if(args.length != 0) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        this.sendToggledMessage(sender, PowertoolCmd.togglePowertools());
        this.broadcastToggledMessage(sender, PowertoolCmd.powertoolsEnabled());
    }

    private void sendToggledMessage(CommandSender sender, boolean enabled) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.powertooltoggle", enabled ? "enabled" : "disabled"));
    }

    private void broadcastToggledMessage(CommandSender sender, boolean enabled) {
        if(Config.getBoolean("broadcast-powertool-toggle")) {
            String[] x = {"Powertools have been", (enabled ? "enabled" : "disabled") + "."};
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
        return Collections.singletonList("ptt");
    }

}
