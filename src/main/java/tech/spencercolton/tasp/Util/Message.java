package tech.spencercolton.tasp.Util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Commands.Command;
import tech.spencercolton.tasp.Entity.Person;

/**
 * @author Spencer Colton
 */
public class Message {

    public static class AFK {
        public static void broadcastAFKMessage(Person p) {
            if(!Config.getBoolean("broadcast-afk"))
                return;

            if(p.isAfk())
                Bukkit.broadcastMessage(Config.c1() + " * " + Config.c2() + p.getPlayer().getDisplayName() + Config.c1() + " is AFK.");
            else
                Bukkit.broadcastMessage(Config.c1() + " * " + Config.c2() + p.getPlayer().getDisplayName() + Config.c1() + " has returned from being AFK.");
        }
    }

    public static class Antidote {
        public static void sendAntidoteMessage(CommandSender sender) {
            if(Command.messageEnabled("antidote"))
                sender.sendMessage(M.cm("antidote"));
        }

        public static void sendAntidoteMessage(CommandSender sender, Player p) {
            if(sender.equals(p)) {
                sendAntidoteMessage(sender);
                return;
            }

            if(Command.messageEnabled("antidote"))
                sender.sendMessage(M.cm("antidote-s", p.getDisplayName()));
            if(Command.messageEnabled("antidote-others"))
                p.sendMessage(M.cm("antidote-r", Command.getDisplayName(sender)));
        }
    }

    public static class Back {
        public static class Error {
            public static void sendNoBackMessage(CommandSender sender) {
                sender.sendMessage(Config.err() + "No location to teleport back to!");
            }
        }
    }

    public static class Block {
        public static void sendBlockedMessage(CommandSender sender, Person p) {
            if (Command.messageEnabled("block"))
                sender.sendMessage(M.cm("block", p.getPlayer().getDisplayName()));
            if (Command.messageEnabled("block-r"))
                p.getPlayer().sendMessage(M.cm("block-r", sender.getName()));
        }

        public static class Error {
            public static void sendAlreadyBlockedMessage(CommandSender sender, Person p) {
                sender.sendMessage(Config.err() + p.getPlayer().getDisplayName() + " is already blocked.");
            }
        }
    }
    
    public static class TeleportListener {
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

        public static class Error {
            public static void sendTeleportCooldownMessage(Player p, long time) {
                p.sendMessage(Config.err() + "You can't teleport again for another " + (int)Math.ceil(time / 1000.0D) + " seconds.");
            }

            public static void sendTeleportCooldownMessage(Player p, long time, Player other) {
                other.sendMessage(Config.err() + p.getDisplayName() + " cannot teleport again for another " + (int)Math.ceil(time / 1000.0D) + " seconds.  Try again later.");
            }

            public static void sendTeleportDisabledMessage(CommandSender s) {
                s.sendMessage(Config.err() + "Teleporting has been disabled for this server.");
            }
        }
    }
    
    public static class Buddha {
        public static void sendBuddhaMessage(CommandSender sender, boolean enabled) {
            sender.sendMessage(M.cm("buddha", (enabled ? "enabled" : "disabled")));
        }

        public static void sendBuddhaMessage(CommandSender sender, boolean enabled, Person p) {
            if(sender.equals(p)) {
                sendBuddhaMessage(sender, enabled);
                return;
            }

            if(Command.messageEnabled("buddha"))
                sender.sendMessage(M.cm("buddha-s", (enabled ? "enabled" : "disabled"), p.getPlayer().getDisplayName()));
            if(Command.messageEnabled("buddha-others"))
                sender.sendMessage(M.cm("buddha-r", (enabled ? "enabled" : "disabled"), Command.getDisplayName(sender)));
        }
    }
    
    public static class Burn {
        public static void sendFireMessage(CommandSender sender, int seconds) {
            if(Command.messageEnabled("burn"))
                sender.sendMessage(M.cm("burn", Integer.toString(seconds)));
        }

        public static void sendFireMessage(CommandSender sender, int seconds, Player other) {
            if(sender.equals(other)) {
                sendFireMessage(sender, seconds);
                return;
            }
            if(Command.messageEnabled("burn"))
                sender.sendMessage(M.cm("burn-s", Integer.toString(seconds), other.getDisplayName()));
            if(Command.messageEnabled("burn-others"))
                other.sendMessage(M.cm("burn-r", Integer.toString(seconds), Command.getDisplayName(sender)));
        }
    }

    public static class Drops {
        public static void sendDropsMessage(CommandSender sender, int drops) {
            if(Command.messageEnabled("drops"))
                sender.sendMessage(M.m("command-message-text.drops", Integer.toString(drops)));
        }
    }
    
    public static class Feed {
        public static void sendFedMessage(CommandSender sender, float amount) {
            if(Command.messageEnabled("feed"))
                sender.sendMessage(M.cm("feed", Float.toString(amount)));
        }

        public static void sendFedMessage(CommandSender sender, float amount, Player other) {
            if(sender.equals(other)) {
                sendFedMessage(sender, amount);
                return;
            }

            if(Command.messageEnabled("feed"))
                sender.sendMessage(M.cm("feed-s", Float.toString(amount), other.getDisplayName()));
            if(Command.messageEnabled("feed-others"))
                other.sendMessage(M.cm("feed-r", Float.toString(amount), Command.getDisplayName(sender)));
        }

        public static class Error {
            public static void sendNegativeMessage(CommandSender sender) {
                sender.sendMessage(Config.err() + "Amount must be positive.");
            }
        }
    }

    public static class Fly {
        public static void sendFlyingMessage(CommandSender sender, boolean flying) {
            if(Command.messageEnabled("fly"))
                sender.sendMessage(M.cm("command-message-text.fly", flying ? "enabled" : "disabled"));
        }

        public static void sendFlyingMessage(CommandSender sender, boolean flying, String n) {
            Player p = Bukkit.getPlayer(n);
            assert p != null;

            if(sender.equals(p)) {
                sendFlyingMessage(sender, flying);
                return;
            }

            if(Command.messageEnabled("fly"))
                sender.sendMessage(M.cm("fly-s", flying ? "enabled" : "disabled", n));
            if(Command.messageEnabled("fly-others"))
                p.sendMessage(M.cm("fly-r", flying ? "enabled" : "disabled", sender.getName()));
        }
    }
    
    public static class FOM {
        public static void sendFOMMessage(CommandSender sender, boolean enabled) {
            if(Command.messageEnabled("fom"))
                sender.sendMessage(M.cm("fom", (enabled ? "enabled" : "disabled")));
        }

        public static void sendFOMMessage(CommandSender sender, boolean enabled, Person p) {
            if(p.getPlayer().equals(sender)) {
                sendFOMMessage(sender, enabled);
                return;
            }

            if(Command.messageEnabled("fom"))
                sender.sendMessage(M.cm("fom-s", (enabled ? "enabled" : "disabled"), p.getPlayer().getDisplayName()));
            if(Command.messageEnabled("fom-others"))
                p.getPlayer().sendMessage(M.cm("fom-r", (enabled ? "enabled" : "disabled"), Command.getDisplayName(sender)));
        }
    }
    
    public static class Gamemode {
        public static void sendGamemodeMessage(CommandSender s, String p) {
            if(Command.messageEnabled("gamemode"))
                s.sendMessage(M.cm("gamemode", p));
        }

        public static void sendGamemodeMessage(CommandSender s, String p, Player x) {
            if(s.equals(x)) {
                sendGamemodeMessage(s, p);
                return;
            }

            if(Command.messageEnabled("gamemode"))
                s.sendMessage(M.cm("gamemode-s", p, x.getDisplayName()));
            if(Command.messageEnabled("gamemode-others"))
                x.sendMessage(M.cm("gamemode-r", p, s.getName()));
        }

        public static class Error {
            public static void sendGamemodeNotFoundMessage(CommandSender s, String g) {
                s.sendMessage(Config.err() + "Couldn't find gamemode with name \"" + g + "\"");
            }
        }
    }

}
