package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;

import java.util.HashMap;

/**
 * A class for managing {@link TASPCommand}s and general command maintenance.
 * <p>
 *     Provides error-related functions for when players and console senders fail to properly use the command.
 * </p>
 *
 * @author Spencer Colton
 * @since 0.0.1-001
 */
public class Command implements CommandExecutor{

    /**
     * {@code HashMap} mapping familiar plugin names to their classes.
     * <p>
     *     Populated when the Command is instantiated.
     * </p>
     */
    private static HashMap<String,TASPCommand> cmds = new HashMap<>();

    /**
     * Not designed as a constructor, but as a convenient way for the class to populate the {@link #cmds} field
     * so that commands can be processed successfully.
     */
    public Command() {
        populateList();
    }

    /**
     * The server-required {@code onCommand} method that passes only the required information to command processors.
     * <p>
     *     Because the server-required arguments were verbose and often unnecessary, TASP implements a new class for
     *     command processing that transfers all of the necessary argments to a more simple command executor that
     *     does not return a boolean value, as it expected that all relevant information will be supplied directly to
     *     the user in the form of messages.
     * </p>
     *
     * @param sender The sender, supplied by the server.
     * @param cmd The command, supplied by the server.
     * @param label The command's label, supplied by the server.
     * @param args The command's arguments, supplied by the server.
     * @return {@code true} if the command was found in the list of available commands, else {@code false}.
     */
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        String s = cmd.getName();

        TASPCommand c = getCommand(s.toLowerCase());
        if(c == null)
            return false;

        boolean hasPermission = false;

        if(sender instanceof Player) {
            Player p = (Player)sender;
            if(c.predictOthers(sender, args)) {
                if (p.hasPermission(c.getPermission() + ".others")) {
                    hasPermission = true;
                }
            }
            else
                if(p.hasPermission(c.getPermission()))
                    hasPermission = true;
        } else if(sender instanceof ConsoleCommandSender) {
            hasPermission = true;
        }

        if(!hasPermission) {
            sendPermissionError(sender);
            return true;
        }

        return executeCommand(s, sender, args);
    }

    /**
     * The method used to find a command's executor and execute it using the given arguments.
     *
     * @param name The name of the command.
     * @param sender Who sent the command.
     * @param args The arguments of the command, supplied by the player.
     * @return {@code true} if the command was found in the list of available commands, else {@code false}.
     */
    private boolean executeCommand(String name, CommandSender sender, String[] args) {
        if(cmds.keySet().contains(name.toLowerCase())) {
            cmds.get(name.toLowerCase()).execute(sender, args);
            return true;
        }
        Bukkit.getLogger().info("Command: " + name);
        return false;
    }

    /**
     * Run at class instantiation, this method fills the {@link #cmds} field with all available commands.
     */
    private static void populateList() {
        cmds.put(KillallCmd.name.toLowerCase(), new KillallCmd());
        cmds.put(HomeCmd.name.toLowerCase(), new HomeCmd());
        cmds.put(SetspeedCmd.name.toLowerCase(), new SetspeedCmd());
        cmds.put(FlyCmd.name.toLowerCase(), new FlyCmd());
        cmds.put(SethomeCmd.name.toLowerCase(), new SethomeCmd());
        cmds.put(GodCmd.name.toLowerCase(), new GodCmd());
        cmds.put(SetspawnCmd.name.toLowerCase(), new SetspeedCmd());
        cmds.put(TimeCmd.name.toLowerCase(), new TimeCmd());
    }

    /**
     * A method to send an error message to the console informing it that the command cannot be run from the console.
     * <p>
     *     Internally calls {@link #sendConsoleError(ConsoleCommandSender, String)}.
     * </p>
     *
     * @param s A console command sender to whom the message will be sent.
     */
    @SuppressWarnings("unused")
    public static void sendConsoleError(ConsoleCommandSender s) {
        sendConsoleError(s, "this command");
    }

    /**
     * A method to send an error message to the console informing it that the command cannot be run from the console.
     *
     * @param s A console command sender to whom the message will be sent.
     * @param command The name of the command that cannot be executed from the console.
     */
    public static void sendConsoleError(ConsoleCommandSender s, String command) {
        if(!command.equals("this command"))
            command = "\"" + command + "\"";
        s.sendMessage(Config.err() + "Sorry, " + command + " can only be run in-game.");
    }

    /**
     * A method to send an error message a player informing him or her that his or her syntax was invalid.
     *
     * @param s A {@code CommandSender} (in the form of a player) to whom the message will be sent.
     * @param c The command that was executed incorrectly.
     */
    public static void sendSyntaxError(CommandSender s, TASPCommand c) {
        s.sendMessage(Config.err() + "Invalid syntax! Try: " + c.getSyntax());
    }

    /**
     * A method to send an error message to the console informing it that its syntax was invalid.
     *
     * @param s A {@code ConsoleCommandSender} to whom the message will be sent.
     * @param c The command that was executed incorrectly.
     */
    public static void sendConsoleSyntaxError(ConsoleCommandSender s, TASPCommand c) {
        s.sendMessage(Config.err() + "Invalid syntax! Try: " + c.getConsoleSyntax());
    }

    public static void sendPermissionError(CommandSender s) {
        s.sendMessage(Config.err() + "You do not have permission to do that.");
    }

    private TASPCommand getCommand(String s) {
        return cmds.get(s);
    }

    public static boolean messageEnabled(TASPCommand c, boolean others) {
        Bukkit.getLogger().info("command-messages." + c.getName());
        if(others)
            return Config.getBoolean("command-messages." + c.getName() + ".others");
        else
            return Config.getBoolean("command-messages." + c.getName());
    }

    public static void sendWorldMessage(CommandSender sender, String world) {
        sender.sendMessage(Config.err() + "Couldn't find world \"" + world + "\"");
    }

    public static void sendPlayerMessage(CommandSender s, String p) {
        s.sendMessage(Config.err() + "Couldn't find player \"" + p + "\"");
    }

    private boolean extraPermissionHandler(){return false;}

}
