package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.M;

import java.util.Arrays;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class DropsCmd extends TASPCommand {

    private static final String syntax = "/drops [world]";
    public static final String name = "drops";
    private static final String permission = "tasp.drops";
    private static final String consoleSyntax = "/drops <world>";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            if(args.length != 1) {
                Command.sendConsoleSyntaxError(sender, this);
                return;
            }

            World w = Bukkit.getWorld(args[0]);
            if(w == null) {
                Command.sendWorldMessage(sender, args[0]);
                return;
            }

            int i = 0;
            for(Entity e : w.getEntities()) {
                if(e.getType() == EntityType.DROPPED_ITEM) {
                    e.remove();
                    i++;
                }
            }
            sendDropsMessage(sender, i);
            return;
        }

        switch(args.length) {
            case 0:
                Player p = (Player)sender;
                World w = p.getWorld();

                int i = 0;
                for(Entity e : w.getEntities()) {
                    if(e.getType() == EntityType.DROPPED_ITEM) {
                        e.remove();
                        i++;
                    }
                }

                sendDropsMessage(sender, i);
                return;
            case 1:
                World w2 = Bukkit.getWorld(args[0]);
                if(w2 == null) {
                    Command.sendWorldMessage(sender, args[0]);
                    return;
                }

                int i2 = 0;
                for(Entity e : w2.getEntities()) {
                    if(e.getType() == EntityType.DROPPED_ITEM) {
                        e.remove();
                        i2++;
                    }
                }

                sendDropsMessage(sender, i2);
                return;
            default:
                Command.sendSyntaxError(sender, this);
        }
    }

    private void sendDropsMessage(CommandSender sender, int drops) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.drops", Integer.toString(drops)));
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
        return Arrays.asList("clearlag", "fixlag", "nolag", "lagfix");
    }

}
