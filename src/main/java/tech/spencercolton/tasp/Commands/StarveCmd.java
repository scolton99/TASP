package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

/**
 * Created by scolton17 on 12/22/15.
 */
public class StarveCmd extends TASPCommand {

    private static final String syntax = "/starve [amount] [player]";
    public static final String name = "starve";
    private static final String consoleSyntax = "/starve <player> [amount]";
    private static final String permission = "tasp.feed";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            switch (args.length) {
                case 1:
                    Player p = Bukkit.getPlayer(args[0]);
                    p.setFoodLevel(0);
                    p.setSaturation(0);
                    sendStarvedMessage(sender, 20, p);
                    return;
                case 2:
                    Player p2 = Bukkit.getPlayer(args[0]);
                    try {
                        int x = Integer.parseInt(args[1]);
                        if(x < 0) {
                            sender.sendMessage(Config.err() + "Amount must be positive.");
                            return;
                        }
                        p2.setFoodLevel(p2.getFoodLevel() - x);
                        p2.setSaturation(0);
                        sendStarvedMessage(sender, x, p2);
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
                p.setFoodLevel(0);
                p.setSaturation(0);
                sendStarvedMessage(sender, 20);
                return;
            case 1:
                Player p2 = (Player)sender;
                try {
                    int x = Integer.parseInt(args[0]);
                    p2.setFoodLevel(p2.getFoodLevel() - x);
                    p2.setSaturation(0);
                    sendStarvedMessage(sender, x, p2);
                } catch(NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                }
                return;
            case 2:
                Player p3 = Bukkit.getPlayer(args[1]);
                if(p3 == null) {
                    Command.sendPlayerMessage(sender, args[1]);
                    return;
                }
                try {
                    int x = Integer.parseInt(args[0]);
                    if(x < 0) {
                        sender.sendMessage(Config.err() + "Amount must be positive.");
                        return;
                    }
                    p3.setSaturation(0);
                    p3.setFoodLevel(p3.getFoodLevel() - x);
                    sendStarvedMessage(sender, x, p3);
                } catch(NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                }
        }
    }

    private void sendStarvedMessage(CommandSender sender, int amount) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.starve", Integer.toString(amount)));
    }

    private void sendStarvedMessage(CommandSender sender, int amount, Player other) {
        if(sender.equals(other)) {
            sendStarvedMessage(sender, amount);
            return;
        }

        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.starve-s", Integer.toString(amount), other.getDisplayName()));
        if(Command.messageEnabled(this, true))
            other.sendMessage(M.m("command-message-text.starve-r", Integer.toString(amount), Command.getDisplayName(sender)));
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
