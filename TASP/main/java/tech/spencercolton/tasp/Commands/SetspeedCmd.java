package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;

public class SetspeedCmd extends TASPCommand{

    public static final String name = "setspeed";
    public static final String syntax = "/setspeed <speed> [player]";
    public static final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError((ConsoleCommandSender)sender, name);
            return;
        }

        if(args.length > 2) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        try {
            float i = Float.parseFloat(args[0]);

            if(i <= 0F || i >  50F) {
                sender.sendMessage(ChatColor.RED + "Speed must be between 0 and 50");
                return;
            }

            i /= 50;

            if(args.length == 2) {
                Player p = Bukkit.getPlayer(args[1]);
                if(p == null) {
                    sender.sendMessage(ChatColor.RED + "Couldn't find player " + args[1]);
                    return;
                }

                Person.get(p).setStat("speed", i);

                p.setFlySpeed(i);
                p.setWalkSpeed(i);
            } else {
                Player p = (Player) sender;

                Person.get(p).setStat("speed", i);

                p.setFlySpeed(i);
                p.setWalkSpeed(i);
            }
        } catch(NumberFormatException e) {
            Command.sendSyntaxError(sender, this);
        }
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getConsoleSyntax() {
        return consoleSyntax;
    }

}
