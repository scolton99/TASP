package tech.spencercolton.tasp.Commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Config;

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
    private static final String syntax = "/home";

    /**
     * String containing the command's console syntax.
     */
    private static final String consoleSyntax = null;

    private static final String permission = "tasp.home";

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String... args) {
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

        Location l = p2.getHome();

        if(l == null) {
            this.sendNoHomeMessage(sender);
            return;
        }

        if(l.getWorld().equals(p.getWorld())) {
            p.teleport(l);
        } else {
            this.sendWorldMessage(sender);
        }

    }

    private void sendWorldMessage(CommandSender s) {
        s.sendMessage(Config.err() + "You could not be teleported to your home because it is not in this world.");
    }

    private void sendNoHomeMessage(CommandSender s) {
        s.sendMessage(Config.err() + "You could not be sent home because you have not set your home.  Use /sethome first.");
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
    public String getPermission() {
        return permission;
    }

    @Override
    public String getName() {
        return name;
    }

}
