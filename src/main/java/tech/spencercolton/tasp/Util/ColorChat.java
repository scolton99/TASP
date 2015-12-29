package tech.spencercolton.tasp.Util;

import org.bukkit.ChatColor;

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
