package tech.spencercolton.tasp.Commands;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.CommandSender;
import lombok.Getter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Entities;

import java.util.List;
import java.util.Set;

/**
 * @author Spencer Colton
 */
public class SpawnerCmd extends TASPCommand {

    @Getter
    private final String syntax = "/spawner <entity> [delay]";

    @Getter
    public static final String name = "spawner";

    @Getter
    private final String consoleSyntax = null;

    @Getter
    private final String permission = "tasp.spawner";

    @Override
    public void execute(CommandSender sender, String[] argsg) {
        assert sender instanceof Player;

        List<String> args = Command.processQuotedArguments(argsg);

        Block b = ((Player)sender).getTargetBlock((Set<Material>)null, 10000);

        if(b.getType() == Material.MOB_SPAWNER) {
            CreatureSpawner cs = (CreatureSpawner)b.getState();

            int delay = cs.getDelay();
            switch(args.size()) {
                case 2: {
                    try {
                        delay = Integer.parseInt(args.get(1));
                    } catch (NumberFormatException e) {
                        Command.sendSyntaxError(sender, this);
                        return;
                    }
                }
                case 1: {
                    EntityType et = Entities.getEntityType(args.get(0));
                    if(!Entities.isAllowed(et)) {
                        Command.sendInvalidEntityMessage(sender, args.get(0));
                        return;
                    }
                    cs.setSpawnedType(et);
                    cs.setDelay(delay);
                    //TODO Add Message
                }
            }

        } else {
            //TODO Add message here
        }
    }

}
