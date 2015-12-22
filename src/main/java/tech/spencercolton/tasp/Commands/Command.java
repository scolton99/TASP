package tech.spencercolton.tasp.Commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static final ConcurrentHashMap<String,TASPCommand> cmds = new ConcurrentHashMap<>();

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
     *     command processing that transfers all of the necessary arguments to a more simple command executor that
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
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        String s = cmd.getName();

        TASPCommand c = this.getCommand(s.toLowerCase());
        if(c == null)
            return false;

        boolean hasPermission = false;

        if(sender instanceof Player) {
            Player p = (Player)sender;
            if(p.hasPermission(c.predictRequiredPermission(sender, args)))
                    hasPermission = true;
        } else if(sender instanceof ConsoleCommandSender) {
            hasPermission = true;
        }

        if(!hasPermission) {
            sendPermissionError(sender);
            return true;
        }

        return this.executeCommand(s, sender, args);
    }

    /**
     * The method used to find a command's executor and execute it using the given arguments.
     *
     * @param name The name of the command.
     * @param sender Who sent the command.
     * @param args The arguments of the command, supplied by the player.
     * @return {@code true} if the command was found in the list of available commands, else {@code false}.
     */
    private boolean executeCommand(String name, CommandSender sender, String... args) {
        if(Collections.list(cmds.keys()).contains(name.toLowerCase())) {
            cmds.get(name.toLowerCase()).execute(sender, args);
            return true;
        }
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
        cmds.put(SetspawnCmd.name.toLowerCase(), new SetspawnCmd());
        cmds.put(TimeCmd.name.toLowerCase(), new TimeCmd());
        cmds.put(TopCmd.name.toLowerCase(), new TopCmd());
        cmds.put(AFKCmd.name.toLowerCase(), new AFKCmd());
        cmds.put(XYZCmd.name.toLowerCase(), new XYZCmd());
        cmds.put(MuteCmd.name.toLowerCase(), new MuteCmd());
        cmds.put(MeCmd.name.toLowerCase(), new MeCmd());
        cmds.put(MessageCmd.name.toLowerCase(), new MessageCmd());
        cmds.put(ReplyCmd.name.toLowerCase(), new ReplyCmd());
        cmds.put(GamemodeCmd.name.toLowerCase(), new GamemodeCmd());
        cmds.put(BlockCmd.name.toLowerCase(), new BlockCmd());
        cmds.put(TASPCmd.name.toLowerCase(), new TASPCmd());
        cmds.put(UnblockCmd.name.toLowerCase(), new UnblockCmd());
        cmds.put(BroadcastCmd.name.toLowerCase(), new BroadcastCmd());
        cmds.put(PowertoolCmd.name.toLowerCase(), new PowertoolCmd());
        cmds.put(PowertoolToggleCmd.name.toLowerCase(), new PowertoolToggleCmd());
        cmds.put(StalkerCmd.name.toLowerCase(), new StalkerCmd());
        cmds.put(InfoCmd.name.toLowerCase(), new InfoCmd());
        cmds.put(SrvInfoCmd.name.toLowerCase(), new SrvInfoCmd());
        cmds.put(StahpCmd.name.toLowerCase(), new StahpCmd());
        cmds.put(ShockCmd.name.toLowerCase(), new ShockCmd());
        cmds.put(BurnCmd.name.toLowerCase(), new BurnCmd());
        cmds.put(FeedCmd.name.toLowerCase(), new FeedCmd());
        cmds.put(StarveCmd.name.toLowerCase(), new StarveCmd());
        cmds.put(HealCmd.name.toLowerCase(), new HealCmd());
        cmds.put(HurtCmd.name.toLowerCase(), new HurtCmd());
        cmds.put(FOMCmd.name.toLowerCase(), new FOMCmd());
        cmds.put(PotionCmd.name.toLowerCase(), new PotionCmd());
        cmds.put(AntidoteCmd.name.toLowerCase(), new AntidoteCmd());
        cmds.put(BuddhaCmd.name.toLowerCase(), new BuddhaCmd());

        Collection<TASPCommand> coll = cmds.values();

        for(TASPCommand c : coll) {
            if(c.getAliases() == null)
                continue;
            for(String g : c.getAliases()) {
                cmds.put(g, c);
            }
        }
    }

    /**
     * A method to send an error message to the console informing it that the command cannot be run from the console.
     * <p>
     *     Internally calls {@link #sendConsoleError(ConsoleCommandSender, String)}.
     * </p>
     *
     * @param s A console command sender to whom the message will be sent.
     */
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

    public static void sendConsoleSyntaxError(CommandSender s, TASPCommand c) {
        assert s instanceof ConsoleCommandSender;
        sendConsoleSyntaxError((ConsoleCommandSender)s, c);
    }

    public static void sendGenericSyntaxError(CommandSender s, TASPCommand c) {
        if(s instanceof ConsoleCommandSender)
            sendConsoleSyntaxError(s, c);
        else
            sendSyntaxError(s, c);
    }

    private static void sendPermissionError(CommandSender s) {
        s.sendMessage(Config.err() + "You do not have permission to do that.");
    }

    private TASPCommand getCommand(String s) {
        return cmds.get(s);
    }

    public static boolean messageEnabled(TASPCommand c, boolean others) {
        if(others)
            return Config.getBoolean("command-messages." + c.getName() + "-others");
        else
            return Config.getBoolean("command-messages." + c.getName());
    }

    public static boolean messageEnabled(String s) {
        return Config.getBoolean("command-messages." + s);
    }

    public static void sendWorldMessage(CommandSender sender, String world) {
        sender.sendMessage(Config.err() + "Couldn't find world \"" + world + "\"");
    }

    public static void sendPlayerMessage(CommandSender s, String p) {
        s.sendMessage(Config.err() + "Couldn't find player \"" + p + "\"");
    }

    public static List<String> processQuotedArguments(String... args) {
        String larg = "";
        for(int i = 0; i < args.length; i++) {
            larg += args[i];
            if(!((i + 1) >= args.length))
                larg += " ";
        }

        List<String> newArgs = new ArrayList<>();
        Pattern r = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
        Matcher rm = r.matcher(larg);
        while(rm.find()) {
            if(rm.group(1) != null) {
                newArgs.add(rm.group(1));
            } else if(rm.group(2) != null) {
                newArgs.add(rm.group(2));
            } else {
                newArgs.add(rm.group());
            }
        }

        return newArgs;
    }

    public static String getDisplayName(CommandSender sender) {
        if(sender instanceof ConsoleCommandSender) {
            return sender.getName();
        }

        Player p = (Player)sender;
        return p.getDisplayName();
    }

}
