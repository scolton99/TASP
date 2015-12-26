package tech.spencercolton.tasp.Util;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Random;

/**
 * @author Spencer Colton
 */
public class Weather {
    public static double calcTemperature(double t, World w) {
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

    public static double calcHumidity(double h) {
        return 100.0D * h + 2.0D*(new Random().nextDouble()) - 1.0D;
    }
}
