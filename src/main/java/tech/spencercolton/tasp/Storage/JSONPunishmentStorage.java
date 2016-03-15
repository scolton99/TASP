package tech.spencercolton.tasp.Storage;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tech.spencercolton.tasp.TASP;
import tech.spencercolton.tasp.Util.Punishment;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Spencer Colton
 */
public class JSONPunishmentStorage implements PunishmentStorageProvider {

    private static JSONObject punishments;

    @SuppressWarnings("unchecked")
    public JSONPunishmentStorage() {
        File f = new File(TASP.dataFolder().getAbsolutePath() + File.separator + "mails.json");
        if (!f.exists()) {
            punishments = new JSONObject();
            punishments.put("punishments", new ArrayList<>());
            writePunishments();
        } else {
            try {
                FileReader a = new FileReader(f);
                punishments = (JSONObject) new JSONParser().parse(a);
            } catch (IOException | ParseException e) {
                Bukkit.getLogger().severe("Couldn't load punishments.");
                e.printStackTrace();
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean storePunishment(CommandSender punisher, Player punished, Punishment p, int punishNo) {
        List m = (ArrayList) punishments.get("punishments");

        m.add(p.toMap(punishNo, punisher, punished));
        punishments.put("punishments", m);
        return writePunishments();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int previousInfractions(UUID u, String name) {
        List m = (ArrayList) punishments.get("punishments");

        int punish = 0;
        for (Map<String, String> a : (List<Map<String, String>>) m) {
            if (!UUID.fromString(a.get("punished")).equals(u))
                continue;

            if (!a.get("reason").equals(name))
                continue;

            punish++;
        }

        return punish;
    }

    private boolean writePunishments() {
        try {
            FileWriter f = new FileWriter(new File(TASP.dataFolder().getAbsolutePath() + File.separator + "punishments.json"));
            f.write(punishments.toString());
            f.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getLogger().severe("Couldn't write punishments.");
            return false;
        }
    }

}
