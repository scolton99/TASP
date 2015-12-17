package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

/**
 * The {@link TASPCommand} object containing the runtime information for the {@code setspeed} command.
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
 *             {@code tasp.setspeed}
 *             <br>
 *             {@code tasp.setspeed.others}
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *             Syntax
 *         </td>
 *         <td>
 *             /setspeed &lt;speed&gt; [player]
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *             Console Syntax
 *         </td>
 *         <td>
 *             /setspeed &lt;speed&gt; &lt;player&gt;
 *         </td>
 *     </tr>
 * </table>
 */
public class SetspeedCmd extends TASPCommand{

    /**
     * String containing the command's name.
     */
    public static final String name = "setspeed";

    /**
     * String containing the command's syntax.
     */
    public static final String syntax = "/setspeed <speed> [player]";

    /**
     * String containing the command's console syntax.
     */
    public static final String consoleSyntax = "/setspeed <speed> <player>";

    public static final String permission = "tasp.setspeed";

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError((ConsoleCommandSender)sender, name);
            return;
        }

        if(args.length > 2) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        if((args.length == 0) || (args.length == 1 && args[0].equalsIgnoreCase("default"))) {
            Player p = (Player)sender;
            p.setFlySpeed(0.1f);
            p.setWalkSpeed(0.2f);
            sendSpeedMessage(sender, 5.0F);
            return;
        }

        try {
            float i = Float.parseFloat(args[0]);

            if(i <= 0F || i >  50F) {
                sender.sendMessage(Config.err() + "Speed must be between 0 and 50 (Default 10)");
                return;
            }

            i /= 50;

            if(args.length == 2) {
                Player p = Bukkit.getPlayer(args[1]);
                if(p == null) {
                    sender.sendMessage(Config.err() + "Couldn't find player " + args[1]);
                    return;
                }

                p.setFlySpeed(i/2);
                p.setWalkSpeed(i);

                sendSpeedMessage(sender, Float.parseFloat(args[0]), p.getDisplayName());
            } else {
                Player p = (Player) sender;

                sendSpeedMessage(sender, Float.parseFloat(args[0]));

                p.setFlySpeed(i/2);
                p.setWalkSpeed(i);
            }
        } catch(NumberFormatException e) {
            Command.sendSyntaxError(sender, this);
        }
    }

    /**
     * Sends a player a nmessage informing him or her that his or her speed has been changed.
     *
     * @param p The player to send the message to.
     * @param speed The new speed.
     */
    private void sendSpeedMessage(CommandSender p, Float speed) {
        if(Command.messageEnabled(this, false))
            p.sendMessage(M.m("command-message-text.setspeed", speed.toString()));
    }

    /**
     * Sends a player a nmessage informing him or her that another player's speed has been changed.
     *
     * @param p The player to send the message to.
     * @param speed The new speed.
     * @param n The other player.
     */
    private void sendSpeedMessage(CommandSender p, Float speed, String n) {
        Player z = Bukkit.getPlayer(n);
        assert z != null;

        if(z.equals(p)) {
            sendSpeedMessage(p, speed);
            return;
        }

        if(Command.messageEnabled(this, false))
            p.sendMessage(M.m("command-message-text.setspeed-others-s", n, speed.toString()));
        if(Command.messageEnabled(this, true))
            z.sendMessage(M.m("command-message-text.setspeed-others-r", speed.toString(), p.getName()));
    }

    public boolean predictOthers(String[] args) {
        if(args.length < 2)
            return false;

        Player p = Bukkit.getPlayer(args[1]);

        return p != null;
    }

    public String getSyntax() {
        return syntax;
    }

    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    public String getPermission() {
        return permission;
    }

    public String getName() {
        return name;
    }

}
