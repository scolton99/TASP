package tech.spencercolton.tasp.Util;

import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.spencercolton.tasp.TASP;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Spencer Colton
 */
public class State {

    private static JSONObject states;

    @SuppressWarnings("unchecked")
    public static void initStates() {
        File f = new File(TASP.dataFolder().getAbsolutePath() + File.separator + "states.json");
        if (!f.exists()) {
            states = new JSONObject();
            writeStates();
        } else {
            try {
                FileReader a = new FileReader(f);
                states = (JSONObject) new JSONParser().parse(a);
            } catch (IOException | ParseException e) {
                states = new JSONObject();
                writeStates();
                Bukkit.getLogger().warning("Couldn't load states.");
                e.printStackTrace();
            }
        }
    }

    public static void writeStates() {
        try {
            FileWriter f = new FileWriter(new File(TASP.dataFolder().getAbsolutePath() + File.separator + "states.json"));
            f.write(states.toString());
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getLogger().warning(Config.err() + "Couldn't write states.");
        }
    }

    public static boolean getBoolean(String path) {
        return states.get(path) == null || (boolean)states.get(path);
    }

    @SuppressWarnings("unchecked")
    public static void setBoolean(String path, boolean b) {
        states.put(path, b);
    }

}
