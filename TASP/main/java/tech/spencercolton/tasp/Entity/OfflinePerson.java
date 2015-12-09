package tech.spencercolton.tasp.Entity;

import org.bukkit.OfflinePlayer;

import java.util.List;

public class OfflinePerson {

    private static List<OfflinePerson> offlinePeople;

    public OfflinePerson(OfflinePlayer p) {
        // ...
        offlinePeople.add(this);
    }

    public static List<OfflinePerson> getOfflinePeople() {
        return offlinePeople;
    }

}
