package tech.spencercolton.tasp.Util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Config {

    private static final int TICKS_PER_MINUTE = 1200;
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
        return s.getString(path);
    }

    public static boolean getBoolean(String path) {
        return s.getBoolean(path);
    }

    public static List<String> getListString(String path) {
        return s.getStringList(path);
    }

    public static int afkTime() {
        return TICKS_PER_MINUTE * (int) s.get("afk-timeout");
    }

    public static int getInt(String path) {
        return s.getInt(path);
    }

    public static char getColorCode() {
        return s.getString("color-code").charAt(0);
    }

    public static boolean coloredText() {
        return s.getBoolean("color-text");
    }

    public static int getSpawnLimit() {
        return s.getInt("spawnmob-limit");
    }

    public static char getFilterChar() {
        return s.getString("filter-character").charAt(0);
    }

}
