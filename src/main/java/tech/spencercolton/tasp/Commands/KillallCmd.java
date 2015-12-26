package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.Entities;
import tech.spencercolton.tasp.Util.M;
import tech.spencercolton.tasp.Util.Message;

import java.util.Collections;
import java.util.List;

/**
 * The {@link TASPCommand} object containing the runtime information for the {@code killall} command.
 *
 * <table summary="Properties">
 *     <tr>
 *         <th style="font-weight:bold;">Property</th>
 *         <th style="font-weight:bold;">Value</th>
 *     </tr>
 *     <tr>
 *         <td>
 *             Name
 *         </td>
 *         <td>
 *             {@value name}
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *             Permission
 *         </td>
 *         <td>
 *             {@code tasp.killall}
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *             Syntax
 *         </td>
 *         <td>
 *             {@value syntax}
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *             Console Syntax
 *         </td>
 *         <td>
 *             {@value consoleSyntax}
 *         </td>
 *     </tr>
 * </table>
 */
public class KillallCmd extends TASPCommand {

    /**
     * String containing the command's name.
     */
    public static final String name = "killall";

    /**
     * String containing the command's syntax.
     */
    @Getter
    private static final String syntax = "/killall [entity] [radius] OR /killall [entity]";

    /**
     * String containing the command's console syntax.
     */
    @Getter
    private static final String consoleSyntax = "/killall [entity] [world]";

    @Getter
    private static final String permission = "tasp.killall";

    @Override
    public void execute(CommandSender sender, String... argsg) {
        List<String> args = Command.processQuotedArguments(argsg);
        args = Command.removeSpaces(args.toArray(new String[args.size()]));

        Integer distance = 0;
        World w = null;
        String et = null;
        switch(args.size()) {
            case 2: {
                if(sender instanceof ConsoleCommandSender) {
                    try {
                        distance = Integer.parseInt(args.get(1));
                    } catch(NumberFormatException e) {
                        Command.sendSyntaxError(sender, this);
                        return;
                    }
                } else {
                    w = Bukkit.getWorld(args.get(1));
                    if(w == null) {
                        Command.sendConsoleSyntaxError(sender, this);
                        return;
                    }
                }
            }
            case 1: {
                if(!Entities.killAllowed(args.get(0))) {
                    Message.Killall.Error.sendInvalidEntityMessage(sender, args.get(0));
                    return;
                }
                et = args.get(0);
            }
            case 0: {
                if(et == null)
                    et = "all";
                et = et.toLowerCase();
                if(w == null)
                    w = ((Player)sender).getWorld();
                int count = 0;
                for(Entity e : w.getEntities()) {
                    if(!Entities.killAllowed(e))
                        continue;
                    switch(et) {
                        case "all": {
                            e.remove();
                            count++;
                            break;
                        }
                        case "monster":
                        case "monsters": {
                            if (Entities.isMonster(e)) {
                                e.remove();
                                count++;
                            }
                            break;
                        }
                        case "animal":
                        case "animals": {
                            if (Entities.isAnimal(e)) {
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
                Message.Killall.sendCountMessage(sender, count, w.getName());
                return;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("bluerinse");
    }

}
