package tech.spencercolton.tasp.Configuration;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import tech.spencercolton.tasp.Enums.ConfigType;

import java.util.List;
import java.util.Map;

public class Config {

    private static final int TICKS_PER_MINUTE = 1200;
    private static Configuration s;

    private static ConfigType type;

    public static void loadConfig(ConfigType types) {
        type = types;
        switch (type) {
            case MYSQL: {
                s = new DatabaseConfiguration();
                break;
            }
            case YAML: {
                s = new YAMLConfiguration(Bukkit.getPluginManager().getPlugin("TASP").getConfig());
                break;
            }
        }
    }

    public static ChatColor c1() {
        ChatColor c = ChatColor.valueOf(s.getString("primary-color"));

        if (c != null && c != ChatColor.BOLD && c != ChatColor.ITALIC && c != ChatColor.MAGIC && c != ChatColor.RESET && c != ChatColor.STRIKETHROUGH && c != ChatColor.UNDERLINE) {
            return c;
        } else {
            return ChatColor.GOLD;
        }
    }

    public static ChatColor c2() {
        ChatColor c = ChatColor.valueOf(s.getString("secondary-color"));

        if (c != null && c != ChatColor.BOLD && c != ChatColor.ITALIC && c != ChatColor.MAGIC && c != ChatColor.RESET && c != ChatColor.STRIKETHROUGH && c != ChatColor.UNDERLINE) {
            return c;
        } else {
            return ChatColor.DARK_RED;
        }
    }

    public static ChatColor c3() {
        ChatColor c = ChatColor.valueOf(s.getString("tertiary-color"));

        if (c != null && c != ChatColor.BOLD && c != ChatColor.ITALIC && c != ChatColor.MAGIC && c != ChatColor.RESET && c != ChatColor.STRIKETHROUGH && c != ChatColor.UNDERLINE) {
            return c;
        } else {
            return ChatColor.GREEN;
        }
    }

    public static ChatColor c4() {
        ChatColor c = ChatColor.valueOf(s.getString("quaternary-color"));

        if (c != null && c != ChatColor.BOLD && c != ChatColor.ITALIC && c != ChatColor.MAGIC && c != ChatColor.RESET && c != ChatColor.STRIKETHROUGH && c != ChatColor.UNDERLINE) {
            return c;
        } else {
            return ChatColor.AQUA;
        }
    }


    public static ChatColor err() {
        ChatColor c = ChatColor.valueOf(s.getString("error-color"));

        if (c != null && c != ChatColor.BOLD && c != ChatColor.ITALIC && c != ChatColor.MAGIC && c != ChatColor.RESET && c != ChatColor.STRIKETHROUGH && c != ChatColor.UNDERLINE) {
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
        return TICKS_PER_MINUTE * s.getInt("afk-timeout");
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

    public static boolean obfuscate() {
        return s.getBoolean("obfuscate-bad-words");
    }

    public static char getFilterChar() {
        return s.getString("filter-character").charAt(0);
    }

    public static boolean filterChat() {
        return s.getBoolean("chat-filter");
    }

    public static boolean teleportCooldown() {
        return !(s.getInt("teleport-cooldown") == 0);
    }

    public static int teleportCooldownInt() {
        return s.getInt("teleport-cooldown");
    }

    public static boolean isTeleportRequestLimited() {
        return !(s.getInt("teleport-request-time-limit") == 0);
    }

    public static int teleportRequestLimit() {
        return s.getInt("teleport-request-time-limit") * 1000;
    }

    public static Map getMap(String path) {
        return s.getMap(path);
    }

    public static boolean configDatabase() {
        return s.getBoolean("use-config-database");
    }

}
