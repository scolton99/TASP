package tech.spencercolton.tasp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tech.spencercolton.tasp.Commands.Command;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Listeners.LoginListener;
import tech.spencercolton.tasp.Listeners.LogoutListener;

import java.io.File;

public class TASP extends JavaPlugin {

    private static File dataFolder;

    @Override
    public void onEnable() {
        getLogger().info("Loading TASP plugin...");
        dataFolder = this.getDataFolder();
        initCommands();
        initListeners();
        for(Player p: getServer().getOnlinePlayers()) {
            new Person(p);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling TASP plugin...");
    }

    private void initCommands() {
        this.getCommand("setspeed").setExecutor(new Command());
        this.getCommand("killall").setExecutor(new Command());
    }

    private void initListeners() {
        getServer().getPluginManager().registerEvents(new LoginListener(), this);
        getServer().getPluginManager().registerEvents(new LogoutListener(), this);
    }

    public static File dataFolder() {
        return dataFolder;
    }

    public static void refreshPlayers() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            refreshPlayer(p);
        }
    }

    public static void refreshPlayer(Player p) {
        Person.get(p).reloadData();
    }

}
