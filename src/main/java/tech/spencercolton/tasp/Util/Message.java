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
            private void sendWorldMessage(CommandSender s) {
                s.sendMessage(Config.err() + "You could not be teleported to your home because it is not in this world.");
            }

            private void sendNoHomeMessage(CommandSender s) {
                s.sendMessage(Config.err() + "You could not be sent home because you have not set your home.  Use /sethome first.");
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

            public static void sendTeleportCooldownMessage(Player p, long time, Player o) {
                p.sendMessage(Config.err() + o.getDisplayName() + " cannot teleport again for another " + (int)Math.ceil(time / 1000.0D) + " seconds.  Try again later.");
            }

            public static void sendTeleportDisabledMessage(CommandSender s) {
                s.sendMessage(Config.err() + "Teleporting has been disabled for this server.");
            }
        }
    }

}
