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
public class StalkerCmd extends TASPCommand {

    private static final String syntax = "/stalker [person]";
    public static final String name = "stalker";
    private static final String permission = "tasp.msg.stalk";
    private static final String consoleSyntax = "/stalker <person>";

    @Override
    public void execute(CommandSender sender, String... args) {
        switch(args.length) {
            case 0:
                if(sender instanceof ConsoleCommandSender) {
                    Command.sendConsoleSyntaxError((ConsoleCommandSender)sender, this);
                }

                Person p = Person.get((Player)sender);

                Boolean b = p.getData().getBoolean("stalker");
                if(b == null || !b)
                    p.getData().setBoolean("stalker", true);
                else
                    p.getData().setBoolean("stalker", false);

                assert p.getData().getBoolean("stalker") != null;
                this.sendStalkerMessage(sender, p.getData().getBoolean("stalker"));
                break;
            case 1:
                Player p2 = Bukkit.getPlayer(args[0]);

                if(p2 == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }

                Person p3 = Person.get(p2);
                assert p3 != null;

                Boolean b2 = p3.getData().getBoolean("stalker");
                if(b2 == null || !b2)
                    p3.getData().setBoolean("stalker", true);
                else
                    p3.getData().setBoolean("stalker", false);

                assert p3.getData().getBoolean("stalker") != null;
                this.sendStalkerMessage(sender, p3.getData().getBoolean("stalker"), p3);
                break;
            default:
                if(sender instanceof ConsoleCommandSender) {
                    Command.sendConsoleSyntaxError((ConsoleCommandSender)sender, this);
                } else {
                    Command.sendSyntaxError(sender, this);
                }
        }
    }

    private void sendStalkerMessage(CommandSender sender, boolean enabled) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.stalker", enabled ? "enabled" : "disabled"));
    }

    private void sendStalkerMessage(CommandSender sender, boolean enabled, Person other) {
        if(Person.get((Player)sender).equals(other)) {
            sendStalkerMessage(sender, enabled);
            return;
        }
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.stalker-s", enabled ? "enabled" : "disabled", other.getPlayer().getDisplayName()));
        if(Command.messageEnabled(this, true))
            other.getPlayer().sendMessage(M.m("command-message-text.stalker-r", enabled ? "enabled" : "disabled", sender.getName()));
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
    public String predictRequiredPermission(CommandSender s, String... args) {
        return args.length == 1 && Bukkit.getPlayer(args[0]) != null && !Bukkit.getPlayer(args[0]).equals(s) ? permission + ".others" : permission;
    }

}
