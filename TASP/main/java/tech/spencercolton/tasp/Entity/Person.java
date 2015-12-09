package tech.spencercolton.tasp.Entity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class Person extends OfflinePerson {

    private final String name;
    private final UUID uid;

    private static List<Person> people = new ArrayList<>();
    private static HashMap<UUID, Person> UIDpeople = new HashMap<>();
    private static HashMap<String, Person> namePeople = new HashMap<>();

    private HashMap<String, Object> stats = new HashMap<>();

    private PlayerData data;

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

    public static List<Person> getPeople() {
        return people;
    }

    public static Person get(UUID u) {
        return UIDpeople.get(u);
    }

    public static Person get(String name) {
        return namePeople.get(name);
    }

    public static Person get(Player p) {
        return UIDpeople.get(p.getUniqueId());
    }

    public Object getStat(String s) {
        return stats.get(s);
    }

    public void setStat(String s, Object o) {
        this.stats.put(s, o);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uid);
    }

    @Override
    public String getName() {
        return getPlayer().getName();
    }

    @Override
    public UUID getUid() {
        return this.uid;
    }

    public PlayerData getData() {
        return this.data;
    }

    public void save() {
        this.data.writeData();
    }

    public void reloadData() {
        this.data = new PlayerData(this);
    }

    public void setData(String s, Object o) {

    }

}

