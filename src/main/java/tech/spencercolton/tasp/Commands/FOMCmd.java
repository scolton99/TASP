package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;

import java.util.List;

import static java.util.Collections.*;
import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Entity.Person.get;
import static tech.spencercolton.tasp.Util.Message.FOM.*;

/**
 * @author Spencer Colton
 */
public class FOMCmd extends TASPCommand {

    @Getter
    private final String syntax = "/fom [player]";

    @Getter
    private static final String name = "fom";

    @Getter
    private final String permission = "tasp.fom";

    @Getter
    private final String consoleSyntax = "/fom <player>";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ConsoleCommandSender && args.length != 1) {
            sendConsoleSyntaxError(sender, this);
            return;
        }

        Person p = null;
        switch (args.length) {
            case 1: {
                p = get(getPlayer(args[0]));
                if (p == null) {
                    sendPlayerMessage(sender, args[0]);
                    return;
                }
            }
            case 0: {
                if (p == null)
                    p = get((Player) sender);
                assert p != null;
                p.setFOM(!p.isFOM());
                if (p.isFOM()) {
                    p.getPlayer().getWorld().getEntities().stream().forEach(e -> {
                        if (e instanceof Monster)
                            ((Monster) e).setTarget(null);
                    });
                }
                sendFOMMessage(sender, p.isFOM(), p.getPlayer());
                return;
            }
            default: {
                sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return (args.length == 1 && !sender.equals(getPlayer(args[0]))) ? permission + ".others" : permission;
    }

    @Override
    public List<String> getAliases() {
        return singletonList("disabletarget");
    }

}
