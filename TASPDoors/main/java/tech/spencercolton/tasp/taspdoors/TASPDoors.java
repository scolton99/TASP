package tech.spencercolton.tasp.taspdoors;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class TASPDoors extends JavaPlugin {

    public void onEnable() {
        Plugin TASP = getServer().getPluginManager().getPlugin("TASP");

        if(TASP == null) {
            getLogger().severe(ChatColor.RED + "Unable to load TASP Doors plugin because the TASP main plugin is missing!");
            getLogger().severe(ChatColor.RED + "Install the TASP main plugin before trying again.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public void onDisable() {
        getLogger().info("Disabling TASP Doors plugin...");
    }

}
