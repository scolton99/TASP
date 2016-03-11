package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tech.spencercolton.tasp.Util.Message;

import static org.bukkit.Bukkit.*;

/**
 * @author Spencer Colton
 */
public class DisappearCmd extends TASPCommand {

    @Getter
    private final String syntax = "/disappear [player]";

    @Getter
    public static final String name = "disappear";

    @Getter
    private final String consoleSyntax = null;

    @Getter
    private final String permission = "tasp.disappear";

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        assert sender instanceof Player;

        if(args.length > 1) {
            Command.sendSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        Player pl;

        if(args.length == 1) {
            pl = Bukkit.getPlayer(args[0]);
            if(pl == null) {
                Command.sendPlayerMessage(sender, args[0]);
                return CommandResponse.PLAYER;
            }
        } else {
            pl = (Player)sender;
        }

        if(pl.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
            pl.removePotionEffect(PotionEffectType.INVISIBILITY);
            Message.Disappear.sendDisappearMessage(sender, "visible", pl);
        } else {
            PotionEffect p = new PotionEffect(PotionEffectType.INVISIBILITY, 1_000_000, 0, false, false);
            pl.addPotionEffect(p);
            Message.Disappear.sendDisappearMessage(sender, "invisible", pl);
        }

        return CommandResponse.SUCCESS;
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        return (args.length == 1 && !getPlayer(args[0]).equals(sender)) ? permission + ".others" : permission;
    }

}
