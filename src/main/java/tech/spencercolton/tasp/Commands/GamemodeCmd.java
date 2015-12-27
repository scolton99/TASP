package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.bukkit.Bukkit.getPlayer;
import static org.bukkit.GameMode.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Util.Message.Gamemode.Error.sendGamemodeNotFoundMessage;
import static tech.spencercolton.tasp.Util.Message.Gamemode.sendGamemodeMessage;

public class GamemodeCmd extends TASPCommand {

    @Getter
    private final String syntax = "/gamemode <mode> [player]";

    @Getter
    private final String consoleSyntax = "/gamemode <mode> <player>";

    @Getter
    private static final String name = "gamemode";

    @Getter
    private final String permission = "tasp.gamemode";

    @Override
    public void execute(CommandSender sender, String... args) {
        if (sender instanceof ConsoleCommandSender && args.length != 2) {
            sendConsoleSyntaxError(sender, this);
            return;
        }

        Player p = null;
        switch (args.length) {
            case 2: {
                p = getPlayer(args[1]);
                if (p == null) {
                    sendPlayerMessage(sender, args[1]);
                    return;
                }
            }
            case 1: {
                if (p == null)
                    p = (Player) sender;
                switch (args[0]) {
                    case "c":
                    case "creative":
                    case "1": {
                        p.setGameMode(CREATIVE);
                        break;
                    }
                    case "a":
                    case "adventure":
                    case "2": {
                        p.setGameMode(ADVENTURE);
                        break;
                    }
                    case "s":
                    case "survival":
                    case "0": {
                        p.setGameMode(SURVIVAL);
                        break;
                    }
                    default: {
                        sendGamemodeNotFoundMessage(sender, args[0]);
                        return;
                    }
                }
                sendGamemodeMessage(sender, p.getGameMode().toString().toLowerCase(), p);
                break;
            }
            default: {
                sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public List<String> getAliases() {
        return singletonList("gm");
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return args.length >= 2 && getPlayer(args[1]) != null && !getPlayer(args[1]).equals(sender) ? permission + ".others" : permission;
    }

}
