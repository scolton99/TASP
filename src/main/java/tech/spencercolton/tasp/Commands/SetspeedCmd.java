package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
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
    private static final String syntax = "/setspeed <speed> [player]";

    /**
     * String containing the command's console syntax.
     */
    private static final String consoleSyntax = "/setspeed <speed> <player>";

    private static final String permission = "tasp.setspeed";

    private static final float DEFAULT_FLY_SPEED = 0.1F;
    private static final float DEFAULT_WALK_SPEED = 0.2F;
    private static final float MAX_SPEED = 50.0F;
    private static final float DEFAULT_CUSTOM_SPEED = 5.0F;
    private static final int CONVERSION_FACTOR = 50;

    @Override
    public void execute(CommandSender sender, String... args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError((ConsoleCommandSender)sender, name);
            return;
        }

        if(args.length > 2) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        if(args.length == 0 || 1 == args.length && args[0].equalsIgnoreCase("default")) {
            Player p = (Player)sender;
            p.setFlySpeed(DEFAULT_FLY_SPEED);
            p.setWalkSpeed(DEFAULT_WALK_SPEED);
            this.sendSpeedMessage(sender, DEFAULT_CUSTOM_SPEED);
            return;
        }

        try {
            float i = Float.parseFloat(args[0]);

            if(i <= 0F || i > MAX_SPEED) {
                sender.sendMessage(Config.err() + "Speed must be between 0 and 50 (Default 10)");
                return;
            }

            i /= CONVERSION_FACTOR;

            if(args.length == 2) {
                Player p = Bukkit.getPlayer(args[1]);
                if(p == null) {
                    sender.sendMessage(Config.err() + "Couldn't find player " + args[1]);
                    return;
                }

                p.setFlySpeed(i/2);
                p.setWalkSpeed(i);

                this.sendSpeedMessage(sender, Float.parseFloat(args[0]), p.getDisplayName());
            } else {
                Player p = (Player) sender;

                this.sendSpeedMessage(sender, Float.parseFloat(args[0]));

                p.setFlySpeed(i/2);
                p.setWalkSpeed(i);
            }
        } catch(NumberFormatException e) {
            Command.sendSyntaxError(sender, this);
        }
    }

    /**
     * Sends a player a message informing him or her that his or her speed has been changed.
     *
     * @param p The player to send the message to.
     * @param speed The new speed.
     */
    private void sendSpeedMessage(CommandSender p, Float speed) {
        if(Command.messageEnabled(this, false))
            p.sendMessage(M.m("command-message-text.setspeed", speed.toString()));
    }

    /**
     * Sends a player a message informing him or her that another player's speed has been changed.
     *
     * @param p The player to send the message to.
     * @param speed The new speed.
     * @param n The other player.
     */
    private void sendSpeedMessage(CommandSender p, Float speed, String n) {
        Player z = Bukkit.getPlayer(n);
        assert z != null;

        if(z.equals(p)) {
            this.sendSpeedMessage(p, speed);
            return;
        }

        if(Command.messageEnabled(this, false))
            p.sendMessage(M.m("command-message-text.setspeed-others-s", n, speed.toString()));
        if(Command.messageEnabled(this, true))
            z.sendMessage(M.m("command-message-text.setspeed-others-r", speed.toString(), p.getName()));
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return args.length >= 2 && Bukkit.getPlayer(args[1]) != null && !Bukkit.getPlayer(args[1]).equals(sender) ? permission + ".others" : permission;
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
