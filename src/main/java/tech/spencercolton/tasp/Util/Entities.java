package tech.spencercolton.tasp.Util;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import tech.spencercolton.tasp.Commands.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Spencer Colton
 */
public class Entities {

    private static List<EntityType> monsters;
    private static List<EntityType> animals;
    private static List<EntityType> notSpawnable;
    private static List<String> kaAllowed;
    private static List<String> allEntitiesAsString;

    public static void initEntities() {
        notSpawnable = new ArrayList<>();
        notSpawnable.add(EntityType.ARMOR_STAND);
        notSpawnable.add(EntityType.ARROW);
        notSpawnable.add(EntityType.BOAT);
        notSpawnable.add(EntityType.COMPLEX_PART);
        notSpawnable.add(EntityType.DROPPED_ITEM);
        notSpawnable.add(EntityType.EGG);
        notSpawnable.add(EntityType.ENDER_CRYSTAL);
        notSpawnable.add(EntityType.ENDER_PEARL);
        notSpawnable.add(EntityType.ENDER_SIGNAL);
        notSpawnable.add(EntityType.EXPERIENCE_ORB);
        notSpawnable.add(EntityType.FALLING_BLOCK);
        notSpawnable.add(EntityType.FIREBALL);
        notSpawnable.add(EntityType.FIREWORK);
        notSpawnable.add(EntityType.FISHING_HOOK);
        notSpawnable.add(EntityType.ITEM_FRAME);
        notSpawnable.add(EntityType.LEASH_HITCH);
        notSpawnable.add(EntityType.LIGHTNING);
        notSpawnable.add(EntityType.MINECART);
        notSpawnable.add(EntityType.MINECART_CHEST);
        notSpawnable.add(EntityType.MINECART_COMMAND);
        notSpawnable.add(EntityType.MINECART_FURNACE);
        notSpawnable.add(EntityType.MINECART_HOPPER);
        notSpawnable.add(EntityType.MINECART_MOB_SPAWNER);
        notSpawnable.add(EntityType.MINECART_TNT);
        notSpawnable.add(EntityType.PAINTING);
        notSpawnable.add(EntityType.PLAYER);
        notSpawnable.add(EntityType.PRIMED_TNT);
        notSpawnable.add(EntityType.SMALL_FIREBALL);
        notSpawnable.add(EntityType.SNOWBALL);
        notSpawnable.add(EntityType.SPLASH_POTION);
        notSpawnable.add(EntityType.THROWN_EXP_BOTTLE);
        notSpawnable.add(EntityType.UNKNOWN);
        notSpawnable.add(EntityType.WEATHER);
        notSpawnable.add(EntityType.WITHER_SKULL);

        monsters = new ArrayList<>();
        monsters.add(EntityType.BAT);
        monsters.add(EntityType.CAVE_SPIDER);
        monsters.add(EntityType.CREEPER);
        monsters.add(EntityType.ENDER_DRAGON);
        monsters.add(EntityType.ENDERMAN);
        monsters.add(EntityType.ENDERMITE);
        monsters.add(EntityType.GHAST);
        monsters.add(EntityType.GIANT);
        monsters.add(EntityType.GUARDIAN);
        monsters.add(EntityType.MAGMA_CUBE);
        monsters.add(EntityType.PIG_ZOMBIE);
        monsters.add(EntityType.SILVERFISH);
        monsters.add(EntityType.SKELETON);
        monsters.add(EntityType.SLIME);
        monsters.add(EntityType.SPIDER);
        monsters.add(EntityType.WITCH);
        monsters.add(EntityType.WITHER);
        monsters.add(EntityType.ZOMBIE);

        animals = new ArrayList<>();
        animals.add(EntityType.CHICKEN);
        animals.add(EntityType.COW);
        animals.add(EntityType.HORSE);
        animals.add(EntityType.IRON_GOLEM);
        animals.add(EntityType.MUSHROOM_COW);
        animals.add(EntityType.OCELOT);
        animals.add(EntityType.PIG);
        animals.add(EntityType.RABBIT);
        animals.add(EntityType.SHEEP);
        animals.add(EntityType.SNOWMAN);
        animals.add(EntityType.VILLAGER);
        animals.add(EntityType.WOLF);

        List<EntityType> allowed = new ArrayList<>();
        allowed.add(EntityType.BAT);
        allowed.add(EntityType.BLAZE);
        allowed.add(EntityType.CAVE_SPIDER);
        allowed.add(EntityType.CHICKEN);
        allowed.add(EntityType.COW);
        allowed.add(EntityType.CREEPER);
        allowed.add(EntityType.ENDER_DRAGON);
        allowed.add(EntityType.ENDERMAN);
        allowed.add(EntityType.ENDERMITE);
        allowed.add(EntityType.GHAST);
        allowed.add(EntityType.GIANT);
        allowed.add(EntityType.GUARDIAN);
        allowed.add(EntityType.HORSE);
        allowed.add(EntityType.IRON_GOLEM);
        allowed.add(EntityType.MAGMA_CUBE);
        allowed.add(EntityType.MUSHROOM_COW);
        allowed.add(EntityType.OCELOT);
        allowed.add(EntityType.PIG);
        allowed.add(EntityType.PIG_ZOMBIE);
        allowed.add(EntityType.RABBIT);
        allowed.add(EntityType.SHEEP);
        allowed.add(EntityType.SILVERFISH);
        allowed.add(EntityType.SKELETON);
        allowed.add(EntityType.SLIME);
        allowed.add(EntityType.SNOWMAN);
        allowed.add(EntityType.SPIDER);
        allowed.add(EntityType.SQUID);
        allowed.add(EntityType.VILLAGER);
        allowed.add(EntityType.WITCH);
        allowed.add(EntityType.WITHER);
        allowed.add(EntityType.WOLF);
        allowed.add(EntityType.ZOMBIE);

        kaAllowed = new ArrayList<>();
        kaAllowed.add("all");
        kaAllowed.add("monster");
        kaAllowed.add("animal");

        kaAllowed.addAll(allowed.stream().map(et -> et.toString().toLowerCase()).collect(Collectors.toList()));kaAllowed.add("animal");

        allEntitiesAsString = new ArrayList<>();
        for(EntityType e: EntityType.values()) {
            allEntitiesAsString.add(e.toString().toLowerCase());
        }
    }

    public static boolean isAllowed(String s) {
        EntityType e = EntityType.valueOf(s.toUpperCase().replace(" ", "_"));
        return e != null && isAllowed(e);
    }

    public static boolean isAllowed(EntityType e) {
        return !notSpawnable.contains(e);
    }

    public static boolean killAllowed(String s) {
        return kaAllowed.contains(s);
    }

    public static boolean killAllowed(Entity e) {
        return killAllowed(e.getType().toString().toLowerCase());
    }

    public static boolean isMonster(Entity e) {
        return monsters.contains(e.getType());
    }

    public static boolean isAnimal(Entity e) {
        return animals.contains(e.getType());
    }

    public static boolean isValidEntityName(String s) {
        return allEntitiesAsString.contains(Command.removeSpaces(s).get(0).toLowerCase());
    }

    public static EntityType getEntityType(String s) {
        try {
            return EntityType.valueOf(s.toUpperCase().replace(" ", "_"));
        } catch(IllegalArgumentException e) {
            return null;
        }
    }

}
