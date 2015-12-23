package tech.spencercolton.tasp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import tech.spencercolton.tasp.Commands.Command;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Listeners.*;
import tech.spencercolton.tasp.Scheduler.AFKTimer;
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
 * @version 0.0.3
 * @since 0.0.1-001
 */
public class TASP extends JavaPlugin {

    /**
     * A {@link File} object representing this plugin's data folder.
     * <p>
     *     Generated at runtime by the {@link #onEnable() onEnable} method so that
     *     the variable becomes static and is accessible via static reference by
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
        this.getLogger().info("Loading TASP plugin...");

        this.saveDefaultConfig();
        this.reloadConfig();

        Config.loadConfig(this.getConfig());
        dataFolder = this.getDataFolder();
        this.initCommands();
        this.initListeners();
        for(Player p: this.getServer().getOnlinePlayers()) {
            new Person(p);
            loadPerson(Person.get(p));
        }
        File f = new File(dataFolder().getAbsolutePath() + File.separator + "players" + File.separator);
        if(f.mkdirs()) {
            Bukkit.getLogger().info("Directories were created for TASP.");
        }

        this.loadInteractions();
    }

    /**
     * Method called by the server to disable the plugin.
     * <p>
     *     Currently has no duty except to save the server's configuration.
     * </p>
     */
    @Override
    public void onDisable() {
        Person.getPeople().stream().forEach(Person::writeData);
    }

    /**
     * Registers commands found in the {@code plugin.yml} with their executors.
     * <p>
     *     All commands are executed by the {@link Command} class.
     * </p>
     *
     * @see Command
     */
    private void initCommands() {
        Command c = new Command();
        this.getCommand("setspeed").setExecutor(c);
        this.getCommand("killall").setExecutor(c);
        this.getCommand("fly").setExecutor(c);
        this.getCommand("home").setExecutor(c);
        this.getCommand("sethome").setExecutor(c);
        this.getCommand("god").setExecutor(c);
        this.getCommand("setspawn").setExecutor(c);
        this.getCommand("gamemode").setExecutor(c);
        this.getCommand("time").setExecutor(c);
        this.getCommand("top").setExecutor(c);
        this.getCommand("afk").setExecutor(c);
        this.getCommand("xyz").setExecutor(c);
        this.getCommand("mute").setExecutor(c);
        this.getCommand("me").setExecutor(c);
        this.getCommand("msg").setExecutor(c);
        this.getCommand("reply").setExecutor(c);
        this.getCommand("block").setExecutor(c);
        this.getCommand("unblock").setExecutor(c);
        this.getCommand("broadcast").setExecutor(c);
        this.getCommand("powertool").setExecutor(c);
        this.getCommand("powertooltoggle").setExecutor(c);
        this.getCommand("stalker").setExecutor(c);
        this.getCommand("info").setExecutor(c);
        this.getCommand("tasp").setExecutor(c);
        this.getCommand("srvinfo").setExecutor(c);
        this.getCommand("stahp").setExecutor(c);
        this.getCommand("shock").setExecutor(c);
        this.getCommand("burn").setExecutor(c);
        this.getCommand("feed").setExecutor(c);
        this.getCommand("starve").setExecutor(c);
        this.getCommand("heal").setExecutor(c);
        this.getCommand("hurt").setExecutor(c);
        this.getCommand("fom").setExecutor(c);
        this.getCommand("potion").setExecutor(c);
        this.getCommand("antidote").setExecutor(c);
        this.getCommand("buddha").setExecutor(c);
        this.getCommand("weather").setExecutor(c);
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
        this.getServer().getPluginManager().registerEvents(new LoginListener(), this);
        this.getServer().getPluginManager().registerEvents(new LogoutListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new MoveListener(), this);
        this.getServer().getPluginManager().registerEvents(new ChatListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        this.getServer().getPluginManager().registerEvents(new PersonSendMessageListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityTargetListener(), this);
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
        Bukkit.getOnlinePlayers().forEach(TASP::refreshPlayer);
    }

    /**
     * Reloads a player's data from file in the case that something goes awry with the ephemeral data.
     * <p>
     *     Causes the {@link PlayerData} object contained within the player to be
     *     reloaded from the JSON file containing key-value pairs about the player.
     * </p>
     * @param p The player whose data is to be reloaded.
     */
    private static void refreshPlayer(Player p) {
        Person.get(p).reloadData();
    }

    private void loadInteractions() {
        TASPPerms_link = Bukkit.getPluginManager().getPlugin("TASPPerms");

    }

    public static void loadPerson(Person a) {
        new AFKTimer(a);
    }

    public static void unloadPerson(Person a) {
        AFKTimer.timers.get(a).cancel();
        AFKTimer.timers.remove(a);
        Person.unloadPerson(a);
    }

    public static void reload() {
        Bukkit.getPluginManager().getPlugin("TASP").onDisable();
        Bukkit.getPluginManager().getPlugin("TASP").onEnable();
    }

}
