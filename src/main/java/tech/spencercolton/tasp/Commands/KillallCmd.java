package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Entities;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

import java.util.Collections;
import java.util.List;

/**
 * The {@link TASPCommand} object containing the runtime information for the {@code killall} command.
 *
 * <table summary="Properties">
 *     <tr>
 *         <th style="font-weight:bold;">Property</th>
 *         <th style="font-weight:bold;">Value</th>
 *     </tr>
 *     <tr>
 *         <td>
 *             Name
 *         </td>
 *         <td>
 *             {@value name}
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *             Permission
 *         </td>
 *         <td>
 *             {@code tasp.killall}
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *             Syntax
 *         </td>
 *         <td>
 *             {@value syntax}
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>
 *             Console Syntax
 *         </td>
 *         <td>
 *             {@value consoleSyntax}
 *         </td>
 *     </tr>
 * </table>
 */
public class KillallCmd extends TASPCommand {

    /**
     * String containing the command's name.
     */
    public static final String name = "killall";

    /**
     * String containing the command's syntax.
     */
    private static final String syntax = "/killall [entity] [radius] OR /killall [entity]";

    /**
     * String containing the command's console syntax.
     */
    private static final String consoleSyntax = "/killall [entity] [world]";

    private static final String permission = "tasp.killall";

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String... argsg) {
        List<String> args = Command.processQuotedArguments(argsg);
        args = Command.removeSpaces(args.toArray(new String[args.size()]));

        if(sender instanceof Player) {
            switch(args.size()){
                case 0: {
                    World w = ((Entity) sender).getWorld();
                    int count = 0;

                    List<Entity> entities = w.getEntities();
                    for (Entity e : entities) {
                        if (Entities.killAllowed(e)) {
                            e.remove();
                            count++;
                        }
                    }

                    this.sendCountMessage(sender, count, w.getName());
                    return;
                }
                case 1: {
                    World w = ((Entity) sender).getWorld();
                    int count = 0;

                    if (!Entities.killAllowed(args.get(0))) {
                        Command.sendSyntaxError(sender, this);
                        return;
                    } else {
                        List<Entity> entities = w.getEntities();

                        for (Entity e : entities) {
                            if (!Entities.killAllowed(e))
                                continue;
                            if (args.get(0).equalsIgnoreCase("all")) {
                                e.remove();
                                count++;
                            } else if (args.get(0).equalsIgnoreCase("monster")) {
                                if (Entities.isMonster(e)) {
                                    e.remove();
                                    count++;
                                }
                            } else if (args.get(0).equalsIgnoreCase("animal")) {
                                if (Entities.isAnimal(e)) {
                                    e.remove();
                                    count++;
                                }
                            } else if (e.getType().toString().equalsIgnoreCase(args.get(0))) {
                                e.remove();
                                count++;
                            }
                        }
                        this.sendCountMessage(sender, count, w.getName());
                    }
                    return;
                }
                case 2: {
                    int a;
                    try {
                        a = Integer.parseInt(args.get(1));
                        String entityName = args.get(0);

                        World w = ((Entity) sender).getWorld();
                        
                        int count = 0;

                        List<Entity> entities = w.getEntities();

                        if (Entities.isValidEntityName(entityName)) {
                            for (Entity e : entities) {
                                if (!Entities.killAllowed(e))
                                    continue;
                                if (e.getLocation().distance(((Entity) sender).getLocation()) > a)
                                    continue;
                                if (args.get(0).equalsIgnoreCase("all")) {
                                    e.remove();
                                    count++;
                                } else if (args.get(0).equalsIgnoreCase("monster")) {
                                    if (Entities.isMonster(e)) {
                                        e.remove();
                                        count++;
                                    }
                                } else if (args.get(0).equalsIgnoreCase("animal")) {
                                    if (Entities.isAnimal(e)) {
                                        e.remove();
                                        count++;
                                    }
                                } else if (e.getType().toString().equalsIgnoreCase(args.get(0))) {
                                    e.remove();
                                    count++;
                                }
                            }
                        } else {
                            Command.sendSyntaxError(sender, this);
                            return;
                        }
                        this.sendCountMessage(sender, count, w.getName());
                    } catch (NumberFormatException e) {
                        Command.sendSyntaxError(sender, this);
                    }
                }
            }

        } else if (sender instanceof ConsoleCommandSender) {
            switch(args.size()) {
                case 0: {
                    World w = Bukkit.getWorlds().get(0);
                    int count = 0;

                    for (Entity g : w.getEntities()) {
                        if (Entities.killAllowed(g)) {
                            g.remove();
                            count++;
                        }
                    }
                    this.sendCountMessage(sender, count, w.getName());
                    return;
                }
                case 1: {
                    int count = 0;

                    World w = Bukkit.getWorlds().get(0);

                    if (!Entities.isValidEntityName(args.get(0))) {
                        Command.sendConsoleSyntaxError((ConsoleCommandSender) sender, this);
                    }


                    for (Entity e : w.getEntities()) {
                        if(!Entities.killAllowed(e))
                            continue;
                        
                        switch (args.get(0)) {
                            case "all":
                                e.remove();
                                count++;
                                break;
                            case "monster":
                                if(Entities.isMonster(e)) {
                                    e.remove();
                                    count++;
                                }
                                break;
                            case "animal":
                                if(Entities.isAnimal(e)) {
                                    e.remove();
                                    count++;
                                }
                                break;
                            default:
                                if (e.getType().toString().equalsIgnoreCase(args.get(0))) {
                                    e.remove();
                                    count++;
                                }
                                break;
                        }
                    }
                    this.sendCountMessage(sender, count, w.getName());
                    return;
                }
                case 2: {
                    World w = Bukkit.getWorld(args.get(1));

                    int count = 0;

                    if (w == null) {
                        sender.sendMessage(Config.err() + "World \"" + args.get(1) + "\" does not seem to exist.");
                        return;
                    }

                    if (!Entities.isValidEntityName(args.get(0))) {
                        Command.sendConsoleSyntaxError((ConsoleCommandSender) sender, this);
                        return;
                    }

                    for (Entity e : w.getEntities()) {
                        if(!Entities.killAllowed(e))
                        switch (args.get(0)) {
                            case "all":
                                e.remove();
                                count++;
                                break;
                            case "monster":
                                if (Entities.isMonster(e)) {
                                    e.remove();
                                    count++;
                                }
                                break;
                            case "animal":
                                if (Entities.isAnimal(e)) {
                                    e.remove();
                                    count++;
                                }
                                break;
                            default:
                                if (e.getType().toString().equalsIgnoreCase(args.get(0))) {
                                    e.remove();
                                    count++;
                                }
                                break;
                        }
                    }
                    this.sendCountMessage(sender, count, w.getName());
                }
            }
        }
    }

    /**
     * Method that sends a message regarding the number of entities killed via this command.
     *
     * @param sender The person to whom the message will be sent.
     * @param count The number of entities killed.
     * @param world The name of the world in which the entities were killed.
     */
    private void sendCountMessage(CommandSender sender, int count, String world) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.killall", Integer.toString(count), world));
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("bluerinse");
    }

}
