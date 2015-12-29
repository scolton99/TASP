package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import tech.spencercolton.tasp.Enums.Potions;

import java.util.List;

import static java.lang.Integer.*;
import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Enums.Potions.*;
import static tech.spencercolton.tasp.Util.Message.Potion.Error.*;
import static tech.spencercolton.tasp.Util.Message.Potion.*;

/**
 * @author Spencer Colton
 */
public class PotionCmd extends TASPCommand {

    @Getter
    private final String syntax = "/potion <potion> [player] [strength] [duration]";

    @Getter
    private static final String name = "potion";

    @Getter
    private final String permission = "tasp.potion";

    @Getter
    private final String consoleSyntax = "/potion <potion> <player> [strength] [duration]";

    @Override
    public void execute(CommandSender sender, String... rargs) {
        List<String> args = processQuotedArguments(rargs);

        if (args.size() == 0 || (sender instanceof ConsoleCommandSender && (args.size() < 2 || args.size() > 4))) {
            sendGenericSyntaxError(sender, this);
            return;
        }

        Potions po;
        Player p = null;
        Integer strength = null;
        Integer duration = null;
        switch (args.size()) {
            case 4: {
                try {
                    duration = parseInt(args.get(3));
                } catch (NumberFormatException e) {
                    sendGenericSyntaxError(sender, this);
                    return;
                }
            }
            case 3: {
                try {
                    strength = parseInt(args.get(2));
                } catch (NumberFormatException e) {
                    sendGenericSyntaxError(sender, this);
                    return;
                }
            }
            case 2: {
                p = getPlayer(args.get(1));
                if (p == null) {
                    sendPlayerMessage(sender, args.get(1));
                    return;
                }
            }
            case 1: {
                if (p == null)
                    p = (Player) sender;
                po = getByName(args.get(0));
                if (po == null) {
                    sendPotionNotRecognizedMessage(sender, args.get(0));
                    return;
                }
                if (strength == null)
                    strength = DEFAULT_STRENGTH;
                if (duration == null)
                    duration = po.getDefaultDuration();
                PotionEffect pe = new PotionEffect(po.getSpigotPotion(), duration, strength);
                p.addPotionEffect(pe, true);
                sendPotionMessage(sender, pe, p);
                return;
            }
            default: {
                sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        String ret = permission;

        List<String> realArgs = processQuotedArguments();

        if (realArgs.size() >= 1) {
            Potions p = getByName(realArgs.get(0));
            if (p != null) {
                ret += "." + p.getSpigotPotion().getName().toLowerCase();
            } else {
                return permission;
            }
        }

        if (realArgs.size() == 4 && !sender.equals(getPlayer(realArgs.get(3))))
            ret += ".others";

        return ret;
    }

}
