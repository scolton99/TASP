package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

public class HealCmd extends TASPCommand {

    private static final String syntax = "/heal [person] [amount]";
    public static final String name = "heal";
    private static final String consoleSyntax = "/heal <person> [amount]";
    private static final String permission = "tasp.heal";

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
                    double start = p.getHealth();
                    p.setHealth(p.getMaxHealth());
                    sendHealedMessage(sender, (p.getMaxHealth() - start) / 2.0D, p);
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
                        double start2 = p2.getMaxHealth() - p2.getHealth();
                        double fin = p2.getHealth() + x;
                        if(fin > p2.getMaxHealth())
                            fin = p2.getMaxHealth();
                        p2.setHealth(fin);
                        double reported = x > start2 ? start2 : x;
                        sendHealedMessage(sender, reported / 2.0D, p2);
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
                p.setHealth(p.getMaxHealth());
                sendHealedMessage(sender, (p.getMaxHealth() - start) / 2);
                return;
            case 1:
                Player p2 = Bukkit.getPlayer(args[0]);
                if(p2 == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }
                double start2 = p2.getHealth();
                p2.setHealth(p2.getMaxHealth());
                sendHealedMessage(sender, (p2.getMaxHealth() - start2) / 2.0D, p2);
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
                    double start3 = p3.getMaxHealth() - p3.getHealth();
                    double fin = p3.getHealth() + x;
                    if(fin > p3.getMaxHealth())
                        fin = p3.getMaxHealth();
                    p3.setHealth(fin);
                    double reported = x > start3 ? start3 : x;
                    sendHealedMessage(sender, reported / 2.0D, p3);
                } catch(NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                }
        }
    }

    private void sendHealedMessage(CommandSender sender, double amount) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.heal", Double.toString(amount)));
    }

    private void sendHealedMessage(CommandSender sender, double amount, Player other) {
        if(sender.equals(other)) {
            sendHealedMessage(sender, amount);
            return;
        }

        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.heal-s", Double.toString(amount), other.getDisplayName()));
        if(Command.messageEnabled(this, true))
            other.sendMessage(M.m("command-message-text.heal-r", Double.toString(amount), Command.getDisplayName(sender)));
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return (args.length >= 1 && args.length <= 2 && !sender.equals(Bukkit.getPlayer(args[0]))) ? permission + ".others" : permission;
    }

}
