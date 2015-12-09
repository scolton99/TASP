package tech.spencercolton.tasp.Entity;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Warning;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.spencercolton.tasp.TASP;

import java.io.*;
import java.util.Date;

public class PlayerData {

    private JSONObject data;

    private Person p;

    public PlayerData(Person p) {
        this.p = p;
        if(dataExists(p)) {
            this.data = loadData();
        } else {
            genData();
        }
    }

    public String getString(String s) {
        return (String)this.data.get(s);
    }

    public Integer getInt(String s) {
        return (Integer)(this.data.get(s));
    }

    public Float getFloat(String s) {
        return (Float)(this.data.get(s));
    }

    public JSONArray getArray(String s) {
        return (JSONArray)(this.data.get(s));
    }

    @Deprecated
    @Warning(reason = "Data from this function should not be treated as current")
    public static boolean dataExists(String s) {
        String p1 = TASP.dataFolder().getAbsolutePath();
        p1 += "/players/" + s + ".json";
        return new File(p1).exists();
    }

    /*
        Data from the next two functions can be treated as current information because the user must
        be logged in to access either of them.
     */
    public static boolean dataExists(Person p) {
        return dataExists(p.getName());
    }

    public static boolean dataExists(Player p) {
        return dataExists(p.getName());
    }

    @SuppressWarnings("unchecked")
    private void genData() {
        this.data = new JSONObject();
        this.data.put("lastName", this.p.getName());
        this.data.put("UUID", this.p.getPlayer().getUniqueId());
        this.data.put("firstSeen", new Date().toString());
        this.data.put("lastIP", this.p.getPlayer().getAddress().getHostString());
        writeData();
    }

    private JSONObject loadData() {
        File f = new File(TASP.dataFolder().getAbsolutePath() + "/players/" + p.getPlayer().getUniqueId().toString() + ".json");
        if(!f.exists())
            return null;

        JSONParser p = new JSONParser();
        try {
            return (JSONObject) (p.parse(new FileReader(f)));
        } catch(IOException|ParseException e) {
            Bukkit.getLogger().warning("Error parsing player data:");
            e.printStackTrace();
            return null;
        }
    }

    public boolean writeData() {
        try (FileWriter f = new FileWriter(getPlayerDataPath())) {
            f.write(this.data.toJSONString());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private String getPlayerDataPath() {
        return TASP.dataFolder().getAbsolutePath() + "/players/" + this.p.getPlayer().getUniqueId().toString() + ".json";
    }

}
