package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;

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
    public static final String syntax = "/fly [user]";


    /**
     * String containing the command's console syntax.
     */
    public static final String consoleSyntax = "/fly <user>";

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            if(args.length != 1) {
                Command.sendConsoleSyntaxError((ConsoleCommandSender)sender, this);
                return;
            }

            Player p = Bukkit.getPlayer(args[0]);

            if(p == null) {
                sender.sendMessage(ChatColor.RED + "Couldn't find player \"" + args[0] + ".\"  Please try again.");
                return;
            }

            p.setAllowFlight(true);

            p.setFlying(!p.isFlying());

            sendFlyingMessage(sender, p.isFlying(), p.getDisplayName());
        } else {
            switch(args.length) {
                case 0:
                    Player p = (Player)sender;
                    p.setAllowFlight(true);
                    p.setFlying(!p.isFlying());
                    sendFlyingMessage(sender, p.isFlying());
                    return;
                case 1:
                    Player p2 = Bukkit.getPlayer(args[0]);
                    if(p2 == null) {
                        sender.sendMessage(ChatColor.RED + "Couldn't find player \"" + args[0] + ".\"  Please try again.");
                        return;
                    }
                    p2.setAllowFlight(true);
                    p2.setFlying(!p2.isFlying());
                    sendFlyingMessage(sender, p2.isFlying(), p2.getDisplayName());
            }
        }
    }

    private void sendFlyingMessage(CommandSender sender, boolean flying) {
        sender.sendMessage(Config.c1() + "Flying was " + Config.c2() + (flying ? "enabled" : "disabled") + Config.c1() + ".");
    }

    private void sendFlyingMessage(CommandSender sender, boolean flying, String n) {
        sender.sendMessage(Config.c1() + "Flying was " + Config.c2() + (flying ? "enabled" : "disabled") + Config.c1() + " for " + Config.c2() + n + Config.c1() + ".");
        Player p = Bukkit.getPlayer(n);
        if(p != null)
            p.sendMessage(Config.c1() + "Flying was " + Config.c2() + (flying ? "enabled" : "disabled") + Config.c1() + " by " + Config.c2() + sender.getName() + Config.c1() + ".");
    }

}
