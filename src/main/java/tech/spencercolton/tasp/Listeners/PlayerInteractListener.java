package tech.spencercolton.tasp.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.TASP;

import java.util.List;

/**
 * @author Spencer Colton
 */
public class PlayerInteractListener implements Listener {

    @SuppressWarnings("unused")
    @EventHandler
    public void onEvent(PlayerInteractEvent e) {
        if (e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.LEFT_CLICK_BLOCK)
            return;

        if (!TASP.powertoolsEnabled())
            return;

        if (Person.get(e.getPlayer()).getPowertool(e.getPlayer().getItemInHand().getType()) != null) {
            e.setCancelled(true);
            List<String> cmdLns = Person.get(e.getPlayer()).getPowertool(e.getPlayer().getItemInHand().getType());
            for (String s : cmdLns) {
                Bukkit.dispatchCommand(e.getPlayer(), s);
            }
        }
    }

}
