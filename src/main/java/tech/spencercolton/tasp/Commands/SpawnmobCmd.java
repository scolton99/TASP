package tech.spencercolton.tasp.Commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Spencer Colton
 */
public class SpawnmobCmd extends TASPCommand {

    public static final String syntax = "/spawnmob <type> [amount]";
    public static final String name = "spawnmob";
    public static final String permission = "tasp.spawnmob";
    public static final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String... args) {
        if(sender instanceof ConsoleCommandSender) {
            Command.sendConsoleError((ConsoleCommandSender)sender);
            return;
        }
        List<String> realArgs = Command.processQuotedArguments(args);

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
        pets.addAll(ets.stream().map(et -> et.toString().toLowerCase()).collect(Collectors.toList()));

        switch(realArgs.size()) {
            case 1:
                if(pets.contains(realArgs.get(0).replace(" ", "_"))) {
                    Location l = ((Player)sender).getTargetBlock((Set<Material>)null, 1000).getLocation();
                    l.getWorld().spawnEntity(l, EntityType.valueOf(realArgs.get(0).replace(" ", "_").toUpperCase()));
                    sendSpawnmobMessage(sender, EntityType.valueOf(realArgs.get(0).replace(" ", "_").toUpperCase()), 1);
                } else {
                    Command.sendSyntaxError(sender, this);
                }
                break;
            case 2:
                if(pets.contains(realArgs.get(0).replace(" ", "_"))) {
                    Location l = ((Player)sender).getTargetBlock((Set<Material>)null, 1000).getLocation();
                    int x;
                    try {
                        x = Integer.parseInt(realArgs.get(1));
                        if(x > Config.getSpawnLimit()) {
                            x = Config.getSpawnLimit();
                        }
                        for(int i = 0; i < x; i++)
                            l.getWorld().spawnEntity(l, EntityType.valueOf(realArgs.get(0).replace(" ", "_").toUpperCase()));
                        sendSpawnmobMessage(sender, EntityType.valueOf(realArgs.get(0).replace(" ", "_").toUpperCase()), x);
                    } catch(NumberFormatException e) {
                        Command.sendSyntaxError(sender, this);
                    }
                } else {
                    Command.sendSyntaxError(sender, this);
                }
                return;
            default:
                Command.sendSyntaxError(sender, this);
        }
    }

    private void sendSpawnmobMessage(CommandSender sender, EntityType e, int amount) {
        sender.sendMessage(M.m("command-message-text.spawnmob", e.toString().toLowerCase(), Integer.toString(amount)));
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
