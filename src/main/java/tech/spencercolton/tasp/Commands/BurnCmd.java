package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

/**
 * @author Spencer Colton
 */
public class BurnCmd extends TASPCommand {

    private static final String syntax = "/burn <player> [time]";
    public static final String name = "burn";
    private static final String permission = "tasp.burn";
    private static final String consoleSyntax = syntax;

    private static final int TICKS_PER_SECOND = 20;
    private static final int DEFAULT = Config.getInt("default-burn-time") * TICKS_PER_SECOND;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0 || args.length > 2) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if(p == null) {
            Command.sendPlayerMessage(sender, args[0]);
            return;
        }

        int x;
        if(args.length == 2) {
            try {
                x = Integer.parseInt(args[1]) * TICKS_PER_SECOND;
            } catch(NumberFormatException e) {
                Command.sendSyntaxError(sender, this);
                return;
            }
        } else {
            x = DEFAULT;
        }

        p.setFireTicks(x + p.getFireTicks());

        sendFireMessage(sender, x / TICKS_PER_SECOND, p);
    }

    private void sendFireMessage(CommandSender sender, int seconds) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.burn", Integer.toString(seconds)));
    }

    private void sendFireMessage(CommandSender sender, int seconds, Player other) {
        if(sender.equals(other)) {
            sendFireMessage(sender, seconds);
            return;
        }
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.burn-s", Integer.toString(seconds), other.getDisplayName()));
        if(Command.messageEnabled(this, true))
            other.sendMessage(M.m("command-message-text.burn-r", Integer.toString(seconds), Command.getDisplayName(sender)));
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
        return (args.length >= 1 && !Bukkit.getPlayer(args[0]).equals(sender)) ? permission + ".others" : permission;
    }

}
