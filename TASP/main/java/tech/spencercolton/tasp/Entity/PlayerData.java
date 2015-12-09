package tech.spencercolton.tasp.Entity;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.TASP;

import java.io.File;

public class PlayerData {

    private

    public PlayerData(Person p) {

    }

    public String getString(String s) {

    }

    public static boolean dataExists(String s) {
        String p1 = TASP.dataFolder().getAbsolutePath();
        p1 += "/players/" + s;
        return false;
    }

    public static boolean dataExists(OfflinePerson p) {
        return dataExists(p.getName());
    }

    public static boolean dataExists(OfflinePlayer p) {

    }

}
