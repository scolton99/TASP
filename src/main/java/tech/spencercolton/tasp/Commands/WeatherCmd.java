package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Enums.WeatherType;
import tech.spencercolton.tasp.Util.Message;
import tech.spencercolton.tasp.Util.Weather;

import java.util.Random;

/**
 * @author Spencer Colton
 */
public class WeatherCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/weather [world [type] [time]] | [type] [time]";

    public static final String name = "weather";

    @Getter
    private static final String consoleSyntax = syntax;

    @Getter
    private static final String permission = "tasp.weather";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length > 3) {
            Command.sendGenericSyntaxError(sender, this);
            return;
        }

        World w = null;
        WeatherType type = null;
        Integer time = new Random().nextInt(250) + 250;
        Track t = null;

        switch(args.length) {
            case 3: {
                t = Track.WORLD;
                try {
                    time = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    Command.sendGenericSyntaxError(sender, this);
                    return;
                }
            }
            case 2: {
                if (t == null) {
                    t = Track.TYPE;
                    try {
                        time = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        Command.sendGenericSyntaxError(sender, this);
                        return;
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
                            type = WeatherType.STORM;
                            break;
                        }
                        case "sun":
                        case "clear": {
                            type = WeatherType.SUN;
                            break;
                        }
                        default: {
                            Command.sendGenericSyntaxError(sender, this);
                            return;
                        }
                    }
                }
            }
            case 1: {
                if (t == null) {
                    w = Bukkit.getWorld(args[0]);
                    if (w == null) {
                        switch (args[0].toLowerCase()) {
                            case "rainy":
                            case "rain":
                            case "storm":
                            case "stormy":
                            case "thunder":
                            case "lightning":
                            case "snow": {
                                type = WeatherType.STORM;
                                break;
                            }
                            case "sun":
                            case "clear": {
                                type = WeatherType.SUN;
                                break;
                            }
                            default: {
                                Command.sendGenericSyntaxError(sender, this);
                                return;
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
                            type = WeatherType.STORM;
                            break;
                        }
                        case "sun":
                        case "clear": {
                            type = WeatherType.SUN;
                            break;
                        }
                        default: {
                            Command.sendGenericSyntaxError(sender, this);
                            return;
                        }
                    }
                } else {
                    w = Bukkit.getWorld(args[0]);
                    if (w == null) {
                        Command.sendWorldMessage(sender, args[0]);
                        return;
                    }
                }
            }
            case 0: {
                if (type == null) {
                    if (sender instanceof ConsoleCommandSender) {
                        if (w == null)
                            w = Bukkit.getWorlds().get(0);
                        Message.Weather.sendConsoleWeatherReport(sender, w.hasStorm(), w.getWeatherDuration(), w);
                        return;
                    } else {
                        assert sender instanceof Player;
                        if (w == null)
                            w = ((Player) sender).getWorld();
                        Location l = ((Player) sender).getLocation();
                        Message.Weather.sendWeatherReport(sender, w.hasStorm(), w.getWeatherDuration(), Weather.calcTemperature(w.getTemperature(l.getBlockX(), l.getBlockZ()), w), Weather.calcHumidity(w.getHumidity(l.getBlockX(), l.getBlockZ())), w);
                        return;
                    }
                } else {
                    if (sender instanceof ConsoleCommandSender) {
                        if (w == null)
                            w = Bukkit.getWorlds().get(0);
                    } else {
                        assert sender instanceof Player;
                        if (w == null)
                            w = ((Player) sender).getWorld();
                    }
                    w.setStorm(type == WeatherType.STORM);
                    w.setWeatherDuration(time);
                    Message.Weather.sendWeatherMessage(sender, type, time, w);
                }
            }
            default: {
                Command.sendGenericSyntaxError(sender, this);
            }
        }
    }

    private enum Track {
        WORLD,
        TYPE
    }

}
