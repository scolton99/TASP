package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import tech.spencercolton.tasp.Enums.Potions;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

import java.util.List;

/**
 * @author Spencer Colton
 */
public class PotionCmd extends TASPCommand {

    public static final String syntax = "/potion <potion> [player] [strength] [duration]";
    public static final String name = "potion";
    public static final String permission = "tasp.potion";
    public static final String consoleSyntax = "/potion <potion> <player> [strength] [duration]";

    @Override
    public void execute(CommandSender sender, String... args) {
        List<String> realArgs = Command.processQuotedArguments(args);

        switch(realArgs.size()) {
            case 1:
                if(sender instanceof ConsoleCommandSender) {
                    Command.sendConsoleSyntaxError(sender, this);
                    return;
                }
                Player pa = (Player)sender;
                Potions po = Potions.getByName(realArgs.get(0));
                if(po == null) {
                    sendPotionNotRecognizedMessage(sender, realArgs.get(0));
                    return;
                }
                PotionEffect pe2 = new PotionEffect(po.getSpigotPotion(), po.getDefaultDuration(), Potions.DEFAULT_STRENGTH);
                pa.addPotionEffect(pe2, true);
                sendPotionMessage(sender, pe2);
                return;
            case 2:
                Potions p = Potions.getByName(realArgs.get(0));
                if(p == null) {
                    sendPotionNotRecognizedMessage(sender, realArgs.get(0));
                    return;
                }
                Player p1 = Bukkit.getPlayer(realArgs.get(1));
                if(p1 == null) {
                    Command.sendPlayerMessage(sender, realArgs.get(1));
                    return;
                }
                PotionEffect pe = new PotionEffect(p.getSpigotPotion(), p.getDefaultDuration(), Potions.DEFAULT_STRENGTH);
                p1.addPotionEffect(pe, true);
                sendPotionMessage(sender, pe, p1);
                return;
            case 3:
                Potions p3 = Potions.getByName(realArgs.get(0));
                if(p3 == null) {
                    sendPotionNotRecognizedMessage(sender, realArgs.get(0));
                    return;
                }
                Player pa1 = Bukkit.getPlayer(realArgs.get(1));
                if(pa1 == null) {
                    Command.sendPlayerMessage(sender, realArgs.get(1));
                    return;
                }
                int strength;
                try {
                    strength = Integer.parseInt(realArgs.get(2));
                } catch (NumberFormatException e) {
                    if(sender instanceof ConsoleCommandSender)
                        Command.sendConsoleSyntaxError(sender, this);
                    else
                        Command.sendSyntaxError(sender, this);
                    return;
                }
                if(strength < 1 || strength > 10) {
                    if(sender instanceof ConsoleCommandSender)
                        Command.sendConsoleSyntaxError(sender, this);
                    else
                        Command.sendSyntaxError(sender, this);
                    return;
                }
                PotionEffect pe1 = new PotionEffect(p3.getSpigotPotion(), p3.getDefaultDuration(), strength - 1);
                pa1.addPotionEffect(pe1, true);
                sendPotionMessage(sender, pe1, pa1);
                return;
            case 4:
                Potions p43 = Potions.getByName(realArgs.get(0));
                if(p43 == null) {
                    sendPotionNotRecognizedMessage(sender, realArgs.get(0));
                    return;
                }
                Player pa14 = Bukkit.getPlayer(realArgs.get(1));
                if(pa14 == null) {
                    Command.sendPlayerMessage(sender, realArgs.get(1));
                    return;
                }
                int strength4, dur4;
                try {
                    strength4 = Integer.parseInt(realArgs.get(2));
                    dur4 = Integer.parseInt(realArgs.get(3));
                } catch (NumberFormatException e) {
                    if(sender instanceof ConsoleCommandSender)
                        Command.sendConsoleSyntaxError(sender, this);
                    else
                        Command.sendSyntaxError(sender, this);
                    return;
                }
                if(strength4 < 1 || strength4 > 10) {
                    if(sender instanceof ConsoleCommandSender)
                        Command.sendConsoleSyntaxError(sender, this);
                    else
                        Command.sendSyntaxError(sender, this);
                    return;
                }
                if(dur4 < 1) {
                    if(sender instanceof ConsoleCommandSender)
                        Command.sendConsoleSyntaxError(sender, this);
                    else
                        Command.sendSyntaxError(sender, this);
                    return;
                }
                if(dur4 > 3600)
                    dur4 = 1_000_000;
                PotionEffect pe14 = new PotionEffect(p43.getSpigotPotion(), dur4 * Potions.TICKS_IN_SECOND, strength4 - 1);
                pa14.addPotionEffect(pe14, true);
                sendPotionMessage(sender, pe14, pa14);
                return;
            default:
                if(sender instanceof ConsoleCommandSender)
                    Command.sendConsoleSyntaxError(sender, this);
                else
                    Command.sendSyntaxError(sender, this);
        }
    }

    private void sendPotionMessage(CommandSender sender, PotionEffect pe) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.potion", pe.getType().getName(), Integer.toString(pe.getAmplifier() + 1), Integer.toString(pe.getDuration() / Potions.TICKS_IN_SECOND)));
    }

    private void sendPotionMessage(CommandSender sender, PotionEffect pe, Player p) {
        if(sender.equals(p)) {
            sendPotionMessage(sender, pe);
            return;
        }

        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.potion-s", pe.getType().getName(), Integer.toString(pe.getAmplifier() + 1), Integer.toString(pe.getDuration() / Potions.TICKS_IN_SECOND), p.getDisplayName()));
        if(Command.messageEnabled(this, true))
            p.sendMessage(M.m("command-message-text.potion-r", pe.getType().getName(), Integer.toString(pe.getAmplifier() + 1), Integer.toString(pe.getDuration() / Potions.TICKS_IN_SECOND), Command.getDisplayName(sender)));
    }

    private void sendPotionNotRecognizedMessage(CommandSender sender, String potion) {
        sender.sendMessage(Config.err() + "Potion " + potion + " not recognized.");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getConsoleSyntax() {
        return consoleSyntax;
    }

    @Override
    public String predictRequiredPermission(CommandSender sender, String... args) {
        String ret = permission;

        List<String> realArgs = Command.processQuotedArguments();

        if(realArgs.size() >= 1) {
            Potions p = Potions.getByName(realArgs.get(0));
            if(p != null) {
                ret += p.getSpigotPotion().getName().toLowerCase();
            } else {
                return permission;
            }
        }

        if(realArgs.size() == 4 && !sender.equals(Bukkit.getPlayer(realArgs.get(3))))
            ret += ".others";

        return ret;
    }

}
