package tech.spencercolton.tasp.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;


public class HomeCmd extends TASPCommand {

    public static final String name = "home";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.RED + "Sorry, this command can only be used in-game.");
            return;
        }

        if(args.length != 0) {
            sender.sendMessage("Proper command usage: " );
        }


    }

}
