package tech.spencercolton.tasp.Commands;

import com.sun.deploy.uitoolkit.ui.ConsoleController;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.M;

import java.text.DecimalFormat;

/**
 * @author Spencer Colton
 */
public class WeatherCmd extends TASPCommand {

    private static final String syntax = "/weather [world [type] [time]] | [type] [time]";
    public static final String name = "weather";
    private static final String consoleSyntax = syntax;
    private static final String permission = "tasp.weather";

    String[] acceptableWeathers = {"rainy", "rain", "storm", "stormy", "thunder", "lightning", "sun", "clear", "snow"};

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
                    sender.sendMessage(M.m("command-message-text.weather-console", (w.hasStorm() ? "storming" : "not storming"), Integer.toString(w.getWeatherDuration())));
                } else {
                    Player p = (Player)sender;
                    Location l = p.getLocation();
                    sender.sendMessage(M.m("command-message-text.weather", (w.hasStorm() ? "storming" : "not storming"), Integer.toString(w.getWeatherDuration()), new DecimalFormat("0.00").format(w.getTemperature(l.getBlockX(), l.getBlockZ())), new DecimalFormat("0.00").format(w.getHumidity(l.getBlockX(), l.getBlockZ()))));
                }
            case 1:
                World w2 = Bukkit.getWorld(args[0]);
                boolean failed = false;
                if(w2 == null) {
                    failed = true;
                    if(sender instanceof ConsoleCommandSender) {
                        w2 = Bukkit.getWorlds().get(0);
                    } else {
                        w = ((Player)sender).getWorld();
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

                    }
                } else {

                }
        }
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
