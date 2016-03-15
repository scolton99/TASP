package tech.spencercolton.tasp.Util;

import tech.spencercolton.tasp.Configuration.Config;
import tech.spencercolton.tasp.Storage.DatabasePlayerData;
import tech.spencercolton.tasp.Storage.JSONPlayerData;
import tech.spencercolton.tasp.Storage.PlayerDataProvider;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This class is designed to hold data about a player or person.  The data is read from JSON files stored in a
 * {@code players} directory under the plugin's main directory.  The class also provides various methods for reading and
 * manipulating the data.
 *
 * @author Spencer Colton
 * @since 0.0.1-001
 */
public class PlayerData {

    private PlayerDataProvider data;

    private final UUID u;

    public PlayerData(UUID u) {
        this.u = u;

        if (Config.configDatabase()) {
            this.data = new DatabasePlayerData(this.u);
        } else {
            this.data = new JSONPlayerData(this.u);
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
