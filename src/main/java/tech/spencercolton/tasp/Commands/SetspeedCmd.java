package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;
import tech.spencercolton.tasp.Util.Message;

import java.io.Console;

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
    @Getter
    private static final String syntax = "/setspeed <speed> [player]";

    /**
     * String containing the command's console syntax.
     */
    @Getter
    private static final String consoleSyntax = "/setspeed <speed> <player>";

    @Getter
    private static final String permission = "tasp.setspeed";

    private static final float DEFAULT_FLY_SPEED = 5.0F;
    private static final float DEFAULT_WALK_SPEED = 2.5F;
    private static final float MAX_SPEED = 50.0F;
    private static final float CONVERSION_FACTOR = 50.0F;

    @Override
    public void execute(CommandSender sender, String... args) {
        if(sender instanceof ConsoleCommandSender && args.length != 2) {
            Command.sendConsoleSyntaxError(sender, this);
            return;
        }

        Player p = null;
        Float walkSpeed = null, flySpeed = null;
        switch(args.length) {
            case 2: {
                p = Bukkit.getPlayer(args[1]);
                if(p == null) {
                    Command.sendPlayerMessage(sender, args[1]);
                    return;
                }
            }
            case 1: {
                try {
                    walkSpeed = Float.parseFloat(args[0]);
                    if(walkSpeed <= 0.0F || walkSpeed >= MAX_SPEED) {
                        Message.Setspeed.Error.sendSpeedOOBMessage(sender);
                        return;
                    }
                    flySpeed = walkSpeed / 2.0F;
                    if(p == null) {
                        assert sender instanceof Player;
                        p = (Player) sender;
                    }

                } catch(NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                }
            }
            case 0: {
                if(walkSpeed == null)
                    walkSpeed = DEFAULT_WALK_SPEED;
                if(flySpeed == null)
                    flySpeed = DEFAULT_FLY_SPEED;

                walkSpeed /= CONVERSION_FACTOR;
                flySpeed /= CONVERSION_FACTOR;

                assert p != null;

                p.setFlySpeed(flySpeed);
                p.setWalkSpeed(walkSpeed);

                Message.Setspeed.sendSpeedMessage(sender, walkSpeed * CONVERSION_FACTOR, p);
                return;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return args.length >= 2 && Bukkit.getPlayer(args[1]) != null && !Bukkit.getPlayer(args[1]).equals(sender) ? permission + ".others" : permission;
    }

}
