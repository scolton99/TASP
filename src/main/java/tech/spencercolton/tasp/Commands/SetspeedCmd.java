package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static java.lang.Float.*;
import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Util.Message.Setspeed.Error.*;
import static tech.spencercolton.tasp.Util.Message.Setspeed.*;

/**
 * The {@link TASPCommand} object containing the runtime information for the {@code setspeed} command.
 * <p>
 * <table summary="Properties">
 * <tr>
 * <th style="font-weight:bold;">Property</th>
 * <th style="font-weight:bold;">Value</th>
 * </tr>
 * <tr>
 * <td>
 * Name
 * </td>
 * <td>
 * {@value name}
 * </td>
 * </tr>
 * <tr>
 * <td>
 * Permission
 * </td>
 * <td>
 * {@code tasp.setspeed}
 * <br>
 * {@code tasp.setspeed.others}
 * </td>
 * </tr>
 * <tr>
 * <td>
 * Syntax
 * </td>
 * <td>
 * /setspeed &lt;speed&gt; [player]
 * </td>
 * </tr>
 * <tr>
 * <td>
 * Console Syntax
 * </td>
 * <td>
 * /setspeed &lt;speed&gt; &lt;player&gt;
 * </td>
 * </tr>
 * </table>
 */
public class SetspeedCmd extends TASPCommand {

    /**
     * String containing the command's name.
     */
    @Getter
    private static final String name = "setspeed";

    /**
     * String containing the command's syntax.
     */
    @Getter
    private final String syntax = "/setspeed <speed> [player]";

    /**
     * String containing the command's console syntax.
     */
    @Getter
    private final String consoleSyntax = "/setspeed <speed> <player>";

    @Getter
    private final String permission = "tasp.setspeed";

    private static final float DEFAULT_FLY_SPEED = 5.0F;
    private static final float DEFAULT_WALK_SPEED = 10.0F;
    private static final float MAX_SPEED = 50.0F;
    private static final float CONVERSION_FACTOR = 50.0F;

    @Override
    public void execute(CommandSender sender, String... args) {
        if (sender instanceof ConsoleCommandSender && args.length != 2) {
            sendConsoleSyntaxError(sender, this);
            return;
        }

        Player p = null;
        Float walkSpeed = null, flySpeed = null;
        switch (args.length) {
            case 2: {
                p = getPlayer(args[1]);
                if (p == null) {
                    sendPlayerMessage(sender, args[1]);
                    return;
                }
            }
            case 1: {
                try {
                    walkSpeed = parseFloat(args[0]);
                    if (walkSpeed <= 0.0F || walkSpeed > MAX_SPEED) {
                        sendSpeedOOBMessage(sender);
                        return;
                    }
                    flySpeed = walkSpeed / 2.0F;

                } catch (NumberFormatException e) {
                    sendSyntaxError(sender, this);
                }
            }
            case 0: {
                if (p == null) {
                    assert sender instanceof Player;
                    p = (Player) sender;
                }

                if (walkSpeed == null)
                    walkSpeed = DEFAULT_WALK_SPEED;
                if (flySpeed == null)
                    flySpeed = DEFAULT_FLY_SPEED;

                walkSpeed /= CONVERSION_FACTOR;
                flySpeed /= CONVERSION_FACTOR;

                assert flySpeed != null;
                assert walkSpeed != null;

                p.setFlySpeed(flySpeed);
                p.setWalkSpeed(walkSpeed);

                sendSpeedMessage(sender, walkSpeed * CONVERSION_FACTOR, p);
                return;
            }
            default: {
                sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return args.length >= 2 && getPlayer(args[1]) != null && !getPlayer(args[1]).equals(sender) ? permission + ".others" : permission;
    }

}
