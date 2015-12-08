package tech.spencercolton.tasp.Commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class Command implements CommandExecutor{

    private static HashMap<String,TASPCommand> cmds = new HashMap<>();

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        String s = cmd.getName();

        return executeCommand(s, sender, args);
    }

    private boolean executeCommand(String name, CommandSender sender, String[] args) {
        if(cmds.keySet().contains(name.toLowerCase())) {
            cmds.get(name.toLowerCase()).execute(sender, args);
            return true;
        }
        return false;
    }

    public static void populateList() {
        cmds.put(KillallCmd.name.toLowerCase(), new KillallCmd());
        cmds.put(HomeCmd.name.toLowerCase(), new HomeCmd());
        cmds.put(SetspeedCmd.name.toLowerCase(), new SetspeedCmd());
    }

}
