package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import tech.spencercolton.tasp.Enums.Potions;
import tech.spencercolton.tasp.Util.Message;

import java.util.List;

/**
 * @author Spencer Colton
 */
public class PotionCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/potion <potion> [player] [strength] [duration]";

    public static final String name = "potion";

    @Getter
    private static final String permission = "tasp.potion";

    @Getter
    private static final String consoleSyntax = "/potion <potion> <player> [strength] [duration]";

    @Override
    public void execute(CommandSender sender, String... rargs) {
        List<String> args = Command.processQuotedArguments(rargs);

        if(args.size() == 0 || (sender instanceof ConsoleCommandSender && (args.size() < 2 || args.size() > 4))) {
            Command.sendGenericSyntaxError(sender, this);
            return;
        }

        Potions po;
        Player p = null;
        Integer strength = null;
        Integer duration = null;
        switch(args.size()) {
            case 4: {
                try {
                    duration = Integer.parseInt(args.get(3));
                } catch (NumberFormatException e) {
                    Command.sendGenericSyntaxError(sender, this);
                    return;
                }
            }
            case 3: {
                try {
                    strength = Integer.parseInt(args.get(2));
                } catch (NumberFormatException e) {
                    Command.sendGenericSyntaxError(sender, this);
                    return;
                }
            }
            case 2: {
                p = Bukkit.getPlayer(args.get(1));
                if (p == null) {
                    Command.sendPlayerMessage(sender, args.get(1));
                    return;
                }
            }
            case 1: {
                if (p == null)
                    p = (Player) sender;
                po = Potions.getByName(args.get(0));
                if (po == null) {
                    Message.Potion.Error.sendPotionNotRecognizedMessage(sender, args.get(0));
                    return;
                }
                if (strength == null)
                    strength = Potions.DEFAULT_STRENGTH;
                if (duration == null)
                    duration = po.getDefaultDuration();
                PotionEffect pe = new PotionEffect(po.getSpigotPotion(), duration, strength);
                p.addPotionEffect(pe, true);
                Message.Potion.sendPotionMessage(sender, pe, p);
                return;
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
            }
        }
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        String ret = permission;

        List<String> realArgs = Command.processQuotedArguments();

        if(realArgs.size() >= 1) {
            Potions p = Potions.getByName(realArgs.get(0));
            if(p != null) {
                ret +=  "." + p.getSpigotPotion().getName().toLowerCase();
            } else {
                return permission;
            }
        }

        if(realArgs.size() == 4 && !sender.equals(Bukkit.getPlayer(realArgs.get(3))))
            ret += ".others";

        return ret;
    }

}
