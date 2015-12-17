package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GamemodeCmd extends TASPCommand {

    public static final String syntax = "/gamemode <mode> [player]";
    public static final String consoleSyntax = "/gamemode <mode> <player>";
    public static final String name = "gamemode";
    public static final String permission = "tasp.gamemode";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            if(args.length != 2){
                Command.sendConsoleSyntaxError((ConsoleCommandSender)sender, this);
                return;
            }

            String[] aargs = {"1", "0", "2", "survival", "creative", "adventure", "a", "c", "s"};
            List<String> accept = Arrays.asList(aargs);

            if(!accept.contains(args[0])) {
                Command.sendConsoleSyntaxError((ConsoleCommandSender)sender, this);
                return;
            }

            if(Bukkit.getPlayer(args[1]) == null) {
                sendPlayerNotFoundMessage(sender, args[1]);
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
        } else {

        }
    }

    private void sendPlayerNotFoundMessage(CommandSender s, String p) {
        s.sendMessage(Config.err() + "Couldn't find player \"" + p + "\"");
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
    public boolean predictOthers(String[] args) {
        return args.length <= 1 && Bukkit.getPlayer(args[1]) != null;
    }

}
