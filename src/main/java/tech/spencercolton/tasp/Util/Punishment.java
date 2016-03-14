package tech.spencercolton.tasp.Util;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Enums.PunishType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Spencer Colton
 */
public class Punishment {

    List<String> effects = new ArrayList<>();

    @Getter
    private final String name;

    Punishment() {
        this.name = null;
    }

    Punishment(String name) {
        this.name = name;
    }

    public void addEffect(PunishType type, int duration, String reason) {
        switch (type) {
            case KICK: {
                this.effects.add("kick %%player%% " + reason);
                break;
            }
            case BAN: {
                this.effects.add("ban %%player%% " + reason);
                break;
            }
            case TEMPBAN: {
                this.effects.add("tempban %%player%% " + duration + " " + reason);
                break;
            }
            case MUTE: {
                this.effects.add("gmute %%player%% " + duration);
                break;
            }
        }
    }

    public void addEffectFromConfig(String s) {
        try {
            String[] n = s.split(" ");

            switch (n[0].toLowerCase()) {
                case "mute": {
                    this.effects.add("gmute %%player%% " + n[1]);
                    break;
                }
                case "ban": {
                    this.effects.add("ban %%player%% " + this.name);
                    break;
                }
                case "tempban": {
                    this.effects.add("tempban %%player%% " + n[1] + " " + this.name);
                    break;
                }
                case "kick": {
                    this.effects.add("kick %%player%% " + this.name);
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Bukkit.getLogger().severe("Malformed configuration while trying to load punishment " + this.name);
        }
    }

    public void affect(CommandSender punisher, Player punished) {
        // TODO: Add logic to check for past infractions before running
        if (punisher instanceof Player) {
            ((Player) punisher).performCommand(this.effects.get(0).replace("%%player%%", punished.getName()));
        } else if (punisher instanceof ConsoleCommandSender) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.effects.get(0).replace("%%player%%", punished.getName()));
        }
    }

    public void affect(CommandSender punisher, Person punished) {
        affect(punisher, punished.getPlayer());
    }

}
