package tech.spencercolton.tasp.Util;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Enums.PunishType;
import tech.spencercolton.tasp.TASP;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Spencer Colton
 */
public class Punishment {

    Map<Integer, PunishEffect> effects = new HashMap<>();

    @Getter
    private final String name;

    public Punishment () { this.name = "punishment"; }

    public Punishment (String name) {
        this.name = name;
    }

    public void addEffect(PunishType type, int duration, String reason) {
        int i = nextEffect();

        this.effects.put(i, new PunishEffect(type, duration, reason));
    }

    public void addEffectFromConfig(String s) {
        try {
            String[] n = s.split(" ");

            PunishType t = PunishType.valueOf(n[0].toUpperCase());

            if(t == null)
                return;

            int i = nextEffect();

            switch (t) {
                case MUTE: {
                    if (n.length != 2)
                        return;

                    int g;
                    try {
                        g = Integer.parseInt(n[1]);
                    } catch (NumberFormatException e) {
                        return;
                    }

                    this.effects.put(i, new PunishEffect(t, g, null));
                    break;
                }
                case BAN: {
                    String reason = "";

                    for (int h = 1; h < n.length; h++) {
                        reason += n[h];
                        if ((h + 1) < n.length)
                            reason += " ";
                    }

                    this.effects.put(i, new PunishEffect(t, null, reason));
                    break;
                }
                case TEMPBAN: {
                    if (n.length < 2)
                        return;

                    int g;
                    try {
                        g = Integer.parseInt(n[1]);
                    } catch (NumberFormatException e) {
                        return;
                    }

                    String reason = "";

                    for (int h = 2; h < n.length; h++) {
                        reason += n[h];
                        if ((h + 1) < n.length)
                            reason += " ";
                    }

                    this.effects.put(i, new PunishEffect(t, g, reason));
                    break;
                }
                case KICK: {
                    String reason = "";

                    for (int h = 1; h < n.length; h++) {
                        reason += n[h];
                        if ((h + 1) < n.length)
                            reason += " ";
                    }

                    this.effects.put(i, new PunishEffect(t, null, reason));
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Bukkit.getLogger().severe("Malformed configuration while trying to load punishment.");
        }
    }


    public void affect(CommandSender punisher, UUID punished, int punishNo) {
        if (Bukkit.getPlayer(punished) == null)
            return;

        String cmd = this.getType(punishNo).getLogic().replaceAll("%%player%%", Bukkit.getPlayer(punished).getName()).replaceAll("%%reason%%", this.getName()).replaceAll("%%duration%%", this.getEffect(punishNo).getDuration().toString());

        if (punisher instanceof Player) {
            ((Player) punisher).performCommand(cmd);
        } else if (punisher instanceof ConsoleCommandSender) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
        }
    }

    private int nextEffect() {
        int i = 0;

        if (!effects.isEmpty()) {
            for (Integer ix : effects.keySet()) {
                if (ix > i)
                    i = ix;
            }
            i++;
        }

        return i;
    }

    public PunishType getType (int effectno) {
        if (effectno < effects.size()) {
            return this.effects.get(effectno).getType();
        } else {
            return null;
        }
    }

    public PunishEffect getEffect (int effectno) {
        if (effectno < effects.size()) {
            return this.effects.get(effectno);
        } else {
            return null;
        }
    }

    public Map<String, String> toMap(int effectNo, CommandSender punisher, Player punished) {
        Map<String, String> map = new HashMap<>();

        map.put("id", UUID.randomUUID().toString());
        map.put("type", this.getType(effectNo).toString());
        map.put("punished", punished.toString());

        String punisherd = punisher instanceof Player ? ((Player) punisher).getUniqueId().toString() : punisher.getName();

        map.put("punisher", punisherd);
        map.put("reason", this.getName());
        map.put("created_at", TASP.formatDate(new Timestamp(System.currentTimeMillis())));
        map.put("end_date", TASP.formatDate(new Timestamp(System.currentTimeMillis() + 60000 * this.getEffect(effectNo).getDuration())));

        return map;
    }

    public class PunishEffect {

        @Getter
        private final String reason;

        @Getter
        private final Integer duration;

        @Getter
        private final PunishType type;

        protected PunishEffect(PunishType type, Integer duration, String reason) {
            this.reason = reason;
            this.duration = duration;
            this.type = type;
        }

    }

}
