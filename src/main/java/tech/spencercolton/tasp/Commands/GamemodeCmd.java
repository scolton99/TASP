package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Message;

import java.util.Collections;
import java.util.List;

public class GamemodeCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/gamemode <mode> [player]";

    @Getter
    private static final String consoleSyntax = "/gamemode <mode> <player>";

    public static final String name = "gamemode";

    @Getter
    private static final String permission = "tasp.gamemode";

    @Override
    public void execute(CommandSender sender, String... args) {
        if(sender instanceof ConsoleCommandSender && args.length != 2) {
            Command.sendConsoleSyntaxError(sender, this);
            return;
        }

        Player p = null;
        switch(args.length) {
            case 2: {
                p = Bukkit.getPlayer(args[1]);
                if(p == null) {
                    Command.sendPlayerMessage(sender, args[1]);
                    return;
                }
            }
            case 1: {
                if(p == null)
                    p = (Player)sender;
                switch(args[0]) {
                    case "c":
                    case "creative":
                    case "1": {
                        p.setGameMode(GameMode.CREATIVE);
                        break;
                    }
                    case "a":
                    case "adventure":
                    case "2": {
                        p.setGameMode(GameMode.ADVENTURE);
                        break;
                    }
                    case "s":
                    case "survival":
                    case "0": {
                        p.setGameMode(GameMode.SURVIVAL);
                        break;
                    }
                    default: {
                        Message.Gamemode.Error.sendGamemodeNotFoundMessage(sender, args[0]);
                        return;
                    }
                }
                Message.Gamemode.sendGamemodeMessage(sender, p.getGameMode().toString().toLowerCase(), p);
                break;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("gm");
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return args.length >= 2 && Bukkit.getPlayer(args[1]) != null && !Bukkit.getPlayer(args[1]).equals(sender) ? permission + ".others" : permission;
    }

}
