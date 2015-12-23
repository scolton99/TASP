package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Enums.WeatherType;
import tech.spencercolton.tasp.Util.M;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * @author Spencer Colton
 */
public class WeatherCmd extends TASPCommand {

    private static final String syntax = "/weather [world [type] [time]] | [type] [time]";
    public static final String name = "weather";
    private static final String consoleSyntax = syntax;
    private static final String permission = "tasp.weather";

    String[] acceptableWeathers = {"rainy", "rain", "storm", "stormy", "thunder", "lightning", "sun", "clear", "snow"};
    private static final int TICKS_IN_SECOND = 20;

    @Override
    public void execute(CommandSender sender, String[] args) {
        switch(args.length) {
            case 0:
                World w;
                if(sender instanceof ConsoleCommandSender)
                    w = Bukkit.getWorlds().get(0);
                else
                    w = ((Player)sender).getWorld();

                if(sender instanceof ConsoleCommandSender) {
                    sendConsoleWeatherReport(sender, w.hasStorm(), w.getWeatherDuration(), w);
                } else {
                    Player p = (Player)sender;
                    Location l = p.getLocation();
                    sendWeatherReport(sender, w.hasStorm(), w.getWeatherDuration(), w.getTemperature(l.getBlockX(), l.getBlockZ()), w.getHumidity(l.getBlockX(), l.getBlockZ()), w);
                }
                return;
            case 1:
                World w2 = Bukkit.getWorld(args[0]);
                boolean failed = false;
                if(w2 == null) {
                    failed = true;
                    if(sender instanceof ConsoleCommandSender) {
                        w2 = Bukkit.getWorlds().get(0);
                    } else {
                        w2 = ((Player)sender).getWorld();
                    }
                }
                if(failed) {
                    switch (args[0].toLowerCase()) {
                        case "rainy":
                        case "rain":
                        case "storm":
                        case "stormy":
                        case "thunder":
                        case "lightning":
                        case "snow":
                            w2.setStorm(true);
                            sendWeatherMessage(sender, WeatherType.STORM, w2.getThunderDuration(), w2);
                            break;
                        case "sun":
                        case "clear":
                            w2.setStorm(false);
                            sendWeatherMessage(sender, WeatherType.SUN, w2.getWeatherDuration(), w2);
                            break;
                        default:
                            Command.sendWorldMessage(sender, args[0]);
                    }
                } else {
                    if(sender instanceof  ConsoleCommandSender) {
                        sendConsoleWeatherReport(sender, w2.hasStorm(), w2.getWeatherDuration(), w2);
                    } else {
                        int x = ((Player)sender).getLocation().getBlockX();
                        int z = ((Player)sender).getLocation().getBlockZ();
                        sendWeatherReport(sender, w2.hasStorm(), w2.getWeatherDuration(), w2.getTemperature(x, z), w2.getHumidity(x, z), w2);
                    }
                }
                return;
            case 2:
                World w3 = Bukkit.getWorld(args[0]);
                boolean failed2 = false;
                if(w3 == null) {
                    failed2 = true;
                    if(sender instanceof ConsoleCommandSender) {
                        w3 = Bukkit.getWorlds().get(0);
                    } else {
                        w3 = ((Player)sender).getWorld();
                    }
                }
                if(failed2) {
                    int y;
                    try {
                        y = Integer.parseInt(args[1]);
                    } catch(NumberFormatException e) {
                        Command.sendGenericSyntaxError(sender, this);
                        return;
                    }
                    switch (args[0].toLowerCase()) {
                        case "rainy":
                        case "rain":
                        case "storm":
                        case "stormy":
                        case "thunder":
                        case "lightning":
                        case "snow":
                            w3.setStorm(true);
                            w3.setWeatherDuration(y * TICKS_IN_SECOND);
                            sendWeatherMessage(sender, WeatherType.STORM, w3.getThunderDuration(), w3);
                            break;
                        case "sun":
                        case "clear":
                            w3.setStorm(false);
                            w3.setWeatherDuration(y * TICKS_IN_SECOND);
                            sendWeatherMessage(sender, WeatherType.SUN, w3.getWeatherDuration(), w3);
                            break;
                        default:
                            Command.sendWorldMessage(sender, args[0]);
                    }
                } else {
                    switch (args[1].toLowerCase()) {
                        case "rainy":
                        case "rain":
                        case "storm":
                        case "stormy":
                        case "thunder":
                        case "lightning":
                        case "snow":
                            w3.setStorm(true);
                            sendWeatherMessage(sender, WeatherType.STORM, w3.getThunderDuration(), w3);
                            break;
                        case "sun":
                        case "clear":
                            w3.setStorm(false);
                            sendWeatherMessage(sender, WeatherType.SUN, w3.getWeatherDuration(), w3);
                            break;
                        default:
                            Command.sendGenericSyntaxError(sender, this);
                    }
                }
                return;
            case 3:
                World w4 = Bukkit.getWorld(args[0]);
                if(w4 == null) {
                    Command.sendWorldMessage(sender, args[0]);
                    return;
                }
                try {
                    int zh = Integer.parseInt(args[2]);
                    switch (args[1].toLowerCase()) {
                        case "rainy":
                        case "rain":
                        case "storm":
                        case "stormy":
                        case "thunder":
                        case "lightning":
                        case "snow":
                            w4.setStorm(true);
                            w4.setWeatherDuration(zh * TICKS_IN_SECOND);
                            sendWeatherMessage(sender, WeatherType.STORM, w4.getThunderDuration(), w4);
                            break;
                        case "sun":
                        case "clear":
                            w4.setStorm(false);
                            w4.setWeatherDuration(zh * TICKS_IN_SECOND);
                            sendWeatherMessage(sender, WeatherType.SUN, w4.getWeatherDuration(), w4);
                            break;
                        default:
                            Command.sendGenericSyntaxError(sender, this);
                    }
                } catch(NumberFormatException e) {
                    Command.sendGenericSyntaxError(sender, this);
                    return;
                }
                return;
            default:
                Command.sendGenericSyntaxError(sender, this);
        }
    }

    private void sendWeatherMessage(CommandSender sender, WeatherType w, int duration, World world) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.weather-set", w.getName(), Integer.toString(duration / TICKS_IN_SECOND), world.getName()));
    }

    private void sendConsoleWeatherReport(CommandSender sender, boolean storming, int duration, World w) {
        sender.sendMessage(M.m("command-message-text.weather-console", (storming ? "storming" : "clear"), Integer.toString(duration / TICKS_IN_SECOND), w.getName()));
    }

    private void sendWeatherReport(CommandSender sender, boolean storming, int duration, double temperature, double humidity, World w) {
        DecimalFormat d = new DecimalFormat("0.00");
        String temp = d.format(calcTemperature(temperature, w));
        sender.sendMessage(M.m("command-message-text.weather", (storming ? "storming" : "clear"), Integer.toString(duration / TICKS_IN_SECOND), temp, d.format(calcHumidity(humidity)), w.getName()));
    }

    private double calcTemperature(double t, World w) {
        Bukkit.getLogger().info(Double.toString(t));
        double x = (int)(t * 80.0D);
        x += (new Random().nextDouble() - 0.5D);
        long z = w.getTime();
        double fin;
        if(z >= 0 && z < 6000) {
            fin = 10.0D * (double)z / 6000.0D;
        } else if(z >= 6000 && z < 12000) {
            fin = 10.0D * (1 - (z - 6000) / 6000);
        } else if(z >= 12000 && z < 18000) {
            fin = -10.0D * (z - 12000.0D) / 6000.0D;
        } else if(z >= 18000 && z < 24000) {
            fin = -10.0D * (1 - (z - 18000) / 6000);
        } else {
            fin = 0.0D;
        }
        x += fin;
        if(w.hasStorm())
            x -= 3.0D + (new Random().nextDouble() - 0.5D);
        return x;
    }

    private double calcHumidity(double h) {
        return 100.0D * h + 2.0D*(new Random().nextDouble()) - 1.0D;
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    @Override
    public String getName() {
        return name;
    }

}
