package tech.spencercolton.tasp.Commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.HashMap;

public class Command implements CommandExecutor{

    private static HashMap<String,TASPCommand> cmds = new HashMap<>();

    public Command() {
        populateList();
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        String s = cmd.getName();

        return executeCommand(s, sender, args);
    }

    private boolean executeCommand(String name, CommandSender sender, String[] args) {
        if(cmds.keySet().contains(name.toLowerCase())) {
            cmds.get(name.toLowerCase()).execute(sender, args);
            return true;
        }
        Bukkit.getLogger().info("Command: " + name);
        return false;
    }

    private static void populateList() {
        cmds.put(KillallCmd.name.toLowerCase(), new KillallCmd());
        cmds.put(HomeCmd.name.toLowerCase(), new HomeCmd());
        cmds.put(SetspeedCmd.name.toLowerCase(), new SetspeedCmd());
    }

    public static void sendConsoleError(ConsoleCommandSender s) {
        sendConsoleError(s, "this command");
    }

    public static void sendConsoleError(ConsoleCommandSender s, String command) {
        if(!command.equals("this command"))
            command = "\"" + command + "\"";
        s.sendMessage(ChatColor.RED + "Sorry, " + command + " can only be run in-game.");
    }

    public static void sendSyntaxError(CommandSender s, TASPCommand c) {
        s.sendMessage(ChatColor.RED + "Invalid syntax! Try: " + c.getSyntax());
    }

    public static void sendConsoleSyntaxError(ConsoleCommandSender s, TASPCommand c) {
        s.sendMessage(ChatColor.RED + "Invalid syntax! Try: " + c.getConsoleSyntax());
    }

}
