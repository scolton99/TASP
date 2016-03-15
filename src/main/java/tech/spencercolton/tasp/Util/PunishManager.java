package tech.spencercolton.tasp.Util;

import lombok.Getter;
import tech.spencercolton.tasp.Configuration.Config;
import tech.spencercolton.tasp.Enums.PunishType;
import tech.spencercolton.tasp.Storage.DatabasePunishmentStorage;
import tech.spencercolton.tasp.Storage.JSONPunishmentStorage;
import tech.spencercolton.tasp.Storage.PunishmentStorageProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Spencer Colton
 */
public class PunishManager {

    private static Map<String, Punishment> presets = new HashMap<>();

    @Getter
    private static PunishmentStorageProvider psp;

    @SuppressWarnings("unchecked")
    public static void init() {
        Map<String, List<String>> configs = Config.getMap("punishments");

        for (String s : configs.keySet()) {
            Punishment p = new Punishment(s);

            List<String> x = configs.get(s);
            assert x != null;

            for (String g : x) {
                p.addEffectFromConfig(g);
            }

            presets.put(s, p);
        }

        psp = Config.configDatabase() ? new DatabasePunishmentStorage() : new JSONPunishmentStorage();
    }

    public static Punishment getDefault(String s) {
        return presets.get(s);
    }

    public static Punishment genPunishment(PunishType pt, int duration, String reason) {
        Punishment p = new Punishment();
        p.addEffect(pt, duration, reason);
        return p;
    }

}
