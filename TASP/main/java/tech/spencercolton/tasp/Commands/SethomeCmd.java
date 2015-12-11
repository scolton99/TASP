package tech.spencercolton.tasp.Commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Config;

/**
 * The {@link TASPCommand} object containing the runtime information for the {@code sethome} command.
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
 *             {@value permission}
 *             <br>
 *             {@code tasp.sethome.others}
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *             Syntax
 *         </td>
 *         <td>
 *             /sethome [player]
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *             Console Syntax
 *         </td>
 *         <td>
 *             /setspeed &lt;player&gt;
 *         </td>
 *     </tr>
 * </table>
 */
public class SethomeCmd extends TASPCommand {

    public static final String syntax = "/sethome [player]";
    public static final String consoleSyntax = "/sethome <player>";
    public static final String name = "sethome";
    public static final String permission = "tasp.sethome";

    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError((ConsoleCommandSender)sender, name);
            return;
        }

        if(args.length != 0) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Person p = Person.get((Player)sender);
        p.setHome(((Player)sender).getLocation());

        sendHomeMessage(sender, p.getHome());
    }

    public String getSyntax() {
        return syntax;
    }

    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    private void sendHomeMessage(CommandSender s, Location l) {
        s.sendMessage(Config.c1() + "Your home location was set to " + Config.c2() + l.getX() + " " + l.getY() + " " + l.getZ() + Config.c1() + ".");
    }

}
