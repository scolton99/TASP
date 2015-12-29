package tech.spencercolton.tasp.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Enums.TeleportType;
import tech.spencercolton.tasp.Events.PersonTeleportAllHereEvent;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;
import tech.spencercolton.tasp.Scheduler.TeleportCooldown;
import tech.spencercolton.tasp.TASP;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.Message;

/**
 * @author Spencer Colton
 */
public class PersonTeleportListener implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void onEvent(PersonTeleportEvent e) {
        if (e.getType() == TeleportType.PERSON) {
            if (!TASP.isTeleportEnabled()) {
                Message.Teleport.Error.sendTeleportDisabledMessage(e.getRequester().getPlayer());
                return;
            }
            if (e.isRequest()) {
                Person requester = e.getRequester();
                Person requestee = e.getRequestee();

                requestee.setLastTeleportRequest(requester, e.isHere());
                Message.Teleport.sendTeleportRequestMessage(requester.getPlayer(), requestee.getPlayer(), e.isHere());
            } else {
                if (!TeleportCooldown.teleportAvailable(e.getRequester().getPlayer())) {
                    if (e.isHere())
                        Message.Teleport.Error.sendTeleportCooldownMessage(e.getRequester().getPlayer(), TeleportCooldown.getTimeToTeleport(e.getRequester().getPlayer()), e.getRequestee().getPlayer());
                    else
                        Message.Teleport.Error.sendTeleportCooldownMessage(e.getRequester().getPlayer(), TeleportCooldown.getTimeToTeleport(e.getRequester().getPlayer()));
                    return;
                }
                if (Config.teleportCooldown())
                    TeleportCooldown.setTime(e.getRequester().getPlayer());
                if (e.isHere())
                    e.getRequestee().getPlayer().teleport(e.getRequester().getPlayer());
                else
                    e.getRequester().getPlayer().teleport(e.getRequestee().getPlayer());
            }
        } else if (e.getType() == TeleportType.LOCATION) {
            if (!TASP.isTeleportEnabled()) {
                Message.Teleport.Error.sendTeleportDisabledMessage(e.getRequester().getPlayer());
                return;
            }

            if (!TeleportCooldown.teleportAvailable(e.getRequester().getPlayer())) {
                Message.Teleport.Error.sendTeleportCooldownMessage(e.getRequester().getPlayer(), TeleportCooldown.getTimeToTeleport(e.getRequester().getPlayer()));
                return;
            }

            e.getRequester().getPlayer().teleport(e.getLocation());
        }
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onEvent(PersonTeleportAllHereEvent e) {
        if (!TASP.isTeleportEnabled()) {
            Message.Teleport.Error.sendTeleportDisabledMessage(e.getRequester().getPlayer());
            return;
        }
        if (e.isRequest()) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.equals(e.getRequester().getPlayer()))
                    continue;
                Message.Teleport.sendTeleportRequestMessage(e.getRequester().getPlayer(), p, true, true);
                Person.get(p).setLastTeleportRequest(e.getRequester(), true);
            }
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.equals(e.getRequester().getPlayer()))
                    continue;
                if (TeleportCooldown.teleportAvailable(p)) {
                    if (Config.teleportCooldown())
                        TeleportCooldown.setTime(p);
                    p.getPlayer().teleport(e.getRequester().getPlayer());
                }
            }
        }
    }

}
