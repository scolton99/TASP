package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import tech.spencercolton.tasp.Util.Message;

import java.util.Collections;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class AntidoteCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/antidote [player]";

    public static final String name = "antidote";

    @Getter
    private static final String permission = "tasp.antidote";

    @Getter
    private static final String consoleSyntax = "/antidote <player>";

    @Override
    public void execute(CommandSender sender, String... args) {
        if(args.length == 0 && sender instanceof ConsoleCommandSender) {
            Command.sendConsoleSyntaxError(sender, this);
            return;
        }
        Player p = null;
        switch(args.length) {
            case 1: {
                p = Bukkit.getPlayer(args[0]);
                if (p == null) {
                    Command.sendPlayerMessage(sender, args[0]);
                    return;
                }
            }
            case 0: {
                if (p == null)
                    p = (Player) sender;
                for (PotionEffect g : p.getActivePotionEffects()) {
                    p.removePotionEffect(g.getType());
                }
                Message.Antidote.sendAntidoteMessage(sender, p);
                return;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("cleareffects");
    }

}
