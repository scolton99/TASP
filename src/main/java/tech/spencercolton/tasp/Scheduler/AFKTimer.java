package tech.spencercolton.tasp.Scheduler;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import tech.spencercolton.tasp.Commands.AFKCmd;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Config;

import java.util.HashMap;

public class AFKTimer extends BukkitRunnable {

    public static HashMap<Person, BukkitTask> timers = new HashMap<>();

    Person p;
    int time;

    public AFKTimer(Person p, int time) {
        this.p = p;
        this.time = time;
        timers.put(p, this.runTaskLater(Bukkit.getPluginManager().getPlugin("TASP"), time));
    }

    public AFKTimer(Person p) {
        this(p, Config.AFKTime());
    }

    public void run() {
        if(!this.p.isAfk()) {
            this.p.setAfk(true);
            AFKCmd.broadcastAFKMessage(p);
        }
    }

}
