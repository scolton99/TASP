package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Message;

/**
 * @author Spencer Colton
 */
public class WorldCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/world [world]";

    public static final String name = "world";

    @Getter
    private static final String permission = "tasp.world";

    @Getter
    private static final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] args) {
        assert !(sender instanceof ConsoleCommandSender);
        if(args.length > 1) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        World w = null;
        switch(args.length) {
            case 1: {
                w = Bukkit.getWorld(args[0]);
                if (w == null) {
                    Command.sendWorldMessage(sender, args[0]);
                    return;
                }
            }
            case 0: {
                if(args.length == 0) {
                    Message.World.sendWorldMessage(sender);
                } else {
                    assert w != null;
                    ((Player)sender).teleport(w.getSpawnLocation());
                }
                return;
            }
            default: {
                Command.sendSyntaxError(sender, this);
            }
        }
    }

}
