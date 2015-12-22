package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.M;

/**
 * @author Spencer Colton
 */
public class BuddhaCmd extends TASPCommand {

    public static final String syntax = "/buddha [player]";
    public static final String name = "buddha";
    public static final String permission = "tasp.buddha";
    public static final String consoleSyntax = "/buddha <player>";

    @Override
    public void execute(CommandSender sender, String[] args) {
        switch(args.length) {
            case 0:
                if(sender instanceof ConsoleCommandSender) {
                    Command.sendConsoleSyntaxError(sender, this);
                    return;
                }

                Player p = (Player)sender;
                Person pa = Person.get(p);
                assert pa != null;
                pa.setBuddha(!pa.isBuddha());
                sendBuddhaMessage(sender, pa.isBuddha());
                return;
            case 1:
                Player p1 = Bukkit.getPlayer(args[0]);
                if(p1 == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }

                Person pa1 = Person.get(p1);
                assert pa1 != null;
                pa1.setBuddha(!pa1.isBuddha());
                sendBuddhaMessage(sender, pa1.isBuddha(), pa1);
                return;
            default:
                Command.sendGenericSyntaxError(sender, this);
        }
    }

    private void sendBuddhaMessage(CommandSender sender, boolean enabled) {
        sender.sendMessage(M.m("command-message-text.buddha", (enabled ? "enabled" : "disabled")));
    }

    private void sendBuddhaMessage(CommandSender sender, boolean enabled, Person p) {
        if(sender.equals(p)) {
            sendBuddhaMessage(sender, enabled);
            return;
        }

        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.buddha-s", (enabled ? "enabled" : "disabled"), p.getPlayer().getDisplayName()));
        if(Command.messageEnabled(this, true))
            sender.sendMessage(M.m("command-message-text.buddha-r", (enabled ? "enabled" : "disabled"), Command.getDisplayName(sender)));
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
