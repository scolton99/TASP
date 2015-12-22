package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.M;

import java.util.Set;

/**
 * @author Spencer Colton
 */
public class ShockCmd extends TASPCommand {

    private static final String syntax = "/shock [player]";
    public static final String name = "shock";
    private static final String permission = "tasp.shock";
    private static final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String[] args) {
        switch(args.length) {
            case 0:
                if(sender instanceof ConsoleCommandSender) {
                    Command.sendConsoleSyntaxError(sender, this);
                    return;
                }

                World w2 = ((Player)sender).getWorld();
                w2.strikeLightning(((Player)sender).getTargetBlock((Set<Material>)null, 1000).getLocation());
                break;
            case 1:
                Player p = Bukkit.getPlayer(args[0]);
                if(p == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }

                World w = p.getWorld();
                w.strikeLightning(p.getLocation());

                sendShockMessage(sender, p);
                break;
            default:
                Command.sendSyntaxError(sender, this);
        }
    }

    private void sendShockMessage(CommandSender sender) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.u("command-message-text.shock"));
    }

    private void sendShockMessage(CommandSender sender, Player other) {
        if(other.equals(sender)) {
            sendShockMessage(sender);
            return;
        }
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.shock-s", other.getDisplayName()));
        if(Command.messageEnabled(this, true))
            other.sendMessage(M.m("command-message-text.shock-r", (sender instanceof ConsoleCommandSender) ? sender.getName() : ((Player)sender).getDisplayName()));
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
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return (args.length == 1 && !Bukkit.getPlayer(args[0]).equals(sender)) ? permission + ".others" : permission;
    }

}
