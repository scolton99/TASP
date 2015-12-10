package tech.spencercolton.tasp.Commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import tech.spencercolton.tasp.Entity.Person;

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

    /**
     * String containing the command's name.
     */
    public static final String name = "home";

    /**
     * String containing the command's syntax.
     */
    public static final String syntax = "/home";

    /**
     * String containing the command's console syntax.
     */
    public static final String consoleSyntax = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError((ConsoleCommandSender)sender, name);
            return;
        }

        if(args.length != 0) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Player p = (Player)sender;

        Person p2 = Person.get(p);

        JSONObject homeData = p2.getData().g;

        Location l = new Location(p2.getData().getString())

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSyntax() {
        return syntax;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getConsoleSyntax() {
        return consoleSyntax;
    }

}
