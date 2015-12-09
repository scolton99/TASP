package tech.spencercolton.tasp.Entity;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Warning;

import java.util.UUID;

public class OfflinePerson {

    private PlayerData data;
    private UUID uid;
    private String name;

    public OfflinePerson(OfflinePlayer p) {
        this.data = null;
        this.uid = p.getUniqueId();
        this.name = p.getName();
    }

    public static OfflinePerson getByUUID(UUID u) {
        return new OfflinePerson(Bukkit.getOfflinePlayer(u));
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.uid);
    }

    public UUID getUid() {
        return this.uid;
    }

    @Warning(reason = "This function's value may be outdated")
    public String getName() {
        return this.name;
    }

}
