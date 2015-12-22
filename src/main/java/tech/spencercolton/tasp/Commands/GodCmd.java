package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

import java.util.Collections;
import java.util.List;

public class GodCmd extends TASPCommand {

    public static final String name = "god";
    private static final String syntax = "/god [player]";
    private static final String consoleSyntax = "/god <player>";
    private static final String permission = "tasp.god";

    @Override
    public void execute(CommandSender sender, String... args) {
        if(sender instanceof ConsoleCommandSender) {
            if(args.length != 1) {
                Command.sendConsoleSyntaxError((ConsoleCommandSender)sender, this);
                return;
            }

            Person p = Person.get(Bukkit.getPlayer(args[0]));
            if(p.isGod()) {
                p.setGod(false);
            } else {
                p.setGod(true);
            }
        } else {
            switch(args.length) {
                case 0:
                    Person p = Person.get((Player)sender);
                    if(p.isGod()) {
                        p.setGod(false);
                        this.sendGodMessage(p.getPlayer(), false);
                    } else {
                        p.setGod(true);
                        this.sendGodMessage(p.getPlayer(), true);
                    }
                    return;
                case 1:
                    Person p2 = Person.get(Bukkit.getPlayer(args[0]));

                    if(p2 == null) {
                        this.sendPlayerError(sender, args[0]);
                        return;
                    }

                    if(p2.isGod()) {
                        p2.setGod(false);
                        if(!p2.equals(sender))
                            this.sendGodMessage(p2.getPlayer(), false, (Player)sender);
                        else
                            this.sendGodMessage(p2.getPlayer(), false);
                    } else {
                        p2.setGod(true);
                        if(!p2.equals(sender))
                            this.sendGodMessage(p2.getPlayer(), true, (Player)sender);
                        else
                            this.sendGodMessage(p2.getPlayer(), true);
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
            p.sendMessage(M.m("command-message-text.god", t ? "enabled" : "disabled"));
    }

    private void sendGodMessage(Player p, boolean t, Player other) {
        if(p.equals(other)) {
            this.sendGodMessage(p, t);
            return;
        }

        if(Command.messageEnabled(this, false))
            other.sendMessage(M.m("command-message-text.god-others-s", t ? "enabled" : "disabled", p.getDisplayName()));
        if(Command.messageEnabled(this, true))
            p.sendMessage(M.m("command-message-text.god-others-r", t ? "enabled" : "disabled", other.getDisplayName()));
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... s) {
        return s.length > 0 && Bukkit.getPlayer(s[0]) != null && !Bukkit.getPlayer(s[0]).equals(sender) ? permission + ".others" : permission;
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getName() {
        return name;
    }

}
