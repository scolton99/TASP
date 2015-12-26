package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.M;
import tech.spencercolton.tasp.Util.Message;

import java.util.Collections;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class FOMCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/fom [player]";

    public static final String name = "fom";

    @Getter
    private static final String permission = "tasp.fom";

    @Getter
    private static final String consoleSyntax = "/fom <player>";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender && args.length != 1) {
            Command.sendConsoleSyntaxError(sender, this);
            return;
        }

        Person p = null;
        switch(args.length) {
            case 1: {
                p = Person.get(Bukkit.getPlayer(args[0]));
                if (p == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }
            }
            case 0: {
                if(p == null)
                    p = Person.get((Player)sender);
                assert p != null;
                p.setFOM(!p.isFOM());
                if (p.isFOM()) {
                    p.getPlayer().getWorld().getEntities().stream().forEach(e -> {
                        if (e instanceof Monster)
                            ((Monster) e).setTarget(null);
                    });
                }
                Message.FOM.sendFOMMessage(sender, p.isFOM(), p.getPlayer());
                return;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
            }
        }
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
