package tech.spencercolton.tasp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import tech.spencercolton.tasp.Commands.Command;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Listeners.LoginListener;
import tech.spencercolton.tasp.Listeners.LogoutListener;
import tech.spencercolton.tasp.Listeners.PlayerDamageListener;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.PlayerData;

import java.io.File;

/**
 * Main class for the TASP plugin.
 * <p>
 *     Performs setup for various pieces
 *     (generating data-folder, loading commands and {@link java.util.EventListener}s, etc.).
 *     Also contains code for dealing with server reloads (i.e., loading the plugin when there
 *     are already players online).
 * </p>
 * <p>
 *     In the future, this class will be the linking point for all
 *     other plugins made to work with TASP.  They will not work
 *     if this plugin is not present on the server.
 * </p>
 *
 * @author Spencer Colton
 * @version 0.0.1-003
 * @since 0.0.1-001
 */
public class TASP extends JavaPlugin {

    /**
     * A {@link java.io.File} object representing this plugin's data folder.
     * <p>
     *     Generated at runtime by the {@link #onEnable() onEnable} method so that
     *     the variable becomes static and is accessable via static reference by
     *     other components of the plugin.
     * </p>
     */
    private static File dataFolder;

    /**
     *
     */
    public static Plugin TASPPerms_link;

    /**
     * Main method called by the server to enable the plugin.
     * Currently performs the following duties:
     * <ul>
     *     <li>Sets the value of the {@link #dataFolder dataFolder} variable.</li>
     *     <li>Registers the commands to be used by the plugin.</li>
     *     <li>Registers listeners with the server.</li>
     *     <li>Iterates through players currently online and generates {@link Person Person} objects for them.</li>
     *     <li>Creates the "players" directory under the plugin's data folder.</li>
     *     <li>Writes the default configuration file if none exists already.</li>
     * </ul>
     */
    @Override
    public void onEnable() {
        getLogger().info("Loading TASP plugin...");
        dataFolder = this.getDataFolder();
        initCommands();
        initListeners();
        for(Player p: getServer().getOnlinePlayers()) {
            new Person(p);
        }
        File f = new File(dataFolder().getAbsolutePath() + "\\players\\");
        f.mkdirs();

        saveDefaultConfig();

        Config.loadConfig(this.getConfig());

        loadInteractions();
    }

    /**
     * Method called by the server to disable the plugin.
     * <p>
     *     Currently has no duty except to save the server's configuration.
     * </p>
     */
    @Override
    public void onDisable() {
    }

    /**
     * Registers commands found in the {@code plugin.yml} with their executors.
     * <p>
     *     All commands are executed by the {@link tech.spencercolton.tasp.Commands.Command} class.
     * </p>
     *
     * @see Command
     */
    private void initCommands() {
        Command c = new Command();
        this.getCommand("setspeed").setExecutor(c);
        this.getCommand("killall").setExecutor(c);
        this.getCommand("fly").setExecutor((c));
        this.getCommand("home").setExecutor(c);
        this.getCommand("sethome").setExecutor(c);
        this.getCommand("god").setExecutor(c);
        this.getCommand("setspawn").setExecutor(c);
        this.getCommand("gamemode").setExecutor(c);
    }

    /**
     * Registers events found in the {@link tech.spencercolton.tasp.Listeners} package with the server.
     * Currently listens on the following events:
     * <ul>
     *     <li>{@link org.bukkit.event.player.PlayerJoinEvent}</li>
     *     <li>{@link org.bukkit.event.player.PlayerQuitEvent}</li>
     * </ul>
     */
    private void initListeners() {
        getServer().getPluginManager().registerEvents(new LoginListener(), this);
        getServer().getPluginManager().registerEvents(new LogoutListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
    }

    /**
     * Fetches the cached copy of the plugin's data directory.
     * <p>
     *     This value should be refreshed when the plugin is reloaded, but should (almost) never change.
     * </p>
     * @return A {@link File} object referencing the location of the plugin's data directory.
     */
    public static File dataFolder() {
        return dataFolder;
    }

    /**
     * Iterates through all players on the server and reloads their data from file.
     * <p>
     *     Internally makes a call to the {@link #refreshPlayer(Player)} method.
     * </p>
     */
    @SuppressWarnings("unused")
    public static void refreshPlayers() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            refreshPlayer(p);
        }
    }

    /**
     * Reloads a player's data from file in the case that something goes awry with the ephemeral data.
     * <p>
     *     Causes the {@link PlayerData} object contained within the player to be
     *     reloaded from the JSON file containing key-value pairs about the player.
     * </p>
     * @param p The player whose data is to be reloaded.
     */
    public static void refreshPlayer(Player p) {
        Person.get(p).reloadData();
    }

    private void loadInteractions() {
        TASPPerms_link = Bukkit.getPluginManager().getPlugin("TASPPerms");

    }

}
