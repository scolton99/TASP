package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.*;
import static org.bukkit.Bukkit.*;
import static tech.spencercolton.tasp.Commands.Command.*;
import static tech.spencercolton.tasp.Entity.Person.get;
import static tech.spencercolton.tasp.Configuration.Config.*;

/**
 * @author Spencer Colton
 */
public class InfoCmd extends TASPCommand {

    @Getter
    private final String syntax = "/info <player>";

    @Getter
    private static final String name = "info";

    @Getter
    private final String permission = "tasp.info";
    @Getter
    private final String consoleSyntax = "/info <player>";

    private final List<String> incls = getListString("info-includes");
    private final Map<String, String> strs = new HashMap<>();

    @Override
    public CommandResponse execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sendGenericSyntaxError(sender, this);
            return CommandResponse.SYNTAX;
        }

        Player p = getPlayer(args[0]);
        if (p == null) {
            sendPlayerMessage(sender, args[0]);
            return CommandResponse.PLAYER;
        }

        Person pers = get(p);
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

        String msg = c1() + "====" + c2() + p.getName() + c1() + "====";
        String ms1 = c1() + " * Display name: " + c2() + p.getDisplayName();
        String ms2 = c1() + " * Health: " + c2() + Double.toString(health);
        String ms3 = c1() + " * Food: " + c2() + Integer.toString(food) + c1() + " (Saturation: " + c2() + Float.toString(saturation) + c1() +
                ", Exhaustion: " + c2() + df.format(exhaustion) + c1() + ")";
        String ms4 = c1() + " * XP: " + c2() + Integer.toString(xp) + c1() + " (Level " + c2() +
                Integer.toString(level) + c1() + ")";
        String ms5 = c1() + " * Location: " + c2() + pos.getBlockX() + " " + pos.getBlockY() + " " + pos.getBlockZ() + " " + c1() + "in " + c2() + pos.getWorld().getName();
        String ms6 = c1() + " * Gamemode: " + c2() + gm.toString().toLowerCase();
        String ms7 = c1() + " * God: " + (god ? c3() : c4()) + Boolean.toString(god);
        String ms8 = c1() + " * Stalker: " + (stalker ? c3() : c4()) + Boolean.toString(stalker);
        String ms9 = c1() + " * AFK: " + (afk ? c3() : c4()) + Boolean.toString(afk);
        String ms10 = c1() + " * Muted: " + (muted ? c3() : c4()) + Boolean.toString(muted);
        String ms12 = c1() + " * Flying: " + (flying ? c3() : c4()) + Boolean.toString(flying);
        String ms13 = c1() + " * FOM: " + (fom ? c3() : c4()) + Boolean.toString(fom);
        String ms14 = c1() + " * Buddha: " + (buddha ? c3() : c4()) + Boolean.toString(buddha);
        String ms11 = c1() + " * IP: " + c2() + ip;

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

        if (!this.containsSupportedOptions()) {
            sender.sendMessage(err() + "This command has been disabled.");
            return CommandResponse.FAILURE;
        }

        sender.sendMessage(msg);

        incls.stream().forEach(s -> {
            String g = strs.get(s);
            if (g != null) {
                sender.sendMessage(g);
            }
        });

        return CommandResponse.SUCCESS;
    }

    private boolean containsSupportedOptions() {
        for (int i = 0; i < incls.size(); i++) {
            incls.set(i, incls.get(i).toUpperCase());
        }
        for (String s : strs.keySet()) {
            if (incls.contains(s))
                return true;
        }
        return false;
    }

    @Override
    public List<String> getAliases() {
        return singletonList("whois");
    }

}
