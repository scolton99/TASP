package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.Config;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Spencer Colton
 */
public class InfoCmd extends TASPCommand {

    @Getter
    private static final String syntax = "/info <player>";

    public static final String name = "info";

    @Getter
    private static final String permission = "tasp.info";
    @Getter
    private static final String consoleSyntax = "/info <player>";

    private final List<String> incls = Config.getListString("info-includes");
    private final Map<String, String> strs = new HashMap<>();

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(args.length != 1) {
            Command.sendGenericSyntaxError(sender, this);
            return;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if(p == null) {
            Command.sendPlayerMessage(sender, args[0]);
            return;
        }

        Person pers = Person.get(p);
        assert pers != null;

        boolean god = pers.isGod();
        GameMode gm = p.getGameMode();
        boolean stalker = pers.isStalker();
        boolean afk = pers.isAfk();
        Location pos = p.getLocation();
        double health = p.getHealth();
        int food = p.getFoodLevel();
        float saturation = p.getSaturation();
        float exhaustion = p.getExhaustion();
        DecimalFormat df = new DecimalFormat("0.00");
        int xp = p.getTotalExperience();
        int level = p.getLevel();
        boolean muted = pers.isMuted();
        boolean flying = p.isFlying();
        boolean fom = pers.isFOM();
        boolean buddha = pers.isBuddha();
        String ip = p.getAddress().getHostString();

        String msg = Config.c1() + "====" + Config.c2() + p.getName() + Config.c1() + "====";
        String ms1 = Config.c1() + " * Display name: " + Config.c2() + p.getDisplayName();
        String ms2 = Config.c1() + " * Health: " + Config.c2() + Double.toString(health);
        String ms3 = Config.c1() + " * Food: " + Config.c2() + Integer.toString(food) + Config.c1() + " (Saturation: " + Config.c2() + Float.toString(saturation) + Config.c1() +
                ", Exhaustion: "+ Config.c2() + df.format(exhaustion) + Config.c1() + ")";
        String ms4 = Config.c1() + " * XP: " + Config.c2() + Integer.toString(xp) + Config.c1() + " (Level " + Config.c2() +
                Integer.toString(level) + Config.c1() + ")";
        String ms5 = Config.c1() + " * Location: " + Config.c2() + pos.getBlockX() + " " + pos.getBlockY() + " " + pos.getBlockZ() + " " + Config.c1() + "in " + Config.c2() + pos.getWorld().getName();
        String ms6 = Config.c1() + " * Gamemode: " + Config.c2() + gm.toString().toLowerCase();
        String ms7 = Config.c1() + " * God: " + (god ? Config.c3() : Config.c4()) + Boolean.toString(god);
        String ms8 = Config.c1() + " * Stalker: " + (stalker ? Config.c3() : Config.c4()) + Boolean.toString(stalker);
        String ms9 = Config.c1() + " * AFK: " + (afk ? Config.c3() : Config.c4()) + Boolean.toString(afk);
        String ms10 = Config.c1() + " * Muted: " + (muted ? Config.c3() : Config.c4()) + Boolean.toString(muted);
        String ms12 = Config.c1() + " * Flying: " + (flying ? Config.c3() : Config.c4()) + Boolean.toString(flying);
        String ms13 = Config.c1() + " * FOM: " + (fom ? Config.c3() : Config.c4()) + Boolean.toString(fom);
        String ms14 = Config.c1() + " * Buddha: " + (buddha ? Config.c3() : Config.c4()) + Boolean.toString(buddha);
        String ms11 = Config.c1() + " * IP: " + Config.c2() + ip;

        strs.put("DISPLAY_NAME", ms1);
        strs.put("HEALTH", ms2);
        strs.put("FOOD", ms3);
        strs.put("XP", ms4);
        strs.put("LOCATION", ms5);
        strs.put("GAMEMODE", ms6);
        strs.put("GOD", ms7);
        strs.put("STALKER", ms8);
        strs.put("AFK", ms9);
        strs.put("MUTED", ms10);
        strs.put("IP", ms11);
        strs.put("FLYING", ms12);
        strs.put("FOM", ms13);
        strs.put("BUDDHA", ms14);

        if(!this.containsSupportedOptions()) {
            sender.sendMessage(Config.err() + "This command has been disabled.");
            return;
        }

        sender.sendMessage(msg);

        incls.stream().forEach(s -> {
            String g = strs.get(s);
            if(g != null) {
                sender.sendMessage(g);
            }
        });
    }

    private boolean containsSupportedOptions() {
        for(int i = 0; i < incls.size(); i++) {
            incls.set(i, incls.get(i).toUpperCase());
        }
        for(String s : strs.keySet()) {
            if(incls.contains(s))
                return true;
        }
        return false;
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("whois");
    }

}
