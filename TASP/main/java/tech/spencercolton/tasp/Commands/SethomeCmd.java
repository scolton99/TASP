package tech.spencercolton.tasp.Commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;

public class SethomeCmd extends TASPCommand {

    public static final String syntax = "/sethome";
    public static final String consoleSyntax = null;
    public static final String name = "sethome";
    public static final String permission = "tasp.sethome";

    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError((ConsoleCommandSender)sender, name);
            return;
        }

        if(args.length != 0) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Person p = Person.get((Player)sender);
        p.setHome(((Player)sender).getLocation());

        sendHomeMessage(sender, p.getHome());
    }

    public String getSyntax() {
        return syntax;
    }

    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    private void sendHomeMessage(CommandSender s, Location l) {
        s.sendMessage(Config.c1() + "Your home location was set to " + ChatColor.DARK_RED + l.getX() + " " + l.getY() + " " + l.getZ() + Config.c1() + ".");
    }

}
