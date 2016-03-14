package tech.spencercolton.tasp.Storage;

import tech.spencercolton.tasp.Configuration.Config;
import tech.spencercolton.tasp.Entity.Person;

import java.util.List;
import java.util.Map;

/**
 * This class is designed to hold data about a player or person.  The data is read from JSON files stored in a
 * {@code players} directory under the plugin's main directory.  The class also provides various methods for reading and
 * manipulating the data.
 *
 * @author Spencer Colton
 * @since 0.0.1-001
 */
public class PlayerData {

    /**
     * The object holding the player's data.
     */
    private PlayerDataProvider data;

    /**
     * The {@link Person} for whom this object holds data.
     */
    private final Person p;

    /**
     * Constructs a new PlayerData object by loading data for the player from the player's JSON file, or creating a new
     * JSON file in the case that it doesn't exist.
     *
     * @param p The {@link Person} for whom this object holds data.
     */
    public PlayerData(Person p) {
        this.p = p;

        if (Config.configDatabase()) {
            this.data = new DatabasePlayerData(this.p);
        } else {
            this.data = new JSONPlayerData(this.p);
        }
    }

    public int getInt(String path) {
        return this.data.getInt(path);
    }

    public boolean getBoolean(String path) {
        return this.data.getBoolean(path);
    }

    public String getString(String path) {
        return this.data.getString(path);
    }

    public Map getMap(String path) {
        return this.data.getMap(path);
    }

    public List getList(String path) {
        return this.data.getList(path);
    }

    public void setString(String path, String string) {
        this.data.setString(path, string);
    }

    public void setInt(String path, int i) {
        this.data.setInt(path, i);
    }

    public void setMap(String path, Map m) {
        this.data.setMap(path, m);
    }

    public void setList(String path, List l) {
        this.data.setList(path, l);
    }

    public void setBoolean(String path, boolean b) {
        this.data.setBoolean(path, b);
    }

}
