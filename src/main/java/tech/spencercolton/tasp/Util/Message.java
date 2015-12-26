package tech.spencercolton.tasp.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import tech.spencercolton.tasp.Commands.BroadcastCmd;
import tech.spencercolton.tasp.Commands.Command;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Enums.Potions;
import tech.spencercolton.tasp.Enums.WeatherType;

import java.text.DecimalFormat;

/**
 * @author Spencer Colton
 */
public class Message {

    public static class AFK {
        public static void broadcastAFKMessage(Player p) {
            if(!Config.getBoolean("broadcast-afk"))
                return;

            if(Person.get(p).isAfk())
                Bukkit.broadcastMessage(Config.c1() + " * " + Config.c2() + p.getDisplayName() + Config.c1() + " is AFK.");
            else
                Bukkit.broadcastMessage(Config.c1() + " * " + Config.c2() + p.getDisplayName() + Config.c1() + " has returned from being AFK.");
        }
    }

    public static class Antidote {
        public static void sendAntidoteMessage(CommandSender s, Player p) {
            if(s.equals(p) && Command.messageEnabled("antidote")) {
                s.sendMessage(M.cm("antidote"));
                return;
            }

            if(Command.messageEnabled("antidote"))
                s.sendMessage(M.cm("antidote-s", p.getDisplayName()));
            if(Command.messageEnabled("antidote-others"))
                p.sendMessage(M.cm("antidote-r", Command.getDisplayName(s)));
        }
    }

    public static class Back {
        public static class Error {
            public static void sendNoBackMessage(CommandSender s) {
                s.sendMessage(Config.err() + "No location to teleport back to!");
            }
        }
    }

    public static class Block {
        public static void sendBlockedMessage(CommandSender s, Player p) {
            if (Command.messageEnabled("block"))
                s.sendMessage(M.cm("block", p.getDisplayName()));
            if (Command.messageEnabled("block-r"))
                p.sendMessage(M.cm("block-r", Command.getDisplayName(s)));
        }

        public static class Error {
            public static void sendAlreadyBlockedMessage(CommandSender s, Player p) {
                s.sendMessage(Config.err() + p.getDisplayName() + " is already blocked.");
            }

            public static void sendSelfMessage(CommandSender s) {
                s.sendMessage(Config.err() + "You can't block yourself!");
            }
        }
    }
    
    public static class Buddha {
        public static void sendBuddhaMessage(CommandSender s, boolean enabled, Player p) {
            if(s.equals(p) && Command.messageEnabled("buddha")) {
                s.sendMessage(M.cm("buddha", (enabled ? "enabled" : "disabled")));
                return;
            }

            if(Command.messageEnabled("buddha"))
                s.sendMessage(M.cm("buddha-s", (enabled ? "enabled" : "disabled"), p.getDisplayName()));
            if(Command.messageEnabled("buddha-others"))
                s.sendMessage(M.cm("buddha-r", (enabled ? "enabled" : "disabled"), Command.getDisplayName(s)));
        }
    }
    
    public static class Burn {
        public static void sendFireMessage(CommandSender s, int seconds, Player p) {
            if(s.equals(p) && Command.messageEnabled("burn")) {
                s.sendMessage(M.cm("burn", Integer.toString(seconds)));
                return;
            }
            if(Command.messageEnabled("burn"))
                s.sendMessage(M.cm("burn-s", Integer.toString(seconds), p.getDisplayName()));
            if(Command.messageEnabled("burn-others"))
                p.sendMessage(M.cm("burn-r", Integer.toString(seconds), Command.getDisplayName(s)));
        }
    }

    public static class Drops {
        public static void sendDropsMessage(CommandSender s, int drops) {
            if(Command.messageEnabled("drops"))
                s.sendMessage(M.m("command-message-text.drops", Integer.toString(drops)));
        }
    }
    
    public static class Feed {
        public static void sendFedMessage(CommandSender s, float amount, Player p) {
            if(s.equals(p) && Command.messageEnabled("feed")) {
                s.sendMessage(M.cm("feed", Float.toString(amount)));
                return;
            }

            if(Command.messageEnabled("feed"))
                s.sendMessage(M.cm("feed-s", Float.toString(amount), p.getDisplayName()));
            if(Command.messageEnabled("feed-others"))
                p.sendMessage(M.cm("feed-r", Float.toString(amount), Command.getDisplayName(s)));
        }
    }

    public static class Fly {
        public static void sendFlyingMessage(CommandSender s, boolean flying, Player p) {
            if(s.equals(p) && Command.messageEnabled("fly")) {
                s.sendMessage(M.cm("command-message-text.fly", flying ? "enabled" : "disabled"));
                return;
            }

            if(Command.messageEnabled("fly"))
                s.sendMessage(M.cm("fly-s", flying ? "enabled" : "disabled", p.getDisplayName()));
            if(Command.messageEnabled("fly-others"))
                p.sendMessage(M.cm("fly-r", flying ? "enabled" : "disabled", Command.getDisplayName(s)));
        }
    }
    
    public static class FOM {
        public static void sendFOMMessage(CommandSender s, boolean enabled, Player p) {
            if(p.equals(s) && Command.messageEnabled("fom")) {
                s.sendMessage(M.cm("fom", (enabled ? "enabled" : "disabled")));
                return;
            }

            if(Command.messageEnabled("fom"))
                s.sendMessage(M.cm("fom-s", (enabled ? "enabled" : "disabled"), p.getDisplayName()));
            if(Command.messageEnabled("fom-others"))
                p.sendMessage(M.cm("fom-r", (enabled ? "enabled" : "disabled"), Command.getDisplayName(s)));
        }
    }
    
    public static class Gamemode {
        public static void sendGamemodeMessage(CommandSender s, String gm, Player p) {
            if(s.equals(p) && Command.messageEnabled("gamemode")) {
                s.sendMessage(M.cm("gamemode", gm));
                return;
            }

            if(Command.messageEnabled("gamemode"))
                s.sendMessage(M.cm("gamemode-s", gm, p.getDisplayName()));
            if(Command.messageEnabled("gamemode-others"))
                p.sendMessage(M.cm("gamemode-r", gm, Command.getDisplayName(s)));
        }

        public static class Error {
            public static void sendGamemodeNotFoundMessage(CommandSender s, String g) {
                s.sendMessage(Config.err() + "Couldn't find gamemode with name \"" + g + "\"");
            }
        }
    }

    public static class God {
        public static void sendGodMessage(CommandSender s, boolean t, Player p) {
            if(s.equals(p) && Command.messageEnabled("god")) {
                s.sendMessage(M.cm("god", t ? "enabled" : "disabled"));
                return;
            }

            if(Command.messageEnabled("god"))
                s.sendMessage(M.cm("god-s", t ? "enabled" : "disabled", p.getDisplayName()));
            if(Command.messageEnabled("god-others"))
                p.sendMessage(M.cm("god-r", t ? "enabled" : "disabled", Command.getDisplayName(s)));
        }
    }

    public static class Heal {
        public static void sendHealedMessage(CommandSender sender, double amount, Player p) {
            if(sender.equals(p) && Command.messageEnabled("heal")) {
                sender.sendMessage(M.cm("heal", Double.toString(amount)));
                return;
            }

            if(Command.messageEnabled("heal"))
                sender.sendMessage(M.cm("heal-s", Double.toString(amount), p.getDisplayName()));
            if(Command.messageEnabled("heal-others"))
                p.sendMessage(M.cm("heal-r", Double.toString(amount), Command.getDisplayName(sender)));
        }
    }

    public static class Home {
        public static class Error {
            public static void sendWorldMessage(CommandSender s) {
                s.sendMessage(Config.err() + "You could not be teleported to your home because it is not in this world.");
            }

            public static void sendNoHomeMessage(CommandSender s) {
                s.sendMessage(Config.err() + "You could not be sent home because you have not set your home.  Use /sethome first.");
            }
        }
    }

    public static class Hurt {
        public static void sendHurtMessage(CommandSender sender, double amount, Player other) {
            if (sender.equals(other) && Command.messageEnabled("hurt")) {
                sender.sendMessage(M.cm("hurt", Double.toString(amount)));
                return;
            }

            if (Command.messageEnabled("hurt"))
                sender.sendMessage(M.cm("hurt-s", Double.toString(amount), other.getDisplayName()));
            if (Command.messageEnabled("hurt-others"))
                other.sendMessage(M.cm("hurt-r", Double.toString(amount), Command.getDisplayName(sender)));
        }
    }

    public static class Killall {
        public static void sendCountMessage(CommandSender sender, int count, String world) {
            if(Command.messageEnabled("killall"))
                sender.sendMessage(M.m("command-message-text.killall", Integer.toString(count), world));
        }
    }

    public static class Mail {
        public static void sendDeletedMessage(CommandSender sender) {
            sender.sendMessage(Config.c3() + "Successfully deleted mail.");
        }

        public static void sendSentMessage(CommandSender sender, String other) {
            if (Command.messageEnabled("mail"))
                sender.sendMessage(M.cm("mail", (Bukkit.getPlayer(other) == null ? other : Bukkit.getPlayer(other).getDisplayName())));
            if (Command.messageEnabled("mail-others") && Bukkit.getPlayer(other) != null)
                Bukkit.getPlayer(other).sendMessage(M.cm("mail-r", Command.getDisplayName(sender)));
        }

        public static class Error {
            public static void sendMailNotFoundMessage(CommandSender sender) {
                sender.sendMessage(Config.err() + "A mail with that ID was not found.");
            }
        }
    }

    public static class MessageCmd {


        public static class Error {
            public static void sendBlockedMessage(CommandSender s, String name) {
                s.sendMessage(Config.err() + name + " has blocked you.");
            }

            public static void sendYouBlockedMessage(CommandSender s, String name) {
                s.sendMessage(Config.err() + "You have blocked " + name);
            }
        }
    }

    public static class Mute {
        public static void sendMutedMessage(CommandSender sender, boolean muted, Player p) {
            if(Command.messageEnabled("muted"))
                sender.sendMessage(M.cm("muted-s", p.getDisplayName(), muted ? "muted" : "unmuted"));
            if(Command.messageEnabled("muted-r"))
                sender.sendMessage(M.cm("muted-r", Command.getDisplayName(sender), muted ? "muted" : "unmuted"));
        }
    }

    public static class Potion {
        public static void sendPotionMessage(CommandSender sender, PotionEffect pe, Player p) {
            if(sender.equals(p) && Command.messageEnabled("potion")) {
                sender.sendMessage(M.m("command-message-text.potion", pe.getType().getName(), Integer.toString(pe.getAmplifier() + 1), Integer.toString(pe.getDuration() / Potions.TICKS_IN_SECOND)));
                return;
            }

            if(Command.messageEnabled("potion"))
                sender.sendMessage(M.cm("potion-s", pe.getType().getName(), Integer.toString(pe.getAmplifier() + 1), Integer.toString(pe.getDuration() / Potions.TICKS_IN_SECOND), p.getDisplayName()));
            if(Command.messageEnabled("potion-others"))
                p.sendMessage(M.cm("potion-r", pe.getType().getName(), Integer.toString(pe.getAmplifier() + 1), Integer.toString(pe.getDuration() / Potions.TICKS_IN_SECOND), Command.getDisplayName(sender)));
        }

        public static class Error {
            public static void sendPotionNotRecognizedMessage(CommandSender sender, String potion) {
                sender.sendMessage(Config.err() + "Potion " + potion + " not recognized.");
            }
        }
    }

    public static class Powertool {
        public static void sendRemovedPowertoolsMessage(CommandSender sender, Material m) {
            if(Command.messageEnabled("powertool-remove"))
                sender.sendMessage(M.cm("powertool-remove", m.toString().toLowerCase().replace("_", " ")));

        }

        public static void sendPowertoolEnabledMessage(CommandSender sender, Material m, String cmdLine) {
            if(Command.messageEnabled("powertool"))
                sender.sendMessage(M.cm("powertool", m.toString().toLowerCase().replace("_", " "), cmdLine));
        }
    }

    public static class PowertoolToggle {
        public static void sendToggledMessage(CommandSender sender, boolean enabled) {
            if(Command.messageEnabled("powertooltoggle"))
                sender.sendMessage(M.cm("powertooltoggle", enabled ? "enabled" : "disabled"));
        }

        public static void broadcastToggledMessage(CommandSender sender, boolean enabled) {
            if(Config.getBoolean("broadcast-powertool-toggle")) {
                String[] x = {"Powertools have been", (enabled ? "enabled" : "disabled") + "."};
                new BroadcastCmd().execute(sender, x);
            }
        }
    }

    public static class Reply {
        public static class Error {
            public static void sendNoLastMessage(CommandSender sender) {
                sender.sendMessage(Config.err() + "You cannot reply because you have not been messaged nor have you messaged anyone.");
            }
        }
    }

    public static class Sethome {
        public static void sendHomeMessage(CommandSender s, Location l, Player p) {
            if(s.equals(p) && Command.messageEnabled("sethome")) {
                s.sendMessage(M.cm("sethome", Integer.toString((int)l.getX()), Integer.toString((int)l.getY()), Integer.toString((int)l.getZ())));
                return;
            }

            if(Command.messageEnabled("sethome"))
                s.sendMessage(M.cm("sethome-s", Integer.toString((int)l.getX()), Integer.toString((int)l.getY()), Integer.toString((int)l.getZ()), p.getDisplayName()));
            if(Command.messageEnabled("sethome-others"))
                p.sendMessage(M.cm("sethome-r", Integer.toString((int)l.getX()), Integer.toString((int)l.getY()), Integer.toString((int)l.getZ()), Command.getDisplayName(s)));
        }

        public static class Error {
            public static void sendYawOOBMessage(CommandSender sender) {
                sender.sendMessage(Config.err() + "Yaw must be between 0 and 360 (inclusive).");
            }

            public static void sendPitchOOBMessage(CommandSender sender) {
                sender.sendMessage(Config.err() + "Pitch must be between -90 and 90 (inclusive).");
            }
        }
    }

    public static class Setspawn {
        public static void sendSpawnSetMessage(CommandSender s, int x, int y, int z, String world) {
            if(Command.messageEnabled("setspawn"))
                s.sendMessage(M.cm("setspawn", Integer.toString(x), Integer.toString(y), Integer.toString(z), world));
        }
    }

    public static class Setspeed {
        public static void sendSpeedMessage(CommandSender s, float speed, Player p) {
            if(s.equals(p) && Command.messageEnabled("setspeed")) {
                p.sendMessage(M.m("command-message-text.setspeed", Float.toString(speed)));
                return;
            }

            if(Command.messageEnabled("setspeed"))
                p.sendMessage(M.cm("setspeed-s", p.getDisplayName(), Float.toString(speed)));
            if(Command.messageEnabled("setspeed-others"))
                s.sendMessage(M.cm("setspeed-r", Float.toString(speed), Command.getDisplayName(s)));
        }

        public static class Error {
            public static void sendSpeedOOBMessage(CommandSender s) {
                s.sendMessage(Config.err() + "Speed must be between 0 and 50 (inclusive).");
            }
        }
    }

    public static class Shock {
        public static void sendShockMessage(CommandSender s, Player p) {
            if(p.equals(s)) {
                s.sendMessage(M.cu("shock"));
                return;
            }
            if(Command.messageEnabled("shock"))
                s.sendMessage(M.cm("shock-s", p.getDisplayName()));
            if(Command.messageEnabled("shock-others"))
                p.sendMessage(M.cm("shock-r", Command.getDisplayName(s)));
        }
    }

    public static class Spawnmob {
        public static void sendSpawnmobMessage(CommandSender sender, EntityType e, int amount) {
            sender.sendMessage(M.m("command-message-text.spawnmob", e.toString().toLowerCase().replace("_", " "), Integer.toString(amount)));
        }
    }

    public static class Stalker {
        public static void sendStalkerMessage(CommandSender sender, boolean enabled, Player p) {
            if(sender.equals(p) && Command.messageEnabled("stalker")) {
                sender.sendMessage(M.cm("stalker", enabled ? "enabled" : "disabled"));
                return;
            }

            if(Command.messageEnabled("stalker"))
                sender.sendMessage(M.cm("stalker-s", enabled ? "enabled" : "disabled", p.getDisplayName()));
            if(Command.messageEnabled("stalker-others"))
                p.sendMessage(M.cm("stalker-r", enabled ? "enabled" : "disabled", Command.getDisplayName(sender)));
        }
    }

    public static class Starve {
        public static void sendStarvedMessage(CommandSender sender, float amount, Player p) {
            if(sender.equals(p) && Command.messageEnabled("starve")) {
                sender.sendMessage(M.cm("starve", Float.toString(amount)));
                return;
            }

            if(Command.messageEnabled("starve"))
                sender.sendMessage(M.m("command-message-text.starve-s", Float.toString(amount), p.getDisplayName()));
            if(Command.messageEnabled("starve-others"))
                p.sendMessage(M.m("command-message-text.starve-r", Float.toString(amount), Command.getDisplayName(sender)));
        }
    }

    public static class Teleport {
        public static void sendTeleportRequestMessage(Player requester, Player requestee) {
            requester.sendMessage(Config.c3() + "Asking " + requestee.getDisplayName() + " if it's okay to teleport you to him or her..." + (Config.isTeleportRequestLimited() ? "expires in " + Config.teleportRequestLimit() / 1000 + " seconds" : ""));
            requestee.sendMessage(Config.c4() + "Player " + requester.getDisplayName() + " would like to teleport to you.  Type /tpa to allow, or /tpd to deny (or just ignore this message.) " + (Config.isTeleportRequestLimited() ? "Expires in "+ Config.teleportRequestLimit() / 1000 + " seconds." : "" ));
        }

        public static void sendTeleportRequestMessage(Player requester, Player requestee, boolean here) {
            if(!here) {
                sendTeleportRequestMessage(requester, requestee);
                return;
            }
            requester.sendMessage(Config.c3() + "Asking " + requestee.getDisplayName() + " if it's okay to teleport him or her to you..." + (Config.isTeleportRequestLimited() ? "expires in " + Config.teleportRequestLimit() / 1000 + " seconds" : ""));
            requestee.sendMessage(Config.c4() + "Player " + requester.getDisplayName() + " would like you to teleport to him or her.  Type /tpa to allow, or /tpd to deny (or just ignore this message.) " + (Config.isTeleportRequestLimited() ? "Expires in "+ Config.teleportRequestLimit() / 1000 + " seconds." : "" ));
        }

        public static void sendTeleportRequestMessage(Player requester, Player requestee, boolean here, boolean all) {
            if(all)
                here = true;
            if(!here) {
                sendTeleportRequestMessage(requester, requestee);
                return;
            }
            if(!all)
                requester.sendMessage(Config.c3() + "Asking " + requestee.getDisplayName() + " if it's okay to teleport him or her to you..." + (Config.isTeleportRequestLimited() ? "expires in " + Config.teleportRequestLimit() / 1000 + " seconds" : ""));
            requestee.sendMessage(Config.c4() + "Player " + requester.getDisplayName() + " would like you to teleport to him or her.  Type /tpa to allow, or /tpd to deny (or just ignore this message.) " + (Config.isTeleportRequestLimited() ? "Expires in "+ Config.teleportRequestLimit() / 1000 + " seconds." : "" ));
        }

        public static void sendTeleportDenyMessage(Player requester, Player requestee) {
            requester.sendMessage(Config.c1() + requestee.getDisplayName() + Config.c1() + " denied your teleport request.");
            requestee.sendMessage(Config.c1() + "You denied " + requester.getDisplayName() + Config.c1() + "'s teleport request.");
        }

        public static void sendToggledMessage(CommandSender s, boolean enabled) {
            s.sendMessage(M.m("command-message-text.teleporttoggle", (enabled ? "enabled" : "disabled")));
            if(Config.getBoolean("broadcast-teleport-toggle")) {
                String[] x = {"Teleportation has been", (enabled ? "enabled" : "disabled") + "."};
                new BroadcastCmd().execute(s, x);
            }
        }

        public static class Error {
            public static void sendTeleportCooldownMessage(Player p, long time) {
                p.sendMessage(Config.err() + "You can't teleport again for another " + (int)Math.ceil(time / 1000.0D) + " seconds.");
            }

            public static void sendTeleportCooldownMessage(Player p, long time, Player o) {
                p.sendMessage(Config.err() + o.getDisplayName() + " cannot teleport again for another " + (int)Math.ceil(time / 1000.0D) + " seconds.  Try again later.");
            }

            public static void sendTeleportDisabledMessage(CommandSender s) {
                s.sendMessage(Config.err() + "Teleporting has been disabled for this server.");
            }

            public static void sendNoTeleportRequestsMessage(CommandSender s) {
                s.sendMessage(Config.err() + "You have no existing teleport requests.");
            }
        }
    }

    public static class Time {
        public static void sendTimeMessage(CommandSender sender, String time, String bukkitTime, String name) {
            sender.sendMessage(M.m("command-message-text.time", time, bukkitTime, name));
        }

        public static void sendTimeSetMessage(CommandSender sender, String time, String bukkitTime, String name) {
            if(Command.messageEnabled("time-set"))
                sender.sendMessage(M.m("command-message-text.time-set", time, bukkitTime, name));
        }

        public static class Error {
            public static void sendInvalidFormatMessage(CommandSender sender) {
                sender.sendMessage(Config.err() + "Time must be in ticks, 12 or 24 hour format, or \"noon\", \"midnight\", \"night\", or \"day\".");
            }
        }
    }

    public static class Unblock {
        public static void sendUnblockedMessage(CommandSender sender, Person p) {
            if(Command.messageEnabled("unblock"))
                sender.sendMessage(M.m("command-message-text.unblock", p.getPlayer().getDisplayName()));
            if(Command.messageEnabled("unblock-r"))
                p.getPlayer().sendMessage(M.m("command-message-text.unblock-r", sender.getName()));
        }

        public static class Error {
            public static void sendNotBlockedMessage(CommandSender sender, Person p) {
                sender.sendMessage(Config.err() + p.getPlayer().getDisplayName() + " is not blocked.");
            }
        }
    }


    public static class Weather {
        public static void sendWeatherMessage(CommandSender sender, WeatherType w, int duration, org.bukkit.World world) {
            if(Command.messageEnabled("weather"))
                sender.sendMessage(M.m("command-message-text.weather-set", w.getName(), Integer.toString(duration / tech.spencercolton.tasp.Util.Time.TICKS_IN_SECOND), world.getName()));
        }

        public static void sendConsoleWeatherReport(CommandSender sender, boolean storming, int duration, org.bukkit.World w) {
            sender.sendMessage(M.m("command-message-text.weather-console", (storming ? "storming" : "clear"), Integer.toString(duration / tech.spencercolton.tasp.Util.Time.TICKS_IN_SECOND), w.getName()));
        }

        public static void sendWeatherReport(CommandSender sender, boolean storming, int duration, double temperature, double humidity, org.bukkit.World w) {
            DecimalFormat d = new DecimalFormat("0.00");
            String temp = d.format(tech.spencercolton.tasp.Util.Weather.calcTemperature(temperature, w));
            sender.sendMessage(M.m("command-message-text.weather", (storming ? "storming" : "clear"), Integer.toString(duration / tech.spencercolton.tasp.Util.Time.TICKS_IN_SECOND), temp, d.format(tech.spencercolton.tasp.Util.Weather.calcHumidity(humidity)), w.getName()));
        }
    }

    public static class World {
        public static void sendWorldMessage(CommandSender sender) {
            if(Command.messageEnabled("world"))
                sender.sendMessage(M.cm("world", ((Player)sender).getWorld().getName()));
        }
    }

    public static class XYZ {
        public static void sendPosMessage(CommandSender sender, int x, int y, int z) {
            sender.sendMessage(M.cm("xyz", Integer.toString(x), Integer.toString(y), Integer.toString(z)));
        }
    }

}
