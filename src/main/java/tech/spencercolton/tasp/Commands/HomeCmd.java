package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.Message;

/**
 * The {@link TASPCommand} object containing the runtime information for the {@code home} command.
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
 *             {@code tasp.home}
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
 *             {@code null}
 *         </td>
 *     </tr>
 * </table>
 */
public class HomeCmd extends TASPCommand {

    public static final String name = "home";

    @Getter
    private static final String syntax = "/home";

    @Getter
    private static final String consoleSyntax = null;

    @Getter
    private static final String permission = "tasp.home";

    @Override
    public void execute(CommandSender sender, String... args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError(sender);
            return;
        }

        if(args.length != 0) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Player p = (Player)sender;

        Person p2 = Person.get(p);

        Location l = p2.getHome();

        if(l == null) {
            Message.Home.Error.sendNoHomeMessage(sender);
            return;
        }

        if(l.getWorld().equals(p.getWorld())) {
            p.teleport(l);
        } else {
            Message.Home.Error.sendWorldMessage(sender);
        }
    }

}
