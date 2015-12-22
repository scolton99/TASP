package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

/**
 * @author Spencer Colton
 * @since 0.0.3
 */
public class HurtCmd extends TASPCommand {

    public static final String syntax = "/hurt [player] [amount]";
    public static final String name = "hurt";
    public static final String permission = "tasp.heal";
    public static final String consoleSyntax = "/hurt <player> [amount]";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            switch (args.length) {
                case 1:
                    Player p = Bukkit.getPlayer(args[0]);
                    if(p == null) {
                        Command.sendPlayerMessage(sender, args[0]);
                        return;
                    }
                    double y = p.getHealth();
                    p.setHealth(0.5D);
                    sendHurtMessage(sender, (y - 0.5D) / 2.0D, p);
                    return;
                case 2:
                    Player p2 = Bukkit.getPlayer(args[0]);
                    if(p2 == null) {
                        Command.sendPlayerMessage(sender, args[0]);
                        return;
                    }
                    try {
                        int x = Integer.parseInt(args[1]);
                        if(x < 0) {
                            sender.sendMessage(Config.err() + "Amount must be positive.");
                            return;
                        }
                        double start = p2.getHealth();
                        double fin = p2.getHealth() - x;
                        if(fin < 0.0D)
                            fin = 0.0D;
                        p2.setHealth(fin);
                        double reported = x > start ? start : x;
                        sendHurtMessage(sender, reported / 2.0D, p2);
                        return;
                    } catch (NumberFormatException e) {
                        Command.sendConsoleSyntaxError(sender, this);
                        return;
                    }
                default:
                    Command.sendConsoleSyntaxError(sender, this);
                    return;
            }
        }

        switch(args.length) {
            case 0:
                Player p = (Player)sender;
                double start = p.getHealth();
                p.setHealth(0.5D);
                sendHurtMessage(sender, (start - 0.5D) / 2.0D);
                return;
            case 1:
                Player p2 = Bukkit.getPlayer(args[0]);
                if(p2 == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }
                double start2 = p2.getHealth();
                p2.setHealth(0.5D);
                sendHurtMessage(sender, (start2 - 0.5D) / 2.0D, p2);
                return;
            case 2:
                Player p3 = Bukkit.getPlayer(args[0]);
                if(p3 == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }
                try {
                    int x = Integer.parseInt(args[1]);
                    if(x < 0) {
                        sender.sendMessage(Config.err() + "Amount must be positive.");
                        return;
                    }
                    double start3 = p3.getHealth();
                    double fin = p3.getHealth() - x;
                    if(fin < 0.0D)
                        fin = 0.0D;
                    p3.setHealth(fin);
                    double reported = x > start3 ? start3 : x;
                    sendHurtMessage(sender, reported / 2.0D, p3);
                } catch(NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                }
        }
    }

    private void sendHurtMessage(CommandSender sender, double amount) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.hurt", Double.toString(amount)));
    }

    private void sendHurtMessage(CommandSender sender, double amount, Player other) {
        if(sender.equals(other)) {
            sendHurtMessage(sender, amount);
            return;
        }

        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.hurt-s", Double.toString(amount), other.getDisplayName()));
        if(Command.messageEnabled(this, true))
            other.sendMessage(M.m("command-message-text.hurt-r", Double.toString(amount), Command.getDisplayName(sender)));
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
