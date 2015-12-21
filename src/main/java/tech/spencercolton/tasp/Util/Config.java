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

    public static ChatColor c3() {
        ChatColor c = ChatColor.valueOf(s.getString("tertiary-color"));

        if(c != null && c != ChatColor.BOLD && c != ChatColor.ITALIC && c != ChatColor.MAGIC && c != ChatColor.RESET && c != ChatColor.STRIKETHROUGH && c != ChatColor.UNDERLINE) {
            return c;
        } else {
            return ChatColor.GREEN;
        }
    }

    public static ChatColor c4() {
        ChatColor c = ChatColor.valueOf(s.getString("quaternary-color"));

        if(c != null && c != ChatColor.BOLD && c != ChatColor.ITALIC && c != ChatColor.MAGIC && c != ChatColor.RESET && c != ChatColor.STRIKETHROUGH && c != ChatColor.UNDERLINE) {
            return c;
        } else {
            return ChatColor.AQUA;
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

    public static String getString(String path) {
        return (String)get(path);
    }

    public static Object get(String path) {
        return s.get(path);
    }

    public static int getInt(String path) {
        return (int)s.get(path);
    }

    public static float getFloat(String path) {
        return (float)s.get(path);
    }

    public static boolean getBoolean(String path) {
        return (boolean)s.get(path);
    }

    public static int AFKTime() {
        return 1200 * (int)s.get("afk-timeout");
    }

}
