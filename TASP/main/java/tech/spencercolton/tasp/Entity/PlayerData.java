package tech.spencercolton.tasp.Entity;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Warning;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.spencercolton.tasp.TASP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PlayerData {

    private String name;
    private JSONObject data;

    public PlayerData(Person p) {
        this.name = p.getName();
        if(dataExists(p)) {
            this.data = loadData();
        } else {
            this.data = genData();
        }
    }

    public String getString(String s) {
        return null;
    }

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

    private JSONObject genData() {

    }

    private JSONObject loadData() {
        File f = new File(TASP.dataFolder().getAbsolutePath() + "/players/" + name + ".json");
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

}
