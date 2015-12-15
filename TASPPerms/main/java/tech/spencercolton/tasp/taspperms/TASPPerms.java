package tech.spencercolton.tasp.taspperms;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import tech.spencercolton.tasp.TASP;

public class TASPPerms extends JavaPlugin {

    public static TASP t;

    @Override
    public void onEnable() {
        Plugin p = getServer().getPluginManager().getPlugin("TASP");

        if(p == null) {
            getLogger().severe("TASP plugin not loaded.");
            getServer().getPluginManager().disablePlugin(this);
        }

        if(p instanceof TASP) {
            t = (TASP)p;
        } else {
            getLogger().severe("A plugin called \"TASP\" seems to be loaded, but it is not the TASP I want.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {

    }

}
