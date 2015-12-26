package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.Entities;
import tech.spencercolton.tasp.Util.Message;

import java.util.List;
import java.util.Set;

/**
 * @author Spencer Colton
 */
public class SpawnmobCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/spawnmob <type> [amount]";

    public static final String name = "spawnmob";

    @Getter
    private static final String permission = "tasp.spawnmob";

    @Getter
    private static final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String... gargs) {
        assert sender instanceof Player;
        List<String> args = Command.processQuotedArguments(gargs);

        int amount = 1;
        switch(args.size()) {
            case 2: {
                try {
                    amount = Integer.parseInt(args.get(1));
                } catch (NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                    return;
                }
            }
            case 1: {
                if (amount > Config.getSpawnLimit())
                    amount = Config.getSpawnLimit();
                EntityType e = Entities.getEntityType(args.get(0));
                if(e == null || !Entities.isAllowed(e)) {
                    Command.sendInvalidEntityMessage(sender, args.get(0));
                    return;
                }

                Location l = ((Player) sender).getTargetBlock((Set<Material>) null, 1000).getLocation();
                l.setY(l.getY() + 1.0D);
                l.getWorld().spawnEntity(l, e);
                Message.Spawnmob.sendSpawnmobMessage(sender, e, amount);
                return;
            }
            default: {
                Command.sendSyntaxError(sender, this);
            }
        }
    }

}
