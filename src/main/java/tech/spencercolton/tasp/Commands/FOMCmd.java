package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.M;

import java.util.Collections;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class FOMCmd extends TASPCommand {

    public static final String syntax = "/fom [player]";
    public static final String name = "fom";
    public static final String permission = "tasp.fom";
    public static final String consoleSyntax = "/fom <player>";

    @Override
    public void execute(CommandSender sender, String[] args) {
        switch(args.length) {
            case 0:
                if(sender instanceof ConsoleCommandSender) {
                    Command.sendConsoleSyntaxError(sender, this);
                    return;
                }
                Person p = Person.get((Player) sender);
                assert p != null;
                p.setFOM(!p.isFOM());
                if(p.isFOM()) {
                    p.getPlayer().getWorld().getEntities().stream().forEach(e -> {
                        if(e instanceof Monster)
                            ((Monster)e).setTarget(null);
                    });
                }
                sendFOMMessage(sender, p.isFOM());
                return;
            case 1:
                Player p2 = Bukkit.getPlayer(args[0]);
                if(p2 == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }

                Person p3 = Person.get(p2);
                assert p3 != null;
                p3.setFOM(!p3.isFOM());
                if(p3.isFOM()) {
                    p3.getPlayer().getWorld().getEntities().stream().forEach(e -> {
                        if(e instanceof Monster)
                            ((Monster)e).setTarget(null);
                    });
                }
                sendFOMMessage(sender, p3.isFOM(), p3);
        }
    }

    private void sendFOMMessage(CommandSender sender, boolean enabled) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.fom", (enabled ? "enabled" : "disabled")));
    }

    private void sendFOMMessage(CommandSender sender, boolean enabled, Person p) {
        if(p.getPlayer().equals(sender)) {
            sendFOMMessage(sender, enabled);
            return;
        }

        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.fom-s", (enabled ? "enabled" : "disabled"), p.getPlayer().getDisplayName()));
        if(Command.messageEnabled(this, true))
            p.getPlayer().sendMessage(M.m("command-message-text.fom-r", (enabled ? "enabled" : "disabled"), Command.getDisplayName(sender)));
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
        return (args.length == 1 && !sender.equals(Bukkit.getPlayer(args[0]))) ? permission + ".others" : permission;
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("disabletarget");
    }

}
