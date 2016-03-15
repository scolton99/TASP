package tech.spencercolton.tasp.Scheduler;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tech.spencercolton.tasp.Util.PunishManager;
import tech.spencercolton.tasp.Util.Punishment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class SyncPunishment extends BukkitRunnable {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static List<SyncPunishment> tasks = new ArrayList<>();

    private final CommandSender punisher;
    private final Player punished;
    private final Punishment p;
    private final int punishNo;

    public SyncPunishment(CommandSender punisher, Player punished, Punishment p, int punishNo) {
        this.punished = punished;
        this.punisher = punisher;
        this.p = p;
        this.punishNo = punishNo;

        tasks.add(this);
        this.runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("TASP"));
    }

    @Override
    public void run() {
        PunishManager.getPsp().storePunishment(punisher, punished, p, punishNo);
        tasks.remove(this);
    }
}
