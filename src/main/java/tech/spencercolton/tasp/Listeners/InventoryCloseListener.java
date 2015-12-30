package tech.spencercolton.tasp.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import tech.spencercolton.tasp.TASP;

/**
 * @author Spencer Colton
 */
public class InventoryCloseListener implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void onEvent(InventoryCloseEvent e) {
        Inventory i = e.getInventory();
        if(TASP.getOpenImmutableInventories().contains(i)) {
            i.clear();
            TASP.getOpenImmutableInventories().remove(i);
        }
    }

}
