package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SetspeedCmd extends TASPCommand{

    public static final String name = "setspeed";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.RED + "Sorry, this command can only be used in-game.");
            return;
        }

        if(args.length > 2) {
            sender.sendMessage(ChatColor.RED + "Command syntax is /setspeed <speed> [player]");
            return;
        }

        try {
            float i = Float.parseFloat(args[0]);

            if(i <= 0F || i >  50F) {
                sender.sendMessage(ChatColor.RED + "Speed must be between 0 and 50");
                return;
            }

            if(args.length == 2) {
                Player p = Bukkit.getPlayer(args[1]);
                if(p == null) {
                    sender.sendMessage(ChatColor.RED + "Couldn't find player " + args[1]);
                    return;
                }

                p.setFlySpeed(i);
                p.setWalkSpeed(i);
            } else {
                Player p = (Player) sender;

                p.setFlySpeed(i);
                p.setWalkSpeed(i);
            }
        } catch(NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Command syntax is /setspeed <speed> [player]");
        }
    }

}
