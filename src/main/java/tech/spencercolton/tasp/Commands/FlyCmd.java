package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;
import tech.spencercolton.tasp.Util.Message;

/**
 * The {@link TASPCommand} object containing the runtime information for the {@code fly} command.
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
 *             {@code tasp.fly}
 *             <br>
 *             {@code tasp.fly.others}
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
 *             /fly &lt;user&gt;
 *         </td>
 *     </tr>
 * </table>
 */
public class FlyCmd extends TASPCommand {

    /**
     * String containing the command's name.
     */
    public static final String name = "fly";


    /**
     * String containing the command's syntax.
     */
    @Getter
    private static final String syntax = "/fly [user]";


    /**
     * String containing the command's console syntax.
     */
    @Getter
    private static final String consoleSyntax = "/fly <user>";

    @Getter
    private static final String permission = "tasp.fly";

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String... args) {
        if(sender instanceof ConsoleCommandSender && args.length != 1) {
            Command.sendConsoleSyntaxError(sender, this);
            return;
        }

        Player p = null;
        switch(args.length) {
            case 1: {
                p = Bukkit.getPlayer(args[0]);
                if (p == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }
            }
            case 0: {
                if(p == null)
                    p = (Player)sender;
                p.setAllowFlight(true);
                p.setFlying(!p.isFlying());
                if(p.equals(sender))
                    Message.Fly.sendFlyingMessage(sender, p.isFlying());
                else
                    Message.Fly.sendFlyingMessage(sender, p.isFlying(), p.getDisplayName());
                return;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return args.length > 0 && Bukkit.getPlayer(args[0]) != null && !Bukkit.getPlayer(args[0]).equals(sender) ? permission + ".others" : permission;
    }

}
