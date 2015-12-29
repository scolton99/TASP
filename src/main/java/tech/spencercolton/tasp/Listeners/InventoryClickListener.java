package tech.spencercolton.tasp.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import tech.spencercolton.tasp.TASP;

/**
 * @author Spencer Colton
 */
public class InventoryClickListener implements Listener {

    @EventHandler
    @SuppressWarnings("unused")
    public void onEvent(InventoryClickEvent e) {
        Inventory s = e.getClickedInventory();
        if(TASP.getOpenImmutableInventories().contains(s)) {
            if(s instanceof CraftingInventory) {
                CraftingInventory c = (CraftingInventory)s;

            }
            e.setCancelled(true);
        }
    }

}
