package tech.spencercolton.tasp.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.spencercolton.tasp.Entity.Person;
import tech.spencercolton.tasp.Util.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Spencer Colton
 */
public class MailCmd extends TASPCommand {

    public static final String syntax = "/mail <option> [options]";
    public static final String name = "mail";
    public static final String permission = "tasp.mail";
    public static final String consoleSyntax = null;

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length < 1) {
            Command.sendSyntaxError(sender, this);
            return;
        }

        switch(args[0]) {
            case "send":
                if(args.length < 3) {
                    Command.sendSyntaxError(sender, this);
                    return;
                }

                if(Person.personExists(args[1]) != null) {
                    String msg = "";
                    for(int i = 2; i < args.length; i++) {
                        msg += args[i];
                        if(!((i + 1) >= args.length))
                            msg += " ";
                    }
                    msg = ColorChat.color(msg);
                    msg = ChatFilter.filter(msg);
                    Mail.send(Person.get((Player)sender), Person.personExists(args[1]), msg);
                    sendSentMessage(sender, args[1]);
                    return;
                } else {
                    Command.sendPlayerMessage(sender, args[1]);
                    return;
                }
            case "check":
                List<Map<String,String>> mailz = Mail.fetch(Person.get((Player)sender));
                int multi = 0;
                if(args.length == 2) {
                    multi = Integer.parseInt(args[1]) - 1;
                }
                sender.sendMessage(Config.c1() + " * Mail for " + ((Player)sender).getDisplayName() + Config.c1() + " * ");
                if(mailz.isEmpty()) {
                    sender.sendMessage(Config.c3() + " * You have no mail.");
                    return;
                }
                for(int i = 0; i < Math.min(5, mailz.size() - multi * 10); i++) {
                    Map<String, String> z = mailz.get(i + multi * 10);
                    String from = Person.getMostRecentName(UUID.fromString(z.get("from")));
                    String add = z.get("message").length() > 30 ? "..." : "";
                    String truncMessage = z.get("message").substring(0, Math.min(30, z.get("message").length())) + add;
                    String date = z.get("sent");
                    boolean b = Boolean.parseBoolean(z.get("read"));
                    try {
                        DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                        Date a = df.parse(date);
                        Calendar c = Calendar.getInstance();
                        c.setTime(a);
                        String day = Integer.toString(c.get(Calendar.DATE));
                        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                        String month = months[c.get(Calendar.MONTH)];
                        String hour = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
                        String minute = c.get(Calendar.MINUTE) < 10 ? "0" + Integer.toString(c.get(Calendar.MINUTE)) : Integer.toString(c.get(Calendar.MINUTE));
                        sender.sendMessage(Config.c2() + " * " + Config.c3() + month + " " + day + " " + hour + ":" + minute + " " + Config.c1() + from + " " + Config.c4() + truncMessage);
                    } catch(ParseException ignored) {

                    }

                }
            default:
                Command.sendSyntaxError(sender, this);
        }
    }

    private void sendSentMessage(CommandSender sender, String other) {
        if(Command.messageEnabled(this, false))
            sender.sendMessage(M.m("command-message-text.mail", (Bukkit.getPlayer(other) == null ? other : Bukkit.getPlayer(other).getDisplayName())));
        if(Command.messageEnabled(this, false) && Bukkit.getPlayer(other) != null)
            Bukkit.getPlayer(other).sendMessage(M.m("command-message-text.mail-r", Command.getDisplayName(sender)));
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
        if(args.length >= 1) {
            switch(args[0]) {
                case "send":
                    return permission + ".send";
                case "check":
                    return permission + ".read";
                case "read":
                    return permission + ".read";
                case "delete":
                    return permission + ".delete";
                default:
                    return permission;
            }
        } else {
            return permission;
        }
    }

}
