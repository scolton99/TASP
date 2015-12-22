package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

/**
 *
 * @author Spencer Colton
 * @since 0.0.3
 */
public class StarveCmd extends TASPCommand {

    private static final String syntax = "/starve [player] [amount]";
    public static final String name = "starve";
    private static final String consoleSyntax = "/starve <player> [amount]";
    private static final String permission = "tasp.feed";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            switch (args.length) {
                case 1:
                    Player p = Bukkit.getPlayer(args[0]);
                    if(p == null) {
                        Command.sendPlayerMessage(sender, args[1]);
                        return;
                    }
                    int start = p.getFoodLevel();
                    p.setFoodLevel(0);
                    p.setSaturation(0);
                    sendStarvedMessage(sender, start / 2.0F, p);
                    return;
                case 2:
                    Player p2 = Bukkit.getPlayer(args[0]);
                    if(p2 == null) {
                        Command.sendPlayerMessage(sender, args[1]);
                        return;
                    }
                    try {
                        int x = Integer.parseInt(args[1]);
                        if(x < 0) {
                            sender.sendMessage(Config.err() + "Amount must be positive.");
                            return;
                        }
                        int start2 = p2.getFoodLevel();
                        int fin = p2.getFoodLevel() - x;
                        if(fin < 0)
                            fin = 0;
                        p2.setFoodLevel(fin);
                        p2.setSaturation(0);
                        int reported = x > start2 ? start2 : x;
                        sendStarvedMessage(sender, reported / 2.0F, p2);
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
                int start = p.getFoodLevel();
                p.setFoodLevel(0);
                p.setSaturation(0);
                sendStarvedMessage(sender, start / 2.0F);
                return;
            case 1:
                Player p2 = Bukkit.getPlayer(args[0]);
                if(p2 == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }
                int start2 = p2.getFoodLevel();
                p2.setFoodLevel(0);
                p2.setSaturation(0);
                sendStarvedMessage(sender, start2 / 2.0F, p2);
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
                    int start3 = p3.getFoodLevel();
                    int fin = p3.getFoodLevel() - x;
                    if(fin < 0)
                        fin = 0;
                    p3.setFoodLevel(fin);
                    p3.setSaturation(0);
                    int reported = x > start3 ? start3 : x;
                    sendStarvedMessage(sender, reported / 2.0F, p3);
                } catch(NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                }
        }
    }

    private void sendStarvedMessage(CommandSender sender, float amount) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.starve", Float.toString(amount)));
    }

    private void sendStarvedMessage(CommandSender sender, float amount, Player other) {
        if(sender.equals(other)) {
            sendStarvedMessage(sender, amount);
            return;
        }

        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.starve-s", Float.toString(amount), other.getDisplayName()));
        if(Command.messageEnabled(this, true))
            other.sendMessage(M.m("command-message-text.starve-r", Float.toString(amount), Command.getDisplayName(sender)));
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
        return (args.length == 2 && !sender.equals(Bukkit.getPlayer(args[1]))) ? permission + ".others" : permission;
    }

}
