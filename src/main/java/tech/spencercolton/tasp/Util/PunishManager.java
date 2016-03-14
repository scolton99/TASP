package tech.spencercolton.tasp.Util;

import tech.spencercolton.tasp.Configuration.Config;
import tech.spencercolton.tasp.Enums.PunishType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Spencer Colton
 */
public class PunishManager {

    Map<String, Punishment> presets = new HashMap<>();

    public static void init() {
        Config.getMap("punishments");
    }

    public static Punishment getDefault(String s) {

        return null;
    }

    public static Punishment genPunishment(PunishType pt, int duration) {
        return null;
    }

}
