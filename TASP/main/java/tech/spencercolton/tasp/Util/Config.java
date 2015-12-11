package tech.spencercolton.tasp.Util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class Config {

    private static ConfigurationSection s;

    public static void loadConfig(ConfigurationSection s1) {
        s = s1;
    }

    public static ChatColor c1() {
        ChatColor c = ChatColor.valueOf(s.getString("primary-color"));

        if(c != null && c != ChatColor.BOLD && c != ChatColor.ITALIC && c != ChatColor.MAGIC && c != ChatColor.RESET && c != ChatColor.STRIKETHROUGH && c != ChatColor.UNDERLINE) {
            return c;
        } else {
            return ChatColor.GOLD;
        }
    }

    public static ChatColor c2() {
        ChatColor c = ChatColor.valueOf(s.getString("secondary-color"));

        if(c != null && c != ChatColor.BOLD && c != ChatColor.ITALIC && c != ChatColor.MAGIC && c != ChatColor.RESET && c != ChatColor.STRIKETHROUGH && c != ChatColor.UNDERLINE) {
            return c;
        } else {
            return ChatColor.GOLD;
        }
    }

}
