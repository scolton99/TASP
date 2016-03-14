package tech.spencercolton.tasp.Scheduler;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Configuration.Config;
import tech.spencercolton.tasp.Util.Message;

import java.util.HashMap;
import java.util.Map;

public class AFKTimer extends BukkitRunnable {

    public static final Map<Person, BukkitTask> timers = new HashMap<>();

    private final Person p;

    public AFKTimer(Person p, int time) {
        this.p = p;
        timers.put(p, this.runTaskLater(Bukkit.getPluginManager().getPlugin("TASP"), time));
    }

    public AFKTimer(Person p) {
        this(p, Config.afkTime());
    }

    @Override
    public void run() {
        if (!this.p.isAfk()) {
            this.p.setAfk(true);
            Message.AFK.broadcastAFKMessage(this.p.getPlayer());
        }
    }

}
