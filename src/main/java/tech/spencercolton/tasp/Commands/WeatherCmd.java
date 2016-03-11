package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Enums.WeatherType;

import java.util.Random;

import static java.lang.Integer.*;
import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Enums.WeatherType.*;
import static tech.spencercolton.tasp.Util.Message.Weather.*;

/**
 * @author Spencer Colton
 */
public class WeatherCmd extends TASPCommand {

    @Getter
    private final String syntax = "/weather [world [type] [time]] | [type] [time]";

    @Getter
    private static final String name = "weather";

    @Getter
    private final String consoleSyntax = syntax;

    @Getter
    private final String permission = "tasp.weather";

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        if (args.length > 3) {
            sendGenericSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        World w = null;
        WeatherType type = null;
        Integer time = new Random().nextInt(250) + 250;
        Track t = null;

        switch (args.length) {
            case 3: {
                t = Track.WORLD;
                try {
                    time = parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sendGenericSyntaxError(sender, this);
                    return CommandResponse.SYNTAX;
                }
            }
            case 2: {
                if (t == null) {
                    t = Track.TYPE;
                    try {
                        time = parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        sendGenericSyntaxError(sender, this);
                        return CommandResponse.SYNTAX;
                    }
                } else {
                    switch (args[1].toLowerCase()) {
                        case "rainy":
                        case "rain":
                        case "storm":
                        case "stormy":
                        case "thunder":
                        case "lightning":
                        case "snow": {
                            type = STORM;
                            break;
                        }
                        case "sun":
                        case "clear": {
                            type = SUN;
                            break;
                        }
                        default: {
                            sendGenericSyntaxError(sender, this);
                            return CommandResponse.SYNTAX;
                        }
                    }
                }
            }
            case 1: {
                if (t == null) {
                    w = getWorld(args[0]);
                    if (w == null) {
                        switch (args[0].toLowerCase()) {
                            case "rainy":
                            case "rain":
                            case "storm":
                            case "stormy":
                            case "thunder":
                            case "lightning":
                            case "snow": {
                                type = STORM;
                                break;
                            }
                            case "sun":
                            case "clear": {
                                type = SUN;
                                break;
                            }
                            default: {
                                sendGenericSyntaxError(sender, this);
                                return CommandResponse.SYNTAX;
                            }
                        }
                    }
                } else if (t == Track.TYPE) {
                    switch (args[0].toLowerCase()) {
                        case "rainy":
                        case "rain":
                        case "storm":
                        case "stormy":
                        case "thunder":
                        case "lightning":
                        case "snow": {
                            type = STORM;
                            break;
                        }
                        case "sun":
                        case "clear": {
                            type = SUN;
                            break;
                        }
                        default: {
                            sendGenericSyntaxError(sender, this);
                            return CommandResponse.SYNTAX;
                        }
                    }
                } else {
                    w = getWorld(args[0]);
                    if (w == null) {
                        sendWorldMessage(sender, args[0]);
                        return CommandResponse.WORLD;
                    }
                }
            }
            case 0: {
                if (type == null) {
                    if (sender instanceof ConsoleCommandSender) {
                        if (w == null)
                            w = getWorlds().get(0);
                        sendConsoleWeatherReport(sender, w.hasStorm(), w.getWeatherDuration(), w);
                        return CommandResponse.SUCCESS;
                    } else {
                        assert sender instanceof Player;
                        if (w == null)
                            w = ((Player) sender).getWorld();
                        Location l = ((Player) sender).getLocation();
                        sendWeatherReport(sender, w.hasStorm(), w.getWeatherDuration(), w.getTemperature(l.getBlockX(), l.getBlockZ()), w.getHumidity(l.getBlockX(), l.getBlockZ()), w);
                        return CommandResponse.SUCCESS;
                    }
                } else {
                    if (sender instanceof ConsoleCommandSender) {
                        if (w == null)
                            w = getWorlds().get(0);
                    } else {
                        assert sender instanceof Player;
                        if (w == null)
                            w = ((Player) sender).getWorld();
                    }
                    w.setStorm(type == STORM);
                    w.setWeatherDuration(time);
                    sendWeatherMessage(sender, type, time, w);
                }
            }
            default: {
                sendGenericSyntaxError(sender, this);
                return CommandResponse.SYNTAX;
            }
        }
    }

    private enum Track {
        WORLD,
        TYPE
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        if(args.length >= 2 && Bukkit.getWorld(args[0]) != null)
            return permission + ".set";
        else if(args.length >= 1)
            return permission + ".set";
        else
            return permission;
    }

}
