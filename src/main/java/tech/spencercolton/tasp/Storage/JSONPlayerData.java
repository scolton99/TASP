package tech.spencercolton.tasp.Storage;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.spencercolton.tasp.Configuration.Config;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.TASP;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Spencer Colton
 */
public class JSONPlayerData implements PlayerDataProvider {

    private JSONObject data;

    private final UUID u;

    @Getter
    private boolean valid;

    public JSONPlayerData(UUID u) {
        this.u = u;

        if (dataExists(u)) {
            this.data = loadData();
            this.valid = true;
        } else {
            this.genData();
            this.valid = true;
        }
    }

    public JSONPlayerData(UUID u, boolean load) {
        this.u = u;

        if (dataExists(u)) {
            this.data = loadData();
            this.valid = true;
        } else {
            if (load && Bukkit.getPlayer(u) != null) {
                this.genData();
                this.valid = true;
            } else {
                this.valid = false;
            }
        }
    }

    private JSONObject loadData() {
        File f = new File(TASP.dataFolder().getAbsolutePath() + File.separator + "players" + File.separator + this.u.toString() + ".json");
        assert f.exists();

        JSONParser p = new JSONParser();
        try (FileReader fa = new FileReader(f)) {
            return (JSONObject) p.parse(fa);
        } catch (IOException | ParseException e) {
            Bukkit.getLogger().warning("Error parsing player data:");
            e.printStackTrace();
            return null;
        }
    }

    private static boolean dataExists(UUID s) {
        String p1 = TASP.dataFolder().getAbsolutePath();
        p1 += File.separator + "players" + File.separator + s + ".json";
        return new File(p1).exists();
    }

    @SuppressWarnings("unchecked")
    private void genData() {
        this.data = new JSONObject();
        Player p = Bukkit.getPlayer(u);

        assert p != null;

        this.data.put("lastName", p.getName());
        this.data.put("UUID", this.u.toString());
        this.data.put("firstSeen", new Date().toString());
        this.data.put("lastIP", p.getAddress().getHostString());
        this.writeData();
    }

    public void writeData() {
        try {
            FileWriter f = new FileWriter(new File(this.getPlayerDataPath()));
            f.write(this.data.toString());
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getLogger().warning(Config.err() + "Couldn't write player data for player " + Person.getMostRecentName(u) + " with filename " + this.getPlayerDataPath());
        }
    }

    private String getPlayerDataPath() {
        return TASP.dataFolder().getAbsolutePath() + File.separator + "players" + File.separator + u.toString() + ".json";
    }

    @Override
    public Boolean getBoolean(String s) {
        return !(this.data.get(s) == null || !((boolean) this.data.get(s)));
    }

    @Override
    public List getList(String s) {
        return (List) this.data.get(s);
    }

    @Override
    public Map getMap(String s) {
        return (Map) this.data.get(s);
    }

    @Override
    public String getString(String s) { return (String) this.data.get(s); }

    @Override
    public Integer getInt(String s) {
        return (int) this.data.get(s);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setInt(String s, Integer i) {
        this.data.put(s, i);
        this.writeData();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setString(String s, String h) {
        this.data.put(s, h);
        this.writeData();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setMap(String s, Map m) {
        this.data.put(s, m);
        this.writeData();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setBoolean(String s, Boolean b) {
        this.data.put(s, b);
        this.writeData();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setList(String s, List l) {
        this.data.put(s, l);
        this.writeData();
    }

}
