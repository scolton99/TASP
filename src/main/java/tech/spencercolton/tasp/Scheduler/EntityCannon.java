package tech.spencercolton.tasp.Scheduler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityCannon extends BukkitRunnable {

    private final Entity e;
    private static final long TICKS_IN_SECOND = 20;

    public EntityCannon(Entity e) {
        this.e = e;
        this.runTaskLater(Bukkit.getPluginManager().getPlugin("TASP"), 2L * TICKS_IN_SECOND);
    }

    @Override
    public void run() {
        Location l = this.e.getLocation();
        this.e.remove();

        l.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), 4.0F, false, false);
    }

}
