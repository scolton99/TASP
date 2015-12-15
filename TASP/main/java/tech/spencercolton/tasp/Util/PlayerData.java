package tech.spencercolton.tasp.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.spencercolton.tasp.Entity.OfflinePerson;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.TASP;

import java.io.*;
import java.util.*;

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
    private JSONObject data;

    /**
     * The {@link Person} for whom this object holds data.
     */
    private Person p;

    /**
     * Constructs a new PlayerData object by loading data for the player from the player's JSON file, or creating a new
     * JSON file in the case that it doesn't exist.
     *
     * @param p The {@link Person} for whom this object holds data.
     */
    public PlayerData(Person p) {
        if(!p.getOfflinePlayer().isOnline())
            return;
        this.p = p;
        if(dataExists(p)) {
            this.data = loadData();
        } else {
            genData();
        }
    }

    /**
     * Gets a data value from the JSONObject in the form of a {@code String}.
     *
     * @param s The property to get the value of.
     * @return The property's value.
     */
    @SuppressWarnings("unused")
    public String getString(String s) {
        return (String)this.data.get(s);
    }

    /**
     * Gets a data value from the JSONObject in the form of an {@code int}.
     *
     * @param s The property to get the value of.
     * @return The property's value.
     */
    @SuppressWarnings("unused")
    public Integer getInt(String s) {
        return (Integer)(this.data.get(s));
    }

    /**
     * Gets a data value from the JSONObject in the form of a {@code float}.
     *
     * @param s The property to get the value of.
     * @return The property's value.
     */
    @SuppressWarnings("unused")
    public Float getFloat(String s) {
        return (Float)(this.data.get(s));
    }

    /**
     * Gets a data value from the JSONObject in the form of a {@code JSONArray}.
     *
     * @param s The property to get the value of.
     * @return The property's value.
     */
    @SuppressWarnings("unused")
    public JSONArray getArray(String s) {
        return (JSONArray)(this.data.get(s));
    }

    public Boolean getBoolean(String s) {
        return (Boolean)(this.data.get(s));
    }

    /**
     * Checks to see if a data file exists for a certain unique ID.
     *
     * @param s The unique ID to be checked.
     * @return {@code true} if the data-file exists, and {@code false} if the data-file does not exist.
     */
    private static boolean dataExists(UUID s) {
        String p1 = TASP.dataFolder().getAbsolutePath();
        p1 += "/players/" + s + ".json";
        return new File(p1).exists();
    }

    /**
     * Checks to see if a data file exists for a certain person.
     *
     * @param p The person to be checked.
     * @return {@code true} if the data-file exists, and {@code false} if the data-file does not exist.
     * @see #dataExists(OfflinePlayer)
     */
    public static boolean dataExists(OfflinePerson p) {
        return dataExists(p.getOfflinePlayer().getUniqueId());
    }

    /**
     * Checks to see if a data file exists for a certain player.
     *
     * @param p The player to be checked.
     * @return {@code true} if the data-file exists, and {@code false} if the data-file does not exist.
     * @see #dataExists(OfflinePerson)
     */
    @SuppressWarnings("unused")
    public static boolean dataExists(OfflinePlayer p) {
        return dataExists(p.getUniqueId());
    }

    /**
     * Generates a bare-bones JSONObject to hold the player's data, then writes it to a file named after the player's
     * unique ID.
     */
    @SuppressWarnings("unchecked")
    private void genData() {
        this.data = new JSONObject();
        this.data.put("lastName", this.p.getName());
        this.data.put("UUID", this.p.getOfflinePlayer().getUniqueId().toString());
        this.data.put("firstSeen", new Date().toString());
        if(TASP.TASPPerms_link != null) {
            this.data.put("permissions", new ArrayList<>());
        }
        if(p.getOfflinePlayer().isOnline())
            this.data.put("lastIP", this.p.getOfflinePlayer().getPlayer().getAddress().getHostString());
        writeData();
    }

    /**
     * Loads a player's data from the JSON file with their unique ID.
     *
     * @return {@code null} if there was a parse error if the file was not found, or a {@code JSONObject} with data if
     * not.
     */
    private JSONObject loadData() {
        File f = new File(TASP.dataFolder().getAbsolutePath() + "/players/" + p.getOfflinePlayer().getUniqueId().toString() + ".json");
        if(!f.exists())
            return null;

        JSONParser p = new JSONParser();
        try (FileReader fa = new FileReader(f)){
            return (JSONObject) (p.parse(fa));
        } catch(IOException|ParseException e) {
            Bukkit.getLogger().warning("Error parsing player data:");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Takes the player's current dataset and writes it to a JSON file bearing his or her unique ID.
     *
     * @return {@code true} if the data was successfully written, {@code false} if not.
     */
    public boolean writeData() {
        try {
            FileWriter f = new FileWriter(new File(getPlayerDataPath()));
            f.write(this.data.toJSONString());
            f.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getLogger().warning(Config.err() + "Couldn't write player data for player " + p.getName() + " with filename " + getPlayerDataPath());
            return false;
        }
    }

    /**
     * Gets the player's data-file path.
     *
     * @return The path of the player's data file, in the form of a {@code String}.
     */
    private String getPlayerDataPath() {
        return TASP.dataFolder().getAbsolutePath() + "\\players\\" + this.p.getOfflinePlayer().getUniqueId().toString() + ".json";
    }

    /**
     * Fetches a {@link JSONObject} in the form of a {@link Map} from the specified name in the ephemeral data object.
     *
     * @param s The name of the object to fetch.
     * @return A {@link Map} corresponding the object with that name.
     */
    public Map getMap(String s) {
        return (Map)this.data.get(s);
    }

    @SuppressWarnings("unchecked")
    public void setObject(String s, Map m) {
        this.data.put(s, m);
        writeData();
    }

    @SuppressWarnings("unchecked")
    public void setString(String s, String h) {
        this.data.put(s, h);
        writeData();
    }

    @SuppressWarnings("unchecked")
    public void setBoolean(String s, boolean b) {
        this.data.put(s, b);
        writeData();
    }

    public List getList(String s) {
        return (List)this.data.get(s);
    }



    public List getPermissions() {
        return (List)this.data.get("permissions");
    }

}
