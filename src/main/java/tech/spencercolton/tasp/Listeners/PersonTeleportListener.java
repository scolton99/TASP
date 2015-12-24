package tech.spencercolton.tasp.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tech.spencercolton.tasp.Commands.TeleportCmd;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Events.PersonTeleportAllHereEvent;
import tech.spencercolton.tasp.Events.PersonTeleportEvent;
import tech.spencercolton.tasp.Scheduler.TeleportCooldown;
import tech.spencercolton.tasp.Util.Config;

/**
 * @author Spencer Colton
 */
public class PersonTeleportListener implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void onEvent(PersonTeleportEvent e) {
        if(!TeleportCmd.isEnabled()) {
            if(e.isRequest())
                e.getTeleporter().getPlayer().sendMessage(Config.err() + "Teleporting has been disabled for this server.");
            else
                if(e.isHere())
                    e.getTo().getPlayer().sendMessage(Config.err() + "Teleporting has been disabled for this server.");
                else
                    e.getTeleporter().getPlayer().sendMessage(Config.err() + "Teleporting has been disabled for this server.");
            return;
        }
        if(e.isRequest()) {
            Person requester = e.getTeleporter();
            Person requestee = e.getTo();

            requestee.setLastTeleportRequest(requester, e.isHere());
            sendTeleportRequestMessage(requester.getPlayer(), requestee.getPlayer(), e.isHere());
        } else {
            if (!TeleportCooldown.teleportAvailable(e.getTeleporter().getPlayer())) {
                if (e.isHere())
                    sendTeleportCooldownMessage(e.getTeleporter().getPlayer(), TeleportCooldown.getTimeToTeleport(e.getTeleporter().getPlayer()), e.getTo().getPlayer());
                else
                    sendTeleportCooldownMessage(e.getTeleporter().getPlayer(), TeleportCooldown.getTimeToTeleport(e.getTeleporter().getPlayer()));
                return;
            }

            if (Config.teleportCooldown())
                TeleportCooldown.setTime(e.getTeleporter().getPlayer());
            e.getTeleporter().getPlayer().teleport(e.getTo().getPlayer());
        }
    }

    private void sendTeleportRequestMessage(Player requester, Player requestee) {
        requester.sendMessage(Config.c3() + "Asking " + requestee.getDisplayName() + " if it's okay to teleport you to him or her..." + (Config.isTeleportRequestLimited() ? "expires in " + Config.teleportRequestLimit() / 1000 + " seconds" : ""));
        requestee.sendMessage(Config.c4() + "Player " + requester.getDisplayName() + " would like to teleport to you.  Type /tpa to allow, or /tpd to deny (or just ignore this message.) " + (Config.isTeleportRequestLimited() ? "Expires in "+ Config.teleportRequestLimit() / 1000 + " seconds." : "" ));
    }

    private void sendTeleportRequestMessage(Player requester, Player requestee, boolean here) {
        if(!here) {
            sendTeleportRequestMessage(requester, requestee);
            return;
        }
        requester.sendMessage(Config.c3() + "Asking " + requestee.getDisplayName() + " if it's okay to teleport him or her to you..." + (Config.isTeleportRequestLimited() ? "expires in " + Config.teleportRequestLimit() / 1000 + " seconds" : ""));
        requestee.sendMessage(Config.c4() + "Player " + requester.getDisplayName() + " would like you to teleport to him or her.  Type /tpa to allow, or /tpd to deny (or just ignore this message.) " + (Config.isTeleportRequestLimited() ? "Expires in "+ Config.teleportRequestLimit() / 1000 + " seconds." : "" ));
    }

    private void sendTeleportRequestMessage(Player requester, Player requestee, boolean here, boolean all) {
        if(all)
            here = true;
        if(!here) {
            sendTeleportRequestMessage(requester, requestee);
            return;
        }
        if(!all)
            requester.sendMessage(Config.c3() + "Asking " + requestee.getDisplayName() + " if it's okay to teleport him or her to you..." + (Config.isTeleportRequestLimited() ? "expires in " + Config.teleportRequestLimit() / 1000 + " seconds" : ""));
        requestee.sendMessage(Config.c4() + "Player " + requester.getDisplayName() + " would like you to teleport to him or her.  Type /tpa to allow, or /tpd to deny (or just ignore this message.) " + (Config.isTeleportRequestLimited() ? "Expires in "+ Config.teleportRequestLimit() / 1000 + " seconds." : "" ));
    }

    private void sendTeleportCooldownMessage(Player p, long time) {
        p.sendMessage(Config.err() + "You can't teleport again for another " + (int)Math.ceil(time / 1000.0D) + " seconds.");
    }

    private void sendTeleportCooldownMessage(Player p, long time, Player other) {
        other.sendMessage(Config.err() + p.getDisplayName() + " cannot teleport again for another " + (int)Math.ceil(time / 1000.0D) + " seconds.  Try again later.");
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onEvent(PersonTeleportAllHereEvent e) {
        if(!TeleportCmd.isEnabled()) {
            e.getTo().getPlayer().sendMessage(Config.err() + "Teleporting has been disabled for this server.");
            return;
        }
        if(e.isRequest()) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(p.equals(e.getTo().getPlayer()))
                    continue;
                sendTeleportRequestMessage(e.getTo().getPlayer(), p, true, true);
                Person.get(p).setLastTeleportRequest(e.getTo(), true);
            }
        } else {
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(p.equals(e.getTo().getPlayer()))
                    continue;
                if (TeleportCooldown.teleportAvailable(p)) {
                    if (Config.teleportCooldown())
                        TeleportCooldown.setTime(p);
                    p.getPlayer().teleport(e.getTo().getPlayer());
                }
            }
        }
    }

}
