package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Util.Config;
import tech.spencercolton.tasp.Util.M;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeCmd extends TASPCommand {

    public static final String name = "time";
    public static final String permission = "tasp.time";
    public static final String syntax = "/time [time | world] [time]";
    public static final String consoleSyntax = syntax;

    @Override
    public void execute(CommandSender sender, String[] args) {
        switch(args.length) {
            case 0:
                World w = (sender instanceof ConsoleCommandSender) ? Bukkit.getWorlds().get(0) : ((Player)sender).getWorld();
                long g = w.getTime();
                String t = niceFormatTime(g);
                sendTimeMessage(sender, t, Long.toString(g), w.getName());
                return;
            case 1:
                World w2 = Bukkit.getWorld(args[0]);

                if(w2 != null) {
                    long g2 = w2.getTime();
                    String t2 = niceFormatTime(g2);
                    sendTimeMessage(sender, t2, Long.toString(g2), w2.getName());
                    return;
                } else {
                    w2 = sender instanceof ConsoleCommandSender ? Bukkit.getWorlds().get(0) : ((Player)sender).getWorld();

                    try {
                        int a = Integer.parseInt(args[0]);
                        if( a < 0 || a >= 24000) {
                            Command.sendSyntaxError(sender, this);
                            return;
                        }
                        w2.setTime(a);
                        sendTimeSetMessage(sender, niceFormatTime(a), Long.toString(a), w2.getName());
                    } catch (NumberFormatException e) {
                        Long nt = timeToBukkitTime(args[0]);

                        if(nt == null) {
                            Command.sendSyntaxError(sender, this);
                            return;
                        }

                        w2.setTime(nt);

                        sendTimeSetMessage(sender, prettyPlayerDate(getCalFromString(args[0])), Long.toString(nt), w2.getName());
                        return;
                    }
                }
                return;
            case 2:
                World w3 = Bukkit.getWorld(args[0]);
                
                if(w3 != null) {
                    try {
                        int a = Integer.parseInt(args[1]);
                        if( a < 0 || a >= 24000) {
                            Command.sendSyntaxError(sender, this);
                            return;
                        }
                        w3.setTime(a);
                        sendTimeSetMessage(sender, niceFormatTime(a), Long.toString(a), w3.getName());
                    } catch (NumberFormatException e) {
                        Long nt = timeToBukkitTime(args[1]);

                        if(nt == null) {
                            Command.sendSyntaxError(sender, this);
                            return;
                        }

                        w3.setTime(nt);

                        sendTimeSetMessage(sender, prettyPlayerDate(getCalFromString(args[1])), Long.toString(nt), w3.getName());
                    }
                } else {
                    Command.sendWorldMessage(sender, args[0]);
                }
        }
    }

    @Override
    public String getName() {
        return name;
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
    public String getPermission() {
        return permission;
    }

    @Override
    public boolean predictOthers(CommandSender sender, String[] args) {
        return false;
    }

    private void sendTimeMessage(CommandSender sender, String time, String bukkitTime, String name) {
        sender.sendMessage(M.m("command-message-text.time", time, bukkitTime, name));
    }

    private void sendTimeSetMessage(CommandSender sender, String time, String bukkitTime, String name) {
        if(Command.messageEnabled("time-set"))
            sender.sendMessage(M.m("command-message-text.time-set", time, bukkitTime, name));
    }

    private Long timeToBukkitTime(String s) {
        if(s.equals("noon"))
            return (long)6000;
        if(s.equals("night"))
            return (long)12000;
        if(s.equals("midnight"))
            return (long)18000;
        if(s.equals("day"))
            return (long)0;

        SimpleDateFormat f2 = new SimpleDateFormat("hh:mma");
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        try {
            Date d = f2.parse(s);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            int xz = c.get(Calendar.MINUTE);
            int yz = xz * 50 / 3;
            int gz = c.get(Calendar.HOUR_OF_DAY);
            int hz = (gz >= 6) ? (gz-6)*1000 : 18000 + 1000*gz;
            return (long)(yz + hz);
        } catch(ParseException e) {
            try {
                Date d = f.parse(s);
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                int xz = c.get(Calendar.MINUTE);
                int yz = xz * 50 / 3;
                int gz = c.get(Calendar.HOUR_OF_DAY);
                int hz = (gz >= 6) ? (gz-6)*1000 : 18000 + 1000*gz;
                return (long)(yz + hz);
            } catch (ParseException e2) {
                return null;
            }
        }
    }

    private Calendar getCalFromString(String s) {
        if(s.equals("noon")) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.AM_PM, Calendar.PM);
            return c;
        }
        if(s.equals("midnight")) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.AM_PM, Calendar.AM);
            return c;
        }
        if(s.equals("day")) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.HOUR, 6);
            c.set(Calendar.AM_PM, Calendar.AM);
            return c;
        }
        if(s.equals("night")) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.HOUR, 6);
            c.set(Calendar.AM_PM, Calendar.PM);
            return c;
        }
        SimpleDateFormat f2 = new SimpleDateFormat("hh:mma");
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(f2.parse(s));
            return c;
        } catch(ParseException e) {
            try {
                Calendar c = Calendar.getInstance();
                c.setTime(f.parse(s));
                return c;
            } catch (ParseException e2) {
                return null;
            }
        }
    }

    private String niceFormatTime(long time) {
        long x2 = (time/1000);
        x2 += 6;
        if(x2 >= 24) {
            x2 -= 24;
        }
        long m2 = time % 1000;
        long min2 = (long)(((float)m2)/1000 * 60);
        String ampm2 = "AM";
        if(x2 > 12) {
            x2 %= 12;
            ampm2 = "PM";
        }
        if(x2 == 0)
            x2 = 12;
        String finH2 = Long.toString(x2);
        String finMin2 = min2 < 10 ? "0" + Long.toString(min2) : Long.toString(min2);
        return finH2 + ":" + finMin2 + " " + ampm2;
    }

    private String prettyPlayerDate(Calendar c) {
        String ampm = c.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
        int min = c.get(Calendar.MINUTE);
        String minFin = min < 10 ? "0" + Integer.toString(min) : Integer.toString(min);
        int hour = c.get(Calendar.HOUR);
        String hourFin = hour == 0 ? "12" : Integer.toString(hour);
        return hourFin + ":" + minFin + " " + ampm;
    }

}