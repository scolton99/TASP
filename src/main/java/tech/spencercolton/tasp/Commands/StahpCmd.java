package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * @author Spencer Colton
 */
public class StahpCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/stahp";

    public static final String name = "stahp";

    @Getter
    private static final String permission = "tasp.stop";

    @Getter
    private static final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 0){
            Command.sendSyntaxError(sender, this);
            return;
        }
        Bukkit.shutdown();
    }

}
