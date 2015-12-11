package tech.spencercolton.tasp.Entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.PlayerData;

import java.util.*;

/**
 * The main class for holding data about players on the server that is not covered by the server's implementation of
 * {@code Player}.
 *
 * @author Spencer Colton
 * @since 0.0.1-001
 */
@SuppressWarnings("unused")
public class Person extends OfflinePerson {

    /**
     * Hold's the player's name.
     */
    private final String name;

    /**
     * Hold's the player's unique id.
     */
    private final UUID uid;

    /**
     * Holds a constant list of players who are currently online on the server.
     */
    private static List<Person> people = new ArrayList<>();

    /**
     * Links each unique ID to a person object.
     */
    private static HashMap<UUID, Person> UIDpeople = new HashMap<>();

    /**
     * Links each name to a person object.
     */
    private static HashMap<String, Person> namePeople = new HashMap<>();

    /**
     * Holds the players statistics.
     *
     * @deprecated This value will soon be replaced by the new {@link PlayerData} model.
     */
    @Deprecated
    private HashMap<String, Object> stats = new HashMap<>();

    /**
     * Holds the player's data.
     * <p>
     *     Information such as IP address, last time seen, status of various properties (flying, god mode, etc.)
     * </p>
     */
    private PlayerData data;

    /**
     * Constructs a person object from a player object.
     * <p>
     *     Sets the person's properties and adds them to the list of people that are currently online.
     * </p>
     *
     * @param p The player from which to generate a new Person.
     */
    public Person(Player p) {
        super(p);
        this.name = p.getName();
        this.uid = p.getUniqueId();
        this.data = new PlayerData(this);
        // ...
        people.add(this);
        UIDpeople.put(this.uid, this);
        namePeople.put(this.name, this);
    }

    /**
     * Gives the list of all users who are currently online.
     *
     * @return A list of people who are currently online.
     */
    public static List<Person> getPeople() {
        return people;
    }

    /**
     * Using a player's unique ID, returns the associated person object.
     *
     * @param u The unique ID to be checked.
     * @return The {@link Person} associated with that unique ID.
     */
    public static Person get(UUID u) {
        return UIDpeople.get(u);
    }

    /**
     * Using a player's name, returns the associated person object.
     *
     * @param name The name to be checked.
     * @return The {@link Person} associated with that name.
     */
    public static Person get(String name) {
        return namePeople.get(name);
    }

    /**
     * Using a player object, returns the associated person object.
     *
     * @param p The player to be checked.
     * @return The {@link Person} associated with that {@code Player}.
     */
    public static Person get(Player p) {
        return UIDpeople.get(p.getUniqueId());
    }

    /**
     * Gets a player's stat by its name.
     *
     * @deprecated This value will soon be replaced by the new {@link PlayerData} model.
     * @param s The name of the property.
     * @return The property's value.
     */
    @Deprecated
    public Object getStat(String s) {
        return stats.get(s);
    }

    /**
     * Sets a player's stat by its name.
     *
     * @deprecated This value will soon be replaced by the new {@link PlayerData} model.
     * @param s The name of the property.
     * @param o The new value.
     */
    @Deprecated
    public void setStat(String s, Object o) {
        this.stats.put(s, o);
    }

    /**
     * Gets the {@link Player} associated with this {@code Person} object.
     *
     * @return The associated {@link Player}.
     */
    public Player getPlayer() {
        return Bukkit.getPlayer(this.uid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return getPlayer().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID getUid() {
        return this.uid;
    }

    /**
     * Gets the data associated with this Person.
     *
     * @return The player's data object.
     */
    public PlayerData getData() {
        return this.data;
    }

    /**
     * Writes this person's data to file.
     */
    public void save() {
        this.data.writeData();
    }

    /**
     * Reads this person's data from file and overwrites the ephemeral data.
     */
    public void reloadData() {
        this.data = new PlayerData(this);
    }

    /**
     * Sets a certain property of this player to a certain value.
     *
     * @param s The property name.
     * @param o The new value.
     */
    public void setDataPoint(String s, Object o) {

    }

    /**
     * Fetches the player's home from their data object, or returns {@code null} if the player has no home set.
     *
     * @return A {@link Location} containing the coordinates, pitch, world, and yaw of the player's home location,
     * or {@code null} if the player has no home set.
     */
    public Location getHome() {
        Map h = this.data.getMap("home");

        if(h == null)
            return null;

        World w = Bukkit.getWorld(UUID.fromString((String)h.get("world")));
        if(w == null)
            return null;

        double x = (double)h.get("x");
        double y = (double)h.get("y");
        double z = (double)h.get("z");
        float pitch = (float)h.get("pitch");
        float yaw = (float)h.get("yaw");

        return new Location(w, x, y, z, yaw, pitch);
    }

    @SuppressWarnings("unchecked")
    public void setHome(Location l) {
        Map m = new HashMap();

        m.put("world", l.getWorld().getUID().toString());
        m.put("x", l.getX());
        m.put("y", l.getY());
        m.put("z", l.getZ());
        m.put("pitch", l.getPitch());
        m.put("yaw", l.getYaw());

        this.data.setObject("home", m);
    }

}

