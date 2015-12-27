package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;

import static org.bukkit.Bukkit.getServer;
import static tech.spencercolton.tasp.Commands.Command.sendSyntaxError;
import static tech.spencercolton.tasp.Util.Config.*;

/**
 * @author Spencer Colton
 */
public class SrvInfoCmd extends TASPCommand {

    @Getter
    private final String syntax = "/srvinfo";

    @Getter
    private static final String name = "srvinfo";

    @Getter
    private final String permission = "tasp.info.server";

    @Getter
    private final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            sendSyntaxError(sender, this);
            return;
        }

        sender.sendMessage(c1() + "This server is running " + c3() + getServer().getVersion() + c1() + " (" +
                c4() + getServer().getBukkitVersion() + c1() + ") ");
        sender.sendMessage(c1() + " * Max Player Count: " + c2() + Integer.toString(getServer().getMaxPlayers()));
        sender.sendMessage(c1() + " * Whitelist: " + (getServer().hasWhitelist() ? c3() : c4()) + Boolean.toString(getServer().hasWhitelist()));
        sender.sendMessage(c1() + " * Online Mode: " + (getServer().getOnlineMode() ? c3() : c4()) + Boolean.toString(getServer().getOnlineMode()));
        sender.sendMessage(c1() + " * Flight Allowed: " + (getServer().getAllowFlight() ? c3() : c4()) + Boolean.toString(getServer().getAllowFlight()));
    }

}
