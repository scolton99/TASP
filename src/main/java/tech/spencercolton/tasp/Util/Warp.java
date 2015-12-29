package tech.spencercolton.tasp.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.spencercolton.tasp.TASP;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author Spencer Colton
 */
public class Warp {

    private static JSONObject warps;

    @SuppressWarnings("unchecked")
    public static void initWarps() {
        File f = new File(TASP.dataFolder().getAbsolutePath() + File.separator + "warps.json");
        if (!f.exists()) {
            warps = new JSONObject();
            warps.put("warps", new ArrayList<Map<String,String>>());
            writeWarps();
        } else {
            try {
                FileReader a = new FileReader(f);
                warps = (JSONObject) new JSONParser().parse(a);
            } catch (IOException | ParseException e) {
                warps = new JSONObject();
                warps.put("warps", new ArrayList<>());
                writeWarps();
                Bukkit.getLogger().warning("Couldn't load mails.");
                e.printStackTrace();
            }
        }
    }

    public static void writeWarps() {
        try {
            FileWriter f = new FileWriter(new File(TASP.dataFolder().getAbsolutePath() + File.separator + "warps.json"));
            f.write(warps.toString());
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getLogger().warning(Config.err() + "Couldn't write warps.");
        }
    }

    @SuppressWarnings("unchecked")
    public static Location getWarp(String s) {
        List<Map<String, String>> wps = (List)warps.get("warps");
        for(Map<String, String> mp : wps) {
            if(mp.get("name").equalsIgnoreCase(s)) {
                Double x = Double.parseDouble(mp.get("x"));
                Double y = Double.parseDouble(mp.get("y"));
                Double z = Double.parseDouble(mp.get("z"));
                Float pitch = Float.parseFloat(mp.get("pitch"));
                Float yaw = Float.parseFloat(mp.get("yaw"));
                World w = Bukkit.getWorld(UUID.fromString(mp.get("world")));
                return new Location(w, x, y, z, yaw, pitch);
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static boolean setWarp(Location l, String name) {
        List<Map<String,String>> wps = (List)warps.get("warps");

        for(Map<String,String> wp : wps)
            if(wp.get("name").equalsIgnoreCase(name))
                return false;

        Map<String,String> warp = new HashMap<>();
        warp.put("name", name);
        warp.put("world", l.getWorld().getUID().toString());
        warp.put("x", Double.toString(l.getX()));
        warp.put("y", Double.toString(l.getY()));
        warp.put("z", Double.toString(l.getZ()));
        warp.put("pitch", Float.toString(l.getPitch()));
        warp.put("yaw", Float.toString(l.getYaw()));

        wps.add(warp);
        warps.put("warps", wps);
        writeWarps();
        return true;
    }

    @SuppressWarnings("unchecked")
    public static void deleteWarp(String s) {
        List<Map<String, String>> wps = (List)warps.get("warps");
        for(Map<String, String> mp : wps) {
            if(mp.get("name").equalsIgnoreCase(s)) {
                wps.remove(mp);
                warps.put("warps", wps);
                writeWarps();
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Map<String,String>> getAllWarps() {
        return (List<Map<String,String>>)warps.get("warps");
    }

}
