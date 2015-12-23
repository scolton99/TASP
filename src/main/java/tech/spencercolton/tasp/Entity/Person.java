package tech.spencercolton.tasp.Entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
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
public class Person {

    /**
     * Hold's the player's unique id.
     */
    private final UUID uid;

    /**
     * Holds a constant list of players who are currently online on the server.
     */
    private static final List<Person> people = new ArrayList<>();

    /**
     * Links each unique ID to a person object.
     */
    private static final Map<UUID, Person> UIDpeople = new HashMap<>();

    /**
     * Holds the player's data.
     * <p>
     *     Information such as IP address, last time seen, status of various properties (flying, god mode, etc.)
     * </p>
     */
    private PlayerData data;

    private CommandSender lastMessaged;
    private boolean afk;
    private String ip;
    private final HashMap<Material, List<String>> pts = new HashMap<>();

    /**
     * Constructs a person object from a player object.
     * <p>
     *     Sets the person's properties and adds them to the list of people that are currently online.
     * </p>
     *
     * @param p The player from which to generate a new Person.
     */
    public Person(Player p) {
        this.uid = p.getUniqueId();
        this.data = new PlayerData(this);
        // ...
        people.add(this);
        UIDpeople.put(this.uid, this);
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
     * Using a player object, returns the associated person object.
     *
     * @param p The player to be checked.
     * @return The {@link Person} associated with that {@code Player}.
     */
    public static Person get(Player p) {
        return UIDpeople.get(p.getUniqueId());
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
    public String getName() {
        return this.getPlayer().getName();
    }

    /**
     * {@inheritDoc}
     */
    public UUID getUid() {
        return this.uid;
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
     * Fetches the player's home from their data object, or returns {@code null} if the player has no home set.
     *
     * @return A {@link Location} containing the coordinates, pitch, world, and yaw of the player's home location,
     * or {@code null} if the player has no home set.
     */
    @SuppressWarnings("unchecked")
    public Location getHome() {
        Map h = this.data.getMap("home");

        if(h == null)
            return null;

        World w = Bukkit.getWorld(UUID.fromString((String)h.get("world")));
        if(w == null)
            return null;

        double x = Double.parseDouble((String)h.get("x"));
        double y = Double.parseDouble((String)h.get("y"));
        double z = Double.parseDouble((String)h.get("z"));
        float pitch = Float.parseFloat((String)h.get("pitch"));
        float yaw = Float.parseFloat((String)h.get("yaw"));

        return new Location(w, x, y, z, yaw, pitch);
    }

    @SuppressWarnings("unchecked")
    public void setHome(Location l) {
        Map m = new HashMap();

        m.put("world", l.getWorld().getUID().toString());
        m.put("x", Double.toString(l.getX()));
        m.put("y", Double.toString(l.getY()));
        m.put("z", Double.toString(l.getZ()));
        m.put("pitch", Float.toString(l.getPitch()));
        m.put("yaw", Float.toString(l.getYaw()));

        this.data.setObject("home", m);
    }

    public List<String> getBlockedPlayers() {
        return this.data.getList("blocked") == null ? new ArrayList<>() : this.data.getList("blocked");
    }

    public void blockPlayer(Person p) {
        List<String> ps = this.getBlockedPlayers();
        ps.add(p.getUid().toString());
        this.data.setList("blocked", ps);
    }

    public void unblockPlayer(Person p) {
        List<String> ps = this.getBlockedPlayers();
        ps.remove(p.getUid().toString());
        this.data.setList("blocked", ps);
    }

    public boolean isPlayerBlocked(Person p) {
        return this.getBlockedPlayers().contains(p.getUid().toString());
    }

    public CommandSender getLastMessaged() {
        return this.lastMessaged;
    }

    public void setLastMessaged(CommandSender p) {
        this.lastMessaged = p;
    }

    @SuppressWarnings("unchecked")
    public Map<String, List<String>> getPowertools() {
        return this.data.getMap("powertools");
    }

    public void setPowertool(Material m, String cmdLine) {
        Map<String, List<String>> x = this.getPowertools();

        Map<String, List<String>> map;
        if(x == null)
            map = new HashMap<>();
        else
            map = new HashMap<>(x);

        List<String> z = map.get(m.name().toLowerCase());
        List<String> list;
        if(z == null)
            list = new ArrayList<>();
        else
            list = new ArrayList<>(z);
        list.add(cmdLine);
        map.put(m.name().toLowerCase(), list);
        this.data.setObject("powertools", map);
    }

    public List<String> getPowertool(Material m) {
        Map<String, List<String>> x = this.getPowertools();
        if(x == null)
            return null;

        return x.get(m.name().toLowerCase());
    }

    public void clearPowertool(Material m) {
        Map<String, List<String>> x = this.getPowertools();
        if(x == null)
            return;
        x.remove(m.name().toLowerCase());
    }

    public static void unloadPerson(Person a) {
        people.remove(a);
        UIDpeople.remove(a.getUid());
    }

    public void writeData() {
        this.data.writeData();
    }

    /* */
    public boolean isStalker() {
        Boolean b = this.data.getBoolean("stalker");
        return !(b == null || !b);
    }

    public void setStalker(boolean b) {
        this.data.setBoolean("stalker", b);
    }
    /* */

    /* */
    public boolean isGod() {
        Boolean b = this.data.getBoolean("god");
        return !(b == null || !b);
    }

    public void setGod(boolean b) {
        this.data.setBoolean("god", b);
    }
    /* */

    /* */
    public boolean isFOM() {
        Boolean b = this.data.getBoolean("fom");
        return !(b == null || !b);
    }

    public void setFOM(boolean b) {
        this.data.setBoolean("fom", b);
    }
    /* */

    /* */
    public boolean isAfk() {
        return this.afk;
    }

    public void setAfk(boolean afk) {
        this.afk = afk;
    }
    /* */

    /* */
    public boolean isMuted() {
        Boolean b = this.data.getBoolean("muted");
        return !(b == null || !b);
    }

    public void setMuted(boolean b) {
        this.data.setBoolean("muted", b);
    }
    /* */

    /* */
    public boolean isBuddha() {
        Boolean b = this.data.getBoolean("buddha");
        return !(b == null || !b);
    }

    public void setBuddha(boolean b) {
        this.data.setBoolean("buddha", b);
    }
    /* */

}

