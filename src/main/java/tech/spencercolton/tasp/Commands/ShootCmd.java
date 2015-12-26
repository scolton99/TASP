package tech.spencercolton.tasp.Commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import tech.spencercolton.tasp.Scheduler.EntityCannon;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.Entities;

import java.util.List;

/**
 * @author Spencer Colton
 */
public class ShootCmd extends TASPCommand {

    private static final String syntax = "/shoot <mob>";
    public static final String name = "shoot";
    private static final String permission = "tasp.shoot";
    private static final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] argc) {
        List<String> args = Command.processQuotedArguments(argc);
        args = Command.removeSpaces(args.toArray(new String[args.size()]));

        assert !(sender instanceof ConsoleCommandSender);

        if(args.size() != 1) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        Player p = (Player)sender;
        World w = p.getWorld();

        Location start = p.getEyeLocation();
        Vector j = p.getEyeLocation().getDirection().multiply(1.25F);

        EntityType a = Entities.getEntityType(args.get(0));

        if(!Entities.isValidEntityName(args.get(0)) || !Entities.isAllowed(args.get(0)) || a == null) {
            sender.sendMessage(Config.err() + "That is not a recognized entity name.");
            return;
        }

        Entity g = w.spawnEntity(start, a);
        g.setVelocity(j);
        new EntityCannon(g);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getConsoleSyntax() {
        return consoleSyntax;
    }


}
