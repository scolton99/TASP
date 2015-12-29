package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

        PotionEffect p = new PotionEffect(PotionEffectType.INVISIBILITY, 1_000_000, 0, false, false);
        // TODO Add message to this command
        // TODO Decide whether or not to make this available for tasp.disappear.others
        Player pl = (Player) sender;
        pl.addPotionEffect(p);
    }

}
