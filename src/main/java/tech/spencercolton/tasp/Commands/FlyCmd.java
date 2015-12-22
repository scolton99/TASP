package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

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
    private static final String syntax = "/fly [user]";


    /**
     * String containing the command's console syntax.
     */
    private static final String consoleSyntax = "/fly <user>";

    private static final String permission = "tasp.fly";

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String... args) {
        if(sender instanceof ConsoleCommandSender) {
            if(args.length != 1) {
                Command.sendConsoleSyntaxError((ConsoleCommandSender)sender, this);
                return;
            }

            Player p = Bukkit.getPlayer(args[0]);

            if(p == null) {
                sender.sendMessage(Config.err() + "Couldn't find player \"" + args[0] + ".\"  Please try again.");
                return;
            }

            p.setAllowFlight(true);

            p.setFlying(!p.isFlying());

            this.sendFlyingMessage(sender, p.isFlying(), p.getDisplayName());
        } else {
            switch(args.length) {
                case 0:
                    Player p = (Player)sender;
                    p.setAllowFlight(true);
                    p.setFlying(!p.isFlying());
                    this.sendFlyingMessage(sender, p.isFlying());
                    return;
                case 1:
                    Player p2 = Bukkit.getPlayer(args[0]);
                    if(p2 == null) {
                        sender.sendMessage(Config.err() + "Couldn't find player \"" + args[0] + ".\"  Please try again.");
                        return;
                    }
                    p2.setAllowFlight(true);
                    p2.setFlying(!p2.isFlying());

                    if(!p2.equals(sender))
                        this.sendFlyingMessage(sender, p2.isFlying(), p2.getDisplayName());
                    else
                        this.sendFlyingMessage(sender, ((Player)sender).isFlying());
            }
        }
    }

    private void sendFlyingMessage(CommandSender sender, boolean flying) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.fly", flying ? "enabled" : "disabled"));
    }

    private void sendFlyingMessage(CommandSender sender, boolean flying, String n) {
        Player p = Bukkit.getPlayer(n);
        assert p != null;

        if(sender.equals(p)) {
            this.sendFlyingMessage(sender, flying);
            return;
        }

        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.fly-others-s", flying ? "enabled" : "disabled", n));
        if(Command.messageEnabled(this, true))
            p.sendMessage(M.m("command-message-text.fly-others-r", flying ? "enabled" : "disabled", sender.getName()));
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return args.length > 0 && Bukkit.getPlayer(args[0]) != null && !Bukkit.getPlayer(args[0]).equals(sender) ? permission + ".others" : permission;
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
