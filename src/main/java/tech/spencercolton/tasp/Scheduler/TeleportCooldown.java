package tech.spencercolton.tasp.Scheduler;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import tech.spencercolton.tasp.Util.Config;

import java.util.HashMap;

public class TeleportCooldown implements Listener {

    private static final int limit = Config.teleportCooldownInt() * 1000;

    private static final HashMap<Player, Long> times = new HashMap<>();

    public static void setTime(Player p) {
        times.put(p, System.currentTimeMillis());
    }

    public static boolean teleportAvailable(Player p) {
        return !Config.teleportCooldown() || times.get(p) == null || ((System.currentTimeMillis() - times.get(p)) > limit);
    }

    public static long getTimeToTeleport(Player p) {
        if (!Config.teleportCooldown())
            return 0;

        if (times.get(p) == null)
            return 0;

        if ((System.currentTimeMillis() - times.get(p)) > limit)
            return 0;

        return limit - (System.currentTimeMillis() - times.get(p));
    }

}
