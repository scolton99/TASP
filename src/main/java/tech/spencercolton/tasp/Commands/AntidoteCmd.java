package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.M;

import java.util.Collections;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class AntidoteCmd extends TASPCommand {

    private static final String syntax = "/antidote [player]";
    public static final String name = "antidote";
    private static final String permission = "tasp.antidote";
    private static final String consoleSyntax = "/antidote <player>";

    @Override
    public void execute(CommandSender sender, String... args) {
        switch(args.length) {
            case 0:
                if(sender instanceof ConsoleCommandSender) {
                    Command.sendConsoleSyntaxError(sender, this);
                    return;
                }
                Player p = (Player)sender;
                p.getActivePotionEffects().stream().forEach(e -> p.removePotionEffect(e.getType()));
                sendAntidoteMessage(sender);
                return;
            case 1:
                Player p2 = Bukkit.getPlayer(args[0]);
                if(p2 == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }
                p2.getActivePotionEffects().stream().forEach(e -> p2.removePotionEffect(e.getType()));
                sendAntidoteMessage(sender, p2);
                return;
            default:
                Command.sendGenericSyntaxError(sender, this);
        }
    }

    private void sendAntidoteMessage(CommandSender sender) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.antidote"));
    }

    private void sendAntidoteMessage(CommandSender sender, Player p) {
        if(sender.equals(p)) {
            sendAntidoteMessage(sender);
            return;
        }

        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.antidote-s", p.getDisplayName()));
        if(Command.messageEnabled(this, true))
            p.sendMessage(M.m("command-message-text.antidote-r", Command.getDisplayName(sender)));
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
        return Collections.singletonList("cleareffects");
    }

}
