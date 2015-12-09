package tech.spencercolton.tasp.Entity;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Warning;
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
        if(!p.getOfflinePlayer().isOnline())
            return;
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

    @Warning(reason = "Data from this function directly should not be treated as current")
    public static boolean dataExists(String s) {
        String p1 = TASP.dataFolder().getAbsolutePath();
        p1 += "/players/" + s + ".json";
        return new File(p1).exists();
    }

    /*
        Data from the next two functions can be treated as current information because the user must
        be logged in to access either of them.
     */
    public static boolean dataExists(OfflinePerson p) {
        return dataExists(p.getOfflinePlayer().getUniqueId().toString());
    }

    public static boolean dataExists(OfflinePlayer p) {
        return dataExists(p.getUniqueId().toString());
    }

    @SuppressWarnings("unchecked")
    private void genData() {
        this.data = new JSONObject();
        this.data.put("lastName", this.p.getName());
        this.data.put("UUID", this.p.getOfflinePlayer().getUniqueId());
        this.data.put("firstSeen", new Date().toString());
        if(p.getOfflinePlayer().isOnline())
            this.data.put("lastIP", this.p.getOfflinePlayer().getPlayer().getAddress().getHostString());
        writeData();
    }

    private JSONObject loadData() {
        File f = new File(TASP.dataFolder().getAbsolutePath() + "/players/" + p.getOfflinePlayer().getUniqueId().toString() + ".json");
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
        return TASP.dataFolder().getAbsolutePath() + "/players/" + this.p.getOfflinePlayer().getUniqueId().toString() + ".json";
    }


}
