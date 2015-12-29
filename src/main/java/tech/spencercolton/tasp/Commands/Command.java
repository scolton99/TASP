package tech.spencercolton.tasp.Commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class for managing {@link TASPCommand}s and general command maintenance.
 * <p>
 * Provides error-related functions for when players and console senders fail to properly use the command.
 * </p>
 *
 * @author Spencer Colton
 * @since 0.0.1-001
 */
public class Command implements CommandExecutor {

    /**
     * {@code HashMap} mapping familiar plugin names to their classes.
     * <p>
     * Populated when the Command is instantiated.
     * </p>
     */
    private static final ConcurrentHashMap<String, TASPCommand> cmds = new ConcurrentHashMap<>();

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
     * Because the server-required arguments were verbose and often unnecessary, TASP implements a new class for
     * command processing that transfers all of the necessary arguments to a more simple command executor that
     * does not return a boolean value, as it expected that all relevant information will be supplied directly to
     * the user in the form of messages.
     * </p>
     *
     * @param sender The sender, supplied by the server.
     * @param cmd    The command, supplied by the server.
     * @param label  The command's label, supplied by the server.
     * @param args   The command's arguments, supplied by the server.
     * @return {@code true} if the command was found in the list of available commands, else {@code false}.
     */
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        String s = cmd.getName();

        TASPCommand c = this.getCommand(s.toLowerCase());
        if (c == null)
            return false;

        boolean hasPermission = false;

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission(c.predictRequiredPermission(sender, args)))
                hasPermission = true;
        } else if (sender instanceof ConsoleCommandSender) {
            hasPermission = true;
        }

        if (!hasPermission) {
            sendPermissionError(sender);
            return true;
        }

        return this.executeCommand(s, sender, args);
    }

    /**
     * The method used to find a command's executor and execute it using the given arguments.
     *
     * @param name   The name of the command.
     * @param sender Who sent the command.
     * @param args   The arguments of the command, supplied by the player.
     * @return {@code true} if the command was found in the list of available commands, else {@code false}.
     */
    private boolean executeCommand(String name, CommandSender sender, String... args) {
        if (Collections.list(cmds.keys()).contains(name.toLowerCase())) {
            if (sender instanceof ConsoleCommandSender && cmds.get(name.toLowerCase()).getConsoleSyntax() == null) {
                sendConsoleError(sender);
                return true;
            }
            cmds.get(name.toLowerCase()).execute(sender, args);
            return true;
        }
        return false;
    }

    /**
     * Run at class instantiation, this method fills the {@link #cmds} field with all available commands.
     */
    private static void populateList() {
        cmds.put(KillallCmd.getName().toLowerCase(), new KillallCmd());
        cmds.put(HomeCmd.getName().toLowerCase(), new HomeCmd());
        cmds.put(SetspeedCmd.getName().toLowerCase(), new SetspeedCmd());
        cmds.put(FlyCmd.getName().toLowerCase(), new FlyCmd());
        cmds.put(SethomeCmd.getName().toLowerCase(), new SethomeCmd());
        cmds.put(GodCmd.getName().toLowerCase(), new GodCmd());
        cmds.put(SetspawnCmd.getName().toLowerCase(), new SetspawnCmd());
        cmds.put(TimeCmd.getName().toLowerCase(), new TimeCmd());
        cmds.put(TopCmd.getName().toLowerCase(), new TopCmd());
        cmds.put(AFKCmd.getName().toLowerCase(), new AFKCmd());
        cmds.put(XYZCmd.getName().toLowerCase(), new XYZCmd());
        cmds.put(MuteCmd.getName().toLowerCase(), new MuteCmd());
        cmds.put(MeCmd.getName().toLowerCase(), new MeCmd());
        cmds.put(MessageCmd.getName().toLowerCase(), new MessageCmd());
        cmds.put(ReplyCmd.getName().toLowerCase(), new ReplyCmd());
        cmds.put(GamemodeCmd.getName().toLowerCase(), new GamemodeCmd());
        cmds.put(BlockCmd.getName().toLowerCase(), new BlockCmd());
        cmds.put(TASPCmd.getName().toLowerCase(), new TASPCmd());
        cmds.put(UnblockCmd.getName().toLowerCase(), new UnblockCmd());
        cmds.put(BroadcastCmd.getName().toLowerCase(), new BroadcastCmd());
        cmds.put(PowertoolCmd.getName().toLowerCase(), new PowertoolCmd());
        cmds.put(PowertoolToggleCmd.getName().toLowerCase(), new PowertoolToggleCmd());
        cmds.put(StalkerCmd.getName().toLowerCase(), new StalkerCmd());
        cmds.put(InfoCmd.getName().toLowerCase(), new InfoCmd());
        cmds.put(SrvInfoCmd.getName().toLowerCase(), new SrvInfoCmd());
        cmds.put(StahpCmd.getName().toLowerCase(), new StahpCmd());
        cmds.put(ShockCmd.getName().toLowerCase(), new ShockCmd());
        cmds.put(BurnCmd.getName().toLowerCase(), new BurnCmd());
        cmds.put(FeedCmd.getName().toLowerCase(), new FeedCmd());
        cmds.put(StarveCmd.getName().toLowerCase(), new StarveCmd());
        cmds.put(HealCmd.getName().toLowerCase(), new HealCmd());
        cmds.put(HurtCmd.getName().toLowerCase(), new HurtCmd());
        cmds.put(FOMCmd.getName().toLowerCase(), new FOMCmd());
        cmds.put(PotionCmd.getName().toLowerCase(), new PotionCmd());
        cmds.put(AntidoteCmd.getName().toLowerCase(), new AntidoteCmd());
        cmds.put(BuddhaCmd.getName().toLowerCase(), new BuddhaCmd());
        cmds.put(WeatherCmd.getName().toLowerCase(), new WeatherCmd());
        cmds.put(SpawnmobCmd.getName().toLowerCase(), new SpawnmobCmd());
        cmds.put(WorldCmd.getName().toLowerCase(), new WorldCmd());
        cmds.put(ExplodeCmd.getName().toLowerCase(), new ExplodeCmd());
        cmds.put(DropsCmd.getName().toLowerCase(), new DropsCmd());
        cmds.put(TeleportCmd.getName().toLowerCase(), new TeleportCmd());
        cmds.put(TeleportHereCmd.getName().toLowerCase(), new TeleportHereCmd());
        cmds.put(TeleportAllHereCmd.getName().toLowerCase(), new TeleportAllHereCmd());
        cmds.put(TeleportAcceptCmd.getName().toLowerCase(), new TeleportAcceptCmd());
        cmds.put(TeleportDenyCommand.getName().toLowerCase(), new TeleportDenyCommand());
        cmds.put(TeleportRequestCmd.getName().toLowerCase(), new TeleportRequestCmd());
        cmds.put(TeleportHereRequestCmd.getName().toLowerCase(), new TeleportHereRequestCmd());
        cmds.put(TeleportAllHereRequestCmd.getName().toLowerCase(), new TeleportAllHereRequestCmd());
        cmds.put(TeleportToggleCmd.getName().toLowerCase(), new TeleportToggleCmd());
        cmds.put(MailCmd.getName().toLowerCase(), new MailCmd());
        cmds.put(SpawnCmd.getName().toLowerCase(), new SpawnCmd());
        cmds.put(BackCmd.getName().toLowerCase(), new BackCmd());
        cmds.put(ShootCmd.getName().toLowerCase(), new ShootCmd());
        cmds.put(HoldingCmd.getName().toLowerCase(), new HoldingCmd());
        cmds.put(ItemCmd.getName().toLowerCase(), new ItemCmd());
        cmds.put(XPCmd.getName().toLowerCase(), new XPCmd());
        cmds.put(CraftCmd.getName().toLowerCase(), new CraftCmd());
        cmds.put(SuCmd.getName().toLowerCase(), new SuCmd());
        cmds.put(InvspyCmd.getName().toLowerCase(), new InvspyCmd());
        cmds.put(ClearCmd.getName().toLowerCase(), new ClearCmd());
        cmds.put(TeleportLocation.getName().toLowerCase(), new TeleportLocation());
        cmds.put(DisappearCmd.getName().toLowerCase(), new DisappearCmd());
        cmds.put(KillCmd.getName().toLowerCase(), new KillCmd());
        cmds.put(NickCmd.getName().toLowerCase(), new NickCmd());
        cmds.put(HelpmeCmd.getName().toLowerCase(), new HelpmeCmd());
        cmds.put(RecipeCmd.getName().toLowerCase(), new RecipeCmd());
        cmds.put(JumpCmd.getName().toLowerCase(), new JumpCmd());
        cmds.put(SetwarpCmd.getName().toLowerCase(), new SetwarpCmd());
        cmds.put(WarpCmd.getName().toLowerCase(), new WarpCmd());

        Collection<TASPCommand> coll = cmds.values();

        for (TASPCommand c : coll) {
            if (c.getAliases() == null)
                continue;
            for (String g : c.getAliases()) {
                cmds.put(g, c);
            }
        }
    }

    /**
     * A method to send an error message to the console informing it that the command cannot be run from the console.
     *
     * @param s A console command sender to whom the message will be sent.
     */
    public static void sendConsoleError(CommandSender s) {
        assert s instanceof ConsoleCommandSender;
        s.sendMessage(Config.err() + "Sorry, this command can only be run in-game.");
    }

    /**
     * A method to send an error message a player informing him or her that his or her syntax was invalid.
     *
     * @param s A {@code CommandSender} (in the form of a player) to whom the message will be sent.
     * @param c The command that was executed incorrectly.
     */
    public static void sendSyntaxError(CommandSender s, TASPCommand c) {
        assert s instanceof Player;
        s.sendMessage(Config.err() + "Invalid syntax! Try: " + c.getSyntax());
    }

    /**
     * A method to send an error message to the console informing it that its syntax was invalid.
     *
     * @param s A {@code ConsoleCommandSender} to whom the message will be sent.
     * @param c The command that was executed incorrectly.
     */
    private static void sendConsoleSyntaxError(ConsoleCommandSender s, TASPCommand c) {
        s.sendMessage(Config.err() + "Invalid syntax! Try: " + c.getConsoleSyntax());
    }

    public static void sendConsoleSyntaxError(CommandSender s, TASPCommand c) {
        assert s instanceof ConsoleCommandSender;
        sendConsoleSyntaxError((ConsoleCommandSender) s, c);
    }

    public static void sendGenericSyntaxError(CommandSender s, TASPCommand c) {
        if (s instanceof ConsoleCommandSender)
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
        for (int i = 0; i < args.length; i++) {
            larg += args[i];
            if (!((i + 1) >= args.length))
                larg += " ";
        }

        List<String> newArgs = new ArrayList<>();
        Pattern r = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
        Matcher rm = r.matcher(larg);
        while (rm.find()) {
            if (rm.group(1) != null) {
                newArgs.add(rm.group(1));
            } else if (rm.group(2) != null) {
                newArgs.add(rm.group(2));
            } else {
                newArgs.add(rm.group());
            }
        }

        return newArgs;
    }

    public static List<String> removeSpaces(String... args) {
        List<String> fin = new ArrayList<>();
        for (String s : args) {
            fin.add(s.replace(" ", "_"));
        }
        return fin;
    }

    public static String getDisplayName(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            return sender.getName();
        }

        Player p = (Player) sender;
        return p.getDisplayName();
    }

    public static String combineArgs(String... args) {
        return combineArgs(Arrays.asList(args), 0);
    }

    public static String combineArgs(int start, String... args) {
        return combineArgs(Arrays.asList(args), start);
    }

    private static String combineArgs(List<String> args, int start) {
        String fin = "";
        for (int i = start; i < args.size(); i++) {
            fin += args.get(i);
            if (!(i + 1 >= args.size()))
                fin += " ";
        }
        return fin;
    }

    public static void sendNegativeMessage(CommandSender s) {
        s.sendMessage(Config.err() + "Amount must be positive.");
    }

    public static void sendInvalidEntityMessage(CommandSender sender, String entity) {
        sender.sendMessage(Config.err() + "\"" + entity + "\" is not recognized as a valid entity.");
    }

    public static String listToCSV(List<String> org) {
        String fin = "";

        for(int i = 0; i < org.size(); i++) {
            fin += org.get(i);
            if(!((i + 1) >= org.size()))
                fin += ", ";
        }

        return fin;
    }

    public static void sendItemNotFoundMessage(CommandSender sender) {
        sender.sendMessage(Config.err() + "Sorry, couldn't find that item.");
    }

}
