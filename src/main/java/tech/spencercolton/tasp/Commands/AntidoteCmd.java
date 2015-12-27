package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.bukkit.Bukkit.getPlayer;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Util.Message.Antidote.sendAntidoteMessage;

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
    public void execute(CommandSender sender, String... args) {
        if (args.length == 0 && sender instanceof ConsoleCommandSender) {
            sendConsoleSyntaxError(sender, this);
            return;
        }
        Player p = null;
        switch (args.length) {
            case 1: {
                p = getPlayer(args[0]);
                if (p == null) {
                    sendPlayerMessage(sender, args[0]);
                    return;
                }
            }
            case 0: {
                if (p == null)
                    p = (Player) sender;
                for (PotionEffect g : p.getActivePotionEffects()) {
                    p.removePotionEffect(g.getType());
                }
                sendAntidoteMessage(sender, p);
                return;
            }
            default: {
                sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public List<String> getAliases() {
        return singletonList("cleareffects");
    }

}
