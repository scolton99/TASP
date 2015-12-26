package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.M;
import tech.spencercolton.tasp.Util.Message;

import java.util.Arrays;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class DropsCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/drops [world]";

    public static final String name = "drops";

    @Getter
    private static final String permission = "tasp.drops";

    @Getter
    private static final String consoleSyntax = "/drops <world>";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender && args.length != 1) {
            Command.sendConsoleSyntaxError(sender, this);
            return;
        }

        World w = null;
        switch(args.length) {
            case 1: {
                w = Bukkit.getWorld(args[0]);
                if(w == null) {
                    Command.sendWorldMessage(sender, args[0]);
                    return;
                }
            }
            case 0: {
                if(w == null) {
                    w = ((Player)sender).getWorld();
                }

                int i = 0;
                for(Entity e : w.getEntities()) {
                    if(e.getType() == EntityType.DROPPED_ITEM) {
                        e.remove();
                        i++;
                    }
                }
                Message.Drops.sendDropsMessage(sender, i);
                return;
            }
            default: {
                Command.sendSyntaxError(sender, this);
            }
        }
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("clearlag", "fixlag", "nolag", "lagfix");
    }

}
