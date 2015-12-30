package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tech.spencercolton.tasp.Util.Message;

/**
 * @author Spencer Colton
 */
public class DisappearCmd extends TASPCommand {

    @Getter
    private final String syntax = "/disappear";

    @Getter
    public static final String name = "disappear";

    @Getter
    private final String consoleSyntax = null;

    @Getter
    private final String permission = "tasp.disappear";

    @Override
    public void execute(CommandSender sender, String[] args) {
        assert sender instanceof Player;

        if(args.length > 1) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Player pl;

        if(args.length == 1) {
            pl = Bukkit.getPlayer(args[0]);
            if(pl == null) {
                Command.sendPlayerMessage(sender, args[0]);
                return;
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
    }

}
