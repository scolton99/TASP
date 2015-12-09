package tech.spencercolton.tasp;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tech.spencercolton.tasp.Commands.Command;
import tech.spencercolton.tasp.Entity.Person;

import java.io.File;

public class TASP extends JavaPlugin {

    private static File dataFolder;

    @Override
    public void onEnable() {
        getLogger().info("Loading TASP plugin...");
        dataFolder = this.getDataFolder();
        initCommands();
        for(Player p: getServer().getOnlinePlayers()) {
            new Person(p);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling TASP plugin...");
    }

    private void initCommands() {
        Command.populateList();
    }

    public static File dataFolder() {
        return dataFolder;
    }

}
