package tech.spencercolton.tasp.Util;

import org.bukkit.ChatColor;
import tech.spencercolton.tasp.Configuration.Config;

/**
 * @author Spencer Colton
 */
public class ColorChat {

    public static String color(String s) {
        if (!Config.coloredText())
            return s;
        return ChatColor.translateAlternateColorCodes(Config.getColorCode(), s);
    }

}
