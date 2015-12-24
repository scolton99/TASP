package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import tech.spencercolton.tasp.Events.TASPBroadcastEvent;

import java.util.Arrays;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class BroadcastCmd extends TASPCommand {

    private static final String syntax = "/broadcast <message>";
    public static final String name = "broadcast";
    private static final String permission = "tasp.broadcast";
    private static final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String... args) {
        if(args.length == 0) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        String fin = "";
        List<String> arg = Arrays.asList(args);
        for(int i = 0; i < arg.size(); i++) {
            fin += arg.get(i);
            if(!(i + 1 >= arg.size()))
                fin += " ";
        }

        Bukkit.getServer().getPluginManager().callEvent(new TASPBroadcastEvent(sender, fin));
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
