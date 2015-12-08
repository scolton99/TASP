package tech.spencercolton.tasp.Commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KillallCmd implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("killall")) {
            if(sender instanceof Player) {
                switch(args.length){
                    case 0:
                        World w = ((Player)sender).getWorld();
                        Player p = ((Player)sender);

                        List<EntityType> ets = new ArrayList<>();
                        ets.add(EntityType.BAT);
                        ets.add(EntityType.BLAZE);
                        ets.add(EntityType.CAVE_SPIDER);
                        ets.add(EntityType.CHICKEN);
                        ets.add(EntityType.COW);
                        ets.add(EntityType.CREEPER);
                        ets.add(EntityType.ENDER_DRAGON);
                        ets.add(EntityType.ENDERMAN);
                        ets.add(EntityType.ENDERMITE);
                        ets.add(EntityType.GHAST)

                        List<Entity> es = w.getEntities();
                        for(Entity e : es) {
                            if(e.getType() == Ent)
                        }
                }

            }
        }
        return true;
    }

}
