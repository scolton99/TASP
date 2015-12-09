package tech.spencercolton.tasp.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;


public class HomeCmd extends TASPCommand {

    public static final String name = "home";
    public static final String syntax = "/home";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError((ConsoleCommandSender)sender, name);
            return;
        }

        if(args.length != 0) {
            sender.sendMessage("Proper command usage: " );
        }


    }

    @Override
    public String getSyntax() {
        return syntax;
    }

}
