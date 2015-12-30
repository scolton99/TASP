package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

import static java.lang.Integer.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Util.Config.*;
import static tech.spencercolton.tasp.Util.Entities.*;
import static tech.spencercolton.tasp.Util.Message.Spawnmob.*;

/**
 * @author Spencer Colton
 */
public class SpawnmobCmd extends TASPCommand {

    @Getter
    private final String syntax = "/spawnmob <type> [amount]";

    @Getter
    private static final String name = "spawnmob";

    @Getter
    private final String permission = "tasp.spawnmob";

    @Getter
    private final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String... gargs) {
        assert sender instanceof Player;
        List<String> args = processQuotedArguments(gargs);

        int amount = 1;
        switch (args.size()) {
            case 2: {
                try {
                    amount = parseInt(args.get(1));
                } catch (NumberFormatException e) {
                    sendSyntaxError(sender, this);
                    return;
                }
            }
            case 1: {
                if (amount > getSpawnLimit())
                    amount = getSpawnLimit();
                EntityType e = getEntityType(args.get(0));
                if (e == null || !isAllowed(e)) {
                    sendInvalidEntityMessage(sender, args.get(0));
                    return;
                }

                Location l = ((Player) sender).getTargetBlock((Set<Material>) null, 1000).getLocation();
                l.setY(l.getY() + 1.0D);
                for (int i = 0; i < amount; i++) {
                    l.getWorld().spawnEntity(l, e);
                }
                sendSpawnmobMessage(sender, e, amount);
                return;
            }
            default: {
                sendSyntaxError(sender, this);
            }
        }
    }

}
