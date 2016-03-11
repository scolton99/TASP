package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;

/**
 * @author Spencer Colton
 */
public class NetworkInfoCmd extends TASPCommand {

    @Getter
    private final String syntax = "/netinfo";

    @Getter
    public static final String name = "netinfo";

    @Getter
    private final String consoleSyntax = "/netinfo";

    @Getter
    private final String permission = "tasp.info.network";

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        // TODO: add command logic
        return CommandResponse.SUCCESS;
    }

}
