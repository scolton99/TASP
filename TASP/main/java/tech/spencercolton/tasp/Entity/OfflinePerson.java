package tech.spencercolton.tasp.Entity;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Warning;

import java.util.UUID;

/**
 * The main class for holding data about offline players on the server that is not covered by the server's
 * implementation of {@code OfflinePlayer}.
 *
 * @author Spencer Colton
 * @since 0.0.1-001
 */
public class OfflinePerson {

    /**
     * Holds the offline player's data.
     */
    @SuppressWarnings("unused")
    private PlayerData data;

    /**
     * Holds the offline player's unique ID.
     */
    private UUID uid;

    /**
     * Holds the offline player's name.
     */
    private String name;

    /**
     * Generates an OfflinePerson from a server offline player.
     *
     * @param p An {@link OfflinePlayer} from which to generate this OfflinePerson.
     */
    public OfflinePerson(OfflinePlayer p) {
        this.data = null;
        this.uid = p.getUniqueId();
        this.name = p.getName();
    }

    /**
     * Creates an OfflinePerson from a unique ID.
     *
     * @param u The unique ID to generate from.
     * @return A new OfflinePerson.
     */
    @SuppressWarnings("unused")
    public static OfflinePerson getByUUID(UUID u) {
        return new OfflinePerson(Bukkit.getOfflinePlayer(u));
    }

    /**
     * Retrieves the {@link OfflinePlayer} with which this OfflinePerson is associated.
     *
     * @return The associated {@link OfflinePlayer}.
     */
    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.uid);
    }

    /**
     * Retrieves this OfflinePerson's unique ID, the same as the one assigned by the server.
     *
     * @return The OfflinePerson's unique ID.
     */
    @SuppressWarnings("unused")
    public UUID getUid() {
        return this.uid;
    }

    /**
     * Retrieves this OfflinePerson's name.
     *
     * @return The name associated with this OfflinePerson.
     */
    @Warning(reason = "This function's value may be outdated")
    public String getName() {
        return this.name;
    }


}
