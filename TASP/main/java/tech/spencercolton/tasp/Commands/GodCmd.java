package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

import java.util.ArrayList;
import java.util.List;

public class GodCmd extends TASPCommand {

    public static final String name = "god";
    public static final String syntax = "/god [player]";
    public static final String consoleSyntax = "/god <player>";
    public static final String permission = "tasp.god";

    public static List<Person> gods = new ArrayList<>();

    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            if(args.length != 1) {
                Command.sendConsoleSyntaxError((ConsoleCommandSender)sender, this);
                return;
            }

            Person p = Person.get(Bukkit.getPlayer(args[0]));
            if(gods.contains(p)) {
                p.getData().setBoolean("god", false);
                gods.remove(p);
            } else {
                p.getData().setBoolean("god", true);
                gods.add(p);
            }
        } else {
            switch(args.length) {
                case 0:
                    Person p = Person.get((Player)sender);
                    if(gods.contains(p)) {
                        p.getData().setBoolean("god", false);
                        gods.remove(p);
                        sendGodMessage(p.getPlayer(), false);
                    } else {
                        p.getData().setBoolean("god", true);
                        gods.add(p);
                        sendGodMessage(p.getPlayer(), true);
                    }
                    return;
                case 1:
                    Person p2 = Person.get(Bukkit.getPlayer(args[0]));

                    if(p2 == null) {
                        sendPlayerError(sender, args[0]);
                        return;
                    }

                    if(gods.contains(p2)) {
                        p2.getData().setBoolean("god", false);
                        gods.remove(p2);
                        if(!p2.equals(sender))
                            sendGodMessage(p2.getPlayer(), false, (Player)sender);
                        else
                            sendGodMessage(p2.getPlayer(), false);
                    } else {
                        p2.getData().setBoolean("god", true);
                        gods.add(p2);
                        if(!p2.equals(sender))
                            sendGodMessage(p2.getPlayer(), true, (Player)sender);
                        else
                            sendGodMessage(p2.getPlayer(), true);
                    }
                    break;
            }
        }
    }

    private void sendPlayerError(CommandSender sender, String p) {
        sender.sendMessage(Config.err() + "Couldn't find player " + p);
    }

    private void sendGodMessage(Player p, boolean t) {
        if(Command.messageEnabled(this, false))
            p.sendMessage(M.m("command-message-text.god", (t ? "enabled" : "disabled")));
    }

    private void sendGodMessage(Player p, boolean t, Player other) {
        if(p.equals(other)) {
            sendGodMessage(p, t);
            return;
        }

        if(Command.messageEnabled(this, false))
            other.sendMessage(M.m("command-message-text.god-others-s", (t ? "enabled" : "disabled"), p.getDisplayName()));
        if(Command.messageEnabled(this, true))
            p.sendMessage(M.m("command-message-text.god-others-r", (t ? "enabled" : "disabled"), other.getDisplayName()));
    }

    public boolean predictOthers(String[] s) {
        if(s.length > 0) {
            Player p = Bukkit.getPlayer(s[0]);
            if(p != null)
                return true;
        }
        return false;
    }

    public String getSyntax() {
        return syntax;
    }

    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    public String getPermission() {
        return permission;
    }

    public String getName() {
        return name;
    }

}
