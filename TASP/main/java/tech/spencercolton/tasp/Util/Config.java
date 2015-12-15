package tech.spencercolton.tasp.Util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    private static FileConfiguration s;

    public static void loadConfig(FileConfiguration s1) {
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
            return ChatColor.DARK_RED;
        }
    }

    public static ChatColor err() {
        ChatColor c = ChatColor.valueOf(s.getString("error-color"));

        if(c != null && c != ChatColor.BOLD && c != ChatColor.ITALIC && c != ChatColor.MAGIC && c != ChatColor.RESET && c != ChatColor.STRIKETHROUGH && c != ChatColor.UNDERLINE) {
            return c;
        } else {
            return ChatColor.RED;
        }
    }

    public String getString(String path) {
        return (String)get(path);
    }

    public Object get(String path) {
        return s.get(path);
    }

    public int getInt(String path) {
        return (int)s.get(path);
    }

    public float getFloat(String path) {
        return (float)s.get(path);
    }

}
