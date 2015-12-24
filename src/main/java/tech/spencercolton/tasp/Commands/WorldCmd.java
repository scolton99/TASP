package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.M;

/**
 * @author Spencer Colton
 */
public class WorldCmd extends TASPCommand {

    private static final String syntax = "/world [world]";
    public static final String name = "world";
    private static final String permission = "tasp.world";
    private static final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError((ConsoleCommandSender)sender);
            return;
        }

        switch(args.length) {
            case 0:
                sendWorldMessage(sender);
                return;
            case 1:
                World w = Bukkit.getWorld(args[0]);
                if(w == null) {
                    Command.sendWorldMessage(sender, args[0]);
                    return;
                }
                Player p = (Player)sender;
                p.teleport(w.getSpawnLocation());
                return;
            default:
                Command.sendSyntaxError(sender, this);
        }
    }

    private void sendWorldMessage(CommandSender sender) {
        if(Command.messageEnabled(this, true))
            sender.sendMessage(M.m("command-message-text.world", ((Player)sender).getWorld().getName()));
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
