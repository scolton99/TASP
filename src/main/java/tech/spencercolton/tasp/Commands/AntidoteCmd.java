package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.List;

import static java.util.Collections.*;
import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Util.Message.Antidote.*;

/**
 * @author Spencer Colton
 */
public class AntidoteCmd extends TASPCommand {

    @Getter
    private final String syntax = "/antidote [player]";

    @Getter
    private static final String name = "antidote";

    @Getter
    private final String permission = "tasp.antidote";

    @Getter
    private final String consoleSyntax = "/antidote <player>";

    @Override
    public CommandResponse execute(CommandSender sender, String... args) {
        if (args.length == 0 && sender instanceof ConsoleCommandSender) {
            sendConsoleSyntaxError(sender, this);
            return CommandResponse.FAILURE;
        }
        Player p = null;
        switch (args.length) {
            case 1: {
                p = getPlayer(args[0]);
                if (p == null) {
                    sendPlayerMessage(sender, args[0]);
                    return CommandResponse.PLAYER;
                }
            }
            case 0: {
                if (p == null)
                    p = (Player) sender;
                for (PotionEffect g : p.getActivePotionEffects()) {
                    p.removePotionEffect(g.getType());
                }
                sendAntidoteMessage(sender, p);
                return CommandResponse.SUCCESS;
            }
            default: {
                sendGenericSyntaxError(sender, this);
                return CommandResponse.SYNTAX;
            }
        }
    }

    @Override
    public List<String> getAliases() {
        return singletonList("cleareffects");
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return (args.length == 1 && !sender.equals(Bukkit.getPlayer(args[0]))) ? permission + ".others" : permission;
    }

}
