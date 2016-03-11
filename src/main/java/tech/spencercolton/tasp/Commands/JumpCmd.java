package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * @author Spencer Colton
 */
public class JumpCmd extends TASPCommand {

    @Getter
    private final String syntax = "/jump";

    @Getter
    public static final String name = "jump";

    @Getter
    private final String consoleSyntax = null;

    @Getter
    private final String permission = "tasp.jump";

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        assert sender instanceof Player;

        Player p = (Player) sender;
        Location l = p.getTargetBlock((Set<Material>)null, 10000).getLocation();
        l.setY(l.getY() + 1.0D);
        p.teleport(l);

        return CommandResponse.SUCCESS;
    }

}
