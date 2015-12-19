package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.M;

import java.util.Arrays;
import java.util.List;

public class GamemodeCmd extends TASPCommand {

    public static final String syntax = "/gamemode <mode> [player]";
    public static final String consoleSyntax = "/gamemode <mode> <player>";
    public static final String name = "gamemode";
    public static final String permission = "tasp.gamemode";

    @Override
    public void execute(CommandSender sender, String[] args) {
        String[] aargs = {"1", "0", "2", "survival", "creative", "adventure", "a", "c", "s"};
        List<String> accept = Arrays.asList(aargs);

        if(sender instanceof ConsoleCommandSender) {
            if(args.length != 2){
                Command.sendConsoleSyntaxError((ConsoleCommandSender)sender, this);
                return;
            }

            if(!accept.contains(args[0])) {
                Command.sendConsoleSyntaxError((ConsoleCommandSender)sender, this);
                return;
            }

            if(Bukkit.getPlayer(args[1]) == null) {
                Command.sendPlayerMessage(sender, args[1]);
                return;
            }

            Player p = Bukkit.getPlayer(args[1]);
            switch(args[0]) {
                case "1":
                case "creative":
                case "c":
                    p.setGameMode(GameMode.CREATIVE);
                    break;
                case "2":
                case "adventure":
                case "a":
                    p.setGameMode(GameMode.ADVENTURE);
                    break;
                case "0":
                case "survival":
                case "s":
                    p.setGameMode(GameMode.SURVIVAL);
                    break;
            }
            sendGamemodeMessage(sender, p.getGameMode().toString().toLowerCase(), p);
        } else {
            if(args.length > 2 || args.length < 1) {
                Command.sendSyntaxError(sender, this);
                return;
            }

            if(!accept.contains(args[0])) {
                Command.sendSyntaxError(sender, this);
                return;
            }

            Player p = (Player)sender;

            switch(args.length) {
                case 1:
                    switch(args[0]) {
                        case "1":
                        case "creative":
                        case "c":
                            p.setGameMode(GameMode.CREATIVE);
                            break;
                        case "2":
                        case "adventure":
                        case "a":
                            p.setGameMode(GameMode.ADVENTURE);
                            break;
                        case "0":
                        case "survival":
                        case "s":
                            p.setGameMode(GameMode.SURVIVAL);
                            break;
                    }
                    sendGamemodeMessage(sender, p.getGameMode().toString().toLowerCase());
                    break;
                case 2:
                    Player p2 = Bukkit.getPlayer(args[1]);
                    if(p2 == null) {
                        Command.sendPlayerMessage(sender, args[1]);
                        return;
                    }
                    switch(args[0]) {
                        case "1":
                        case "creative":
                        case "c":
                            p2.setGameMode(GameMode.CREATIVE);
                            break;
                        case "2":
                        case "adventure":
                        case "a":
                            p2.setGameMode(GameMode.ADVENTURE);
                            break;
                        case "0":
                        case "survival":
                        case "s":
                            p2.setGameMode(GameMode.SURVIVAL);
                            break;
                    }
                    sendGamemodeMessage(sender, p2.getGameMode().toString().toLowerCase(), p2);
                    break;
                default:
                    Command.sendSyntaxError(sender, this);
                    break;
            }
        }
    }



    private void sendGamemodeMessage(CommandSender s, String p) {
        if(Command.messageEnabled(this, false))
            s.sendMessage(M.m("command-message-text.gamemode", s.getName(), p));
    }

    private void sendGamemodeMessage(CommandSender s, String p, Player x) {
        if(x.equals(s)) {
            sendGamemodeMessage(s, p);
            return;
        }

        if(Command.messageEnabled(this, false))
            s.sendMessage(M.m("command-message-text.gamemode-others-s", p, x.getDisplayName()));
        if(Command.messageEnabled(this, true))
            x.sendMessage(M.m("command-message-text.gamemode-others-r", p, s.getName()));
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
    public String getName() {
        return name;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public boolean predictOthers(CommandSender sender, String[] args) {
        return args.length >= 1 && Bukkit.getPlayer(args[1]) != null && !Bukkit.getPlayer(args[1]).equals(sender);
    }

}
