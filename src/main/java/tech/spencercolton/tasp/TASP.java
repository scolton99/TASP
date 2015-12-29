package tech.spencercolton.tasp;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import tech.spencercolton.tasp.Commands.Command;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Listeners.*;
import tech.spencercolton.tasp.Scheduler.AFKTimer;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.Entities;
import tech.spencercolton.tasp.Util.Mail;
import tech.spencercolton.tasp.Util.Warp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for the TASP plugin.
 * <p>
 * Performs setup for various pieces
 * (generating data-folder, loading commands and {@link java.util.EventListener}s, etc.).
 * Also contains code for dealing with server reloads (i.e., loading the plugin when there
 * are already players online).
 * </p>
 * <p>
 * In the future, this class will be the linking point for all
 * other plugins made to work with TASP.  They will not work
 * if this plugin is not present on the server.
 * </p>
 *
 * @author Spencer Colton
 * @version 0.0.3
 * @since 0.0.1-001
 */
public class TASP extends JavaPlugin {

    public static final int WORLD_HEIGHT = 256;

    private static File dataFolder;

    private static boolean powertoolsEnabled = true;

    public static CommandSender consoleLast;

    @Getter
    @Setter
    private static String helpMeRcvPrivilege;

    @Getter
    private static boolean teleportEnabled = true;

    public static boolean toggleTeleporting() {
        return teleportEnabled = !teleportEnabled;
    }

    @Getter
    public static final List<Inventory> openImmutableInventories = new ArrayList<>();

    @Override
    public void onEnable() {
        this.getLogger().info("Loading TASP plugin...");

        this.saveDefaultConfig();
        this.reloadConfig();

        Config.loadConfig(this.getConfig());
        dataFolder = this.getDataFolder();

        Mail.initMail();
        Warp.initWarps();
        this.initCommands();
        this.initListeners();
        Entities.initEntities();
        File f = new File(dataFolder().getAbsolutePath() + File.separator + "players" + File.separator);
        if (f.mkdirs()) {
            Bukkit.getLogger().info("Directories were created for TASP.");
        }

        for (Player p : this.getServer().getOnlinePlayers()) {
            new Person(p);
            loadPerson(Person.get(p));
        }
        this.loadInteractions();
    }

    @Override
    public void onDisable() {
        Person.getPeople().stream().forEach(Person::writeData);
    }

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
        this.getCommand("spawnmob").setExecutor(c);
        this.getCommand("world").setExecutor(c);
        this.getCommand("explode").setExecutor(c);
        this.getCommand("drops").setExecutor(c);
        this.getCommand("tp").setExecutor(c);
        this.getCommand("tphere").setExecutor(c);
        this.getCommand("tpall").setExecutor(c);
        this.getCommand("tpa").setExecutor(c);
        this.getCommand("tpd").setExecutor(c);
        this.getCommand("tpr").setExecutor(c);
        this.getCommand("tprhere").setExecutor(c);
        this.getCommand("tpar").setExecutor(c);
        this.getCommand("tpt").setExecutor(c);
        this.getCommand("mail").setExecutor(c);
        this.getCommand("spawn").setExecutor(c);
        this.getCommand("back").setExecutor(c);
        this.getCommand("shoot").setExecutor(c);
        this.getCommand("xp").setExecutor(c);
        this.getCommand("craft").setExecutor(c);
        this.getCommand("su").setExecutor(c);
        this.getCommand("invspy").setExecutor(c);
        this.getCommand("clear").setExecutor(c);
        this.getCommand("tploc").setExecutor(c);
        this.getCommand("nick").setExecutor(c);
        this.getCommand("jump").setExecutor(c);
        this.getCommand("recipe").setExecutor(c);
        this.getCommand("holding").setExecutor(c);
        this.getCommand("i").setExecutor(c);
        this.getCommand("disappear").setExecutor(c);
        this.getCommand("helpme").setExecutor(c);
        this.getCommand("setwarp").setExecutor(c);
        this.getCommand("warp").setExecutor(c);
    }

    private void initListeners() {
        this.getServer().getPluginManager().registerEvents(new LoginListener(), this);
        this.getServer().getPluginManager().registerEvents(new LogoutListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new MoveListener(), this);
        this.getServer().getPluginManager().registerEvents(new ChatListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        this.getServer().getPluginManager().registerEvents(new PersonSendMessageListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityTargetListener(), this);
        this.getServer().getPluginManager().registerEvents(new TASPBroadcastListener(), this);
        this.getServer().getPluginManager().registerEvents(new PersonTeleportListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerTeleportListener(), this);
        this.getServer().getPluginManager().registerEvents(new PersonHelpmeListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
    }

    public static File dataFolder() {
        return dataFolder;
    }

    private void loadInteractions() {

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
        reloadTASPConfig();
        ((TASP) Bukkit.getPluginManager().getPlugin("TASP")).loadInteractions();
    }

    public static void reloadTASPConfig() {
        Bukkit.getPluginManager().getPlugin("TASP").saveDefaultConfig();
        Bukkit.getPluginManager().getPlugin("TASP").reloadConfig();
        Config.loadConfig(Bukkit.getPluginManager().getPlugin("TASP").getConfig());
    }

    public static boolean powertoolsEnabled() {
        return powertoolsEnabled;
    }

    public static boolean togglePowertools() {
        powertoolsEnabled = !powertoolsEnabled;
        return powertoolsEnabled;
    }

}
