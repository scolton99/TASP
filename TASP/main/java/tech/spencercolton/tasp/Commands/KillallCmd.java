package tech.spencercolton.tasp.Commands;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KillallCmd extends TASPCommand {

    public static final String name = "killall";

    @Override
    public void execute(CommandSender sender, String[] args) {
        List<EntityType> banList = new ArrayList<>();
        banList.add(EntityType.ARMOR_STAND);
        banList.add(EntityType.ARROW);
        banList.add(EntityType.BOAT);
        banList.add(EntityType.COMPLEX_PART);
        banList.add(EntityType.DROPPED_ITEM);
        banList.add(EntityType.EGG);
        banList.add(EntityType.ENDER_CRYSTAL);
        banList.add(EntityType.ENDER_PEARL);
        banList.add(EntityType.ENDER_SIGNAL);
        banList.add(EntityType.EXPERIENCE_ORB);
        banList.add(EntityType.FALLING_BLOCK);
        banList.add(EntityType.FIREBALL);
        banList.add(EntityType.FIREWORK);
        banList.add(EntityType.FISHING_HOOK);
        banList.add(EntityType.ITEM_FRAME);
        banList.add(EntityType.LEASH_HITCH);
        banList.add(EntityType.LIGHTNING);
        banList.add(EntityType.MINECART);
        banList.add(EntityType.MINECART_CHEST);
        banList.add(EntityType.MINECART_COMMAND);
        banList.add(EntityType.MINECART_FURNACE);
        banList.add(EntityType.MINECART_HOPPER);
        banList.add(EntityType.MINECART_MOB_SPAWNER);
        banList.add(EntityType.MINECART_TNT);
        banList.add(EntityType.PAINTING);
        banList.add(EntityType.PLAYER);
        banList.add(EntityType.PRIMED_TNT);
        banList.add(EntityType.SMALL_FIREBALL);
        banList.add(EntityType.SNOWBALL);
        banList.add(EntityType.SPLASH_POTION);
        banList.add(EntityType.THROWN_EXP_BOTTLE);
        banList.add(EntityType.UNKNOWN);
        banList.add(EntityType.WEATHER);
        banList.add(EntityType.WITHER_SKULL);

        List<EntityType> emonster = new ArrayList<>();
        emonster.add(EntityType.BAT);
        emonster.add(EntityType.CAVE_SPIDER);
        emonster.add(EntityType.CREEPER);
        emonster.add(EntityType.ENDER_DRAGON);
        emonster.add(EntityType.ENDERMAN);
        emonster.add(EntityType.ENDERMITE);
        emonster.add(EntityType.GHAST);
        emonster.add(EntityType.GIANT);
        emonster.add(EntityType.GUARDIAN);
        emonster.add(EntityType.MAGMA_CUBE);
        emonster.add(EntityType.PIG_ZOMBIE);
        emonster.add(EntityType.SILVERFISH);
        emonster.add(EntityType.SKELETON);
        emonster.add(EntityType.SLIME);
        emonster.add(EntityType.SPIDER);
        emonster.add(EntityType.WITCH);
        emonster.add(EntityType.WITHER);
        emonster.add(EntityType.ZOMBIE);

        List<EntityType> eanimal = new ArrayList<>();
        eanimal.add(EntityType.CHICKEN);
        eanimal.add(EntityType.COW);
        eanimal.add(EntityType.HORSE);
        eanimal.add(EntityType.IRON_GOLEM);
        eanimal.add(EntityType.MUSHROOM_COW);
        eanimal.add(EntityType.OCELOT);
        eanimal.add(EntityType.PIG);
        eanimal.add(EntityType.RABBIT);
        eanimal.add(EntityType.SHEEP);
        eanimal.add(EntityType.SNOWMAN);
        eanimal.add(EntityType.VILLAGER);
        eanimal.add(EntityType.WOLF);

        if(sender instanceof Player) {
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
            ets.add(EntityType.GHAST);
            ets.add(EntityType.GIANT);
            ets.add(EntityType.GUARDIAN);
            ets.add(EntityType.HORSE);
            ets.add(EntityType.IRON_GOLEM);
            ets.add(EntityType.MAGMA_CUBE);
            ets.add(EntityType.MUSHROOM_COW);
            ets.add(EntityType.OCELOT);
            ets.add(EntityType.PIG);
            ets.add(EntityType.PIG_ZOMBIE);
            ets.add(EntityType.RABBIT);
            ets.add(EntityType.SHEEP);
            ets.add(EntityType.SILVERFISH);
            ets.add(EntityType.SKELETON);
            ets.add(EntityType.SLIME);
            ets.add(EntityType.SNOWMAN);
            ets.add(EntityType.SPIDER);
            ets.add(EntityType.SQUID);
            ets.add(EntityType.VILLAGER);
            ets.add(EntityType.WITCH);
            ets.add(EntityType.WITHER);
            ets.add(EntityType.WOLF);
            ets.add(EntityType.ZOMBIE);

            List<String> pets = new ArrayList<>();
            pets.add("all");
            pets.add("monster");
            pets.add("animal");
            for(EntityType et: ets)
                pets.add(et.toString().toLowerCase());
            switch(args.length){
                case 0:
                    World w = ((Player)sender).getWorld();
                    int count = 0;

                    List<Entity> es = w.getEntities();
                    for(Entity e : es) {
                        if(ets.contains(e.getType()) && !banList.contains(e.getType())) {
                            e.remove();
                            count++;
                        }
                    }

                    sender.sendMessage(ChatColor.GOLD + "Killed " + ChatColor.DARK_RED + count + ChatColor.GOLD + " entities.");
                    return;
                case 1:
                    World w2 = ((Player)sender).getWorld();
                    int cxt = 0;

                    if(!pets.contains(args[0].toLowerCase())) {
                        sender.sendMessage(ChatColor.RED + "Error executing command.  Format: /killall [entity] [radius] OR /killall [entity]");
                        return;
                    } else {
                        List<Entity> eties = w2.getEntities();

                        for(Entity ex: eties) {
                            if(banList.contains(ex.getType()))
                                continue;
                            if(args[0].equalsIgnoreCase("all")) {
                                ex.remove();
                                cxt++;
                            } else if(args[0].equalsIgnoreCase("monster")) {
                                if (emonster.contains(ex.getType())) {
                                    ex.remove();
                                    cxt++;
                                }
                            } else if(args[0].equalsIgnoreCase("animal")) {
                                if(eanimal.contains(ex.getType())) {
                                    ex.remove();
                                    cxt++;
                                }
                            } else if(ex.getType().toString().equalsIgnoreCase(args[0])) {
                                ex.remove();
                                cxt++;
                            }
                        }
                        sender.sendMessage(ChatColor.GOLD + "Killed " + ChatColor.DARK_RED + cxt + ChatColor.GOLD + " entities.");
                    }
                    return;
                case 2:
                    int a;
                    try {
                        a = Integer.parseInt(args[1]);
                        String ET = args[0];

                        World w3 = ((Player)sender).getWorld();

                        List<String> valid = new ArrayList<>();
                        valid.add("all");
                        valid.add("monster");
                        valid.add("animal");

                        for(EntityType g: EntityType.values()) {
                            valid.add(g.toString());
                        }

                        int czt = 0;

                        List<Entity> eties = w3.getEntities();

                        if(valid.contains(ET)) {
                            for(Entity ex: eties) {
                                if(banList.contains(ex.getType()))
                                    continue;
                                if(ex.getLocation().distance(((Player)sender).getLocation()) > a)
                                    continue;
                                if(args[0].equalsIgnoreCase("all")) {
                                    ex.remove();
                                    czt++;
                                } else if(args[0].equalsIgnoreCase("monster")) {
                                    if(emonster.contains(ex.getType())) {
                                        ex.remove();
                                        czt++;
                                    }
                                } else if(args[0].equalsIgnoreCase("animal")) {
                                    if(eanimal.contains(ex.getType())) {
                                        ex.remove();
                                        czt++;
                                    }
                                } else if(ex.getType().toString().equalsIgnoreCase(args[0])) {
                                    ex.remove();
                                    czt++;
                                }
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "Error executing command.  Format: /killall [entity] [radius] OR /killall [entity]");
                            return;
                        }
                        sender.sendMessage(ChatColor.GOLD + "Killed " + ChatColor.DARK_RED + czt + ChatColor.GOLD + " entities.");
                    } catch(NumberFormatException e) {
                        sender.sendMessage(ChatColor.RED + "Error executing command.  Format: /killall [entity] [radius] OR /killall [entity]");
                    }
            }

        }
    }

}
