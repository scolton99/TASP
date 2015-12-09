package tech.spencercolton.tasp.Entity;

import org.bukkit.OfflinePlayer;
import org.bukkit.Warning;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.TASP;

import java.io.File;

public class PlayerData {

    public PlayerData(Person p) {

    }

    public String getString(String s) {

    }

    @Warning(reason = "Data from this function should not be treated as current")
    public static boolean dataExists(String s) {
        String p1 = TASP.dataFolder().getAbsolutePath();
        p1 += "/players/" + s + ".json";

        return false;
    }

    public static boolean dataExists(Person p) {
        return dataExists(p.getName());
    }

    public static boolean dataExists(Player p) {

    }

}
