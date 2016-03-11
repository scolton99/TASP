package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import tech.spencercolton.tasp.Scheduler.EntityCannon;

import java.util.List;

import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Util.Config.*;
import static tech.spencercolton.tasp.Util.Entities.*;

/**
 * @author Spencer Colton
 */
public class ShootCmd extends TASPCommand {

    @Getter
    private final String syntax = "/shoot <mob>";

    @Getter
    private static final String name = "shoot";

    @Getter
    private final String permission = "tasp.shoot";

    @Getter
    private final String consoleSyntax = null;

    @Override
    public CommandResponse execute(CommandSender sender, String[] argc) {
        List<String> args = processQuotedArguments(argc);
        args = removeSpaces(args.toArray(new String[args.size()]));

        assert !(sender instanceof ConsoleCommandSender);

        if (args.size() != 1) {
            sendSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        Player p = (Player) sender;
        World w = p.getWorld();

        Location start = p.getEyeLocation();
        Vector j = p.getEyeLocation().getDirection().multiply(1.25F);

        EntityType a = getEntityType(args.get(0));

        if (!isValidEntityName(args.get(0)) || !isAllowed(args.get(0)) || a == null) {
            sender.sendMessage(err() + "That is not a recognized entity name.");
            return CommandResponse.FAILURE;
        }

        Entity g = w.spawnEntity(start, a);
        g.setVelocity(j);
        new EntityCannon(g);
        return CommandResponse.SUCCESS;
    }

}
