package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

import static java.util.Arrays.*;
import static org.bukkit.Bukkit.*;
import static org.bukkit.entity.EntityType.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Util.Message.Drops.*;

/**
 * @author Spencer Colton
 */
public class DropsCmd extends TASPCommand {

    @Getter
    private final String syntax = "/drops [world]";

    @Getter
    private static final String name = "drops";

    @Getter
    private final String permission = "tasp.drops";

    @Getter
    private final String consoleSyntax = "/drops <world>";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ConsoleCommandSender && args.length != 1) {
            sendConsoleSyntaxError(sender, this);
            return;
        }

        World w = null;
        switch (args.length) {
            case 1: {
                w = getWorld(args[0]);
                if (w == null) {
                    sendWorldMessage(sender, args[0]);
                    return;
                }
            }
            case 0: {
                if (w == null) {
                    w = ((Player) sender).getWorld();
                }

                int i = 0;
                for (Entity e : w.getEntities()) {
                    if (e.getType() == DROPPED_ITEM) {
                        e.remove();
                        i++;
                    }
                }
                sendDropsMessage(sender, i);
                return;
            }
            default: {
                sendSyntaxError(sender, this);
            }
        }
    }

    @Override
    public List<String> getAliases() {
        return asList("clearlag", "fixlag", "nolag", "lagfix");
    }

}
