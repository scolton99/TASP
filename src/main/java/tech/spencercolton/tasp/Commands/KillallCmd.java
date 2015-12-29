package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

import static java.lang.Integer.*;
import static java.util.Collections.*;
import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Util.Entities.*;
import static tech.spencercolton.tasp.Util.Message.Killall.*;

public class KillallCmd extends TASPCommand {

    /**
     * String containing the command's name.
     */
    @Getter
    private static final String name = "killall";

    /**
     * String containing the command's syntax.
     */
    @Getter
    private final String syntax = "/killall [entity] [radius] OR /killall [entity]";

    /**
     * String containing the command's console syntax.
     */
    @Getter
    private final String consoleSyntax = "/killall [entity] [world]";

    @Getter
    private final String permission = "tasp.killall";

    @Override
    public void execute(CommandSender sender, String... argsg) {
        List<String> args = processQuotedArguments(argsg);
        args = removeSpaces(args.toArray(new String[args.size()]));

        Integer distance = 0;
        World w = null;
        String et = null;
        switch (args.size()) {
            case 2: {
                if (sender instanceof Player) {
                    try {
                        distance = parseInt(args.get(1));
                    } catch (NumberFormatException e) {
                        sendSyntaxError(sender, this);
                        return;
                    }
                } else {
                    assert sender instanceof ConsoleCommandSender;
                    w = getWorld(args.get(1));
                    if (w == null) {
                        sendConsoleSyntaxError(sender, this);
                        return;
                    }
                }
            }
            case 1: {
                if (!killAllowed(args.get(0))) {
                    sendInvalidEntityMessage(sender, args.get(0));
                    return;
                }
                et = args.get(0);
            }
            case 0: {
                if (et == null)
                    et = "all";
                et = et.toLowerCase();
                if (w == null)
                    w = ((Player) sender).getWorld();
                int count = 0;
                for (Entity e : w.getEntities()) {
                    if (!killAllowed(e) || (distance != 0 && e.getLocation().distance(((Player) sender).getLocation()) > distance))
                        continue;
                    switch (et) {
                        case "all": {
                            e.remove();
                            count++;
                            break;
                        }
                        case "monster":
                        case "monsters": {
                            if (isMonster(e)) {
                                e.remove();
                                count++;
                            }
                            break;
                        }
                        case "animal":
                        case "animals": {
                            if (isAnimal(e)) {
                                e.remove();
                                count++;
                            }
                            break;
                        }
                        default: {
                            if (e.getType().toString().equalsIgnoreCase(et)) {
                                e.remove();
                                count++;
                            }
                            break;
                        }
                    }
                }
                sendCountMessage(sender, count, w.getName());
                return;
            }
            default: {
                sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public List<String> getAliases() {
        return singletonList("bluerinse");
    }

}
