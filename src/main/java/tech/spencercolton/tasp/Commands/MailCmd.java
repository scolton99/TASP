package tech.spencercolton.tasp.Commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

    @Getter
    public static final String syntax = "/mail <option> [options]";

    public static final String name = "mail";

    @Getter
    public static final String permission = "tasp.mail";

    @Getter
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
                    Message.Mail.sendSentMessage(sender, args[1]);
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
                for(int i = 0; i < Math.min(5, mailz.size() - multi * 5); i++) {
                    Map<String, String> z = mailz.get(i + multi * 5);
                    String from = Person.getMostRecentName(UUID.fromString(z.get("from"))) == null ? z.get("currname") : Person.getMostRecentName(UUID.fromString(z.get("from")));
                    String add = z.get("message").length() > 20 ? "..." : "";
                    String truncMessage = z.get("message").substring(0, Math.min(20, z.get("message").length())) + add;
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
                        sender.sendMessage(Config.c2() + " " + Integer.toString(1 + i + multi * 5)+ " " + Config.c3() + month + " " + day + " " + hour + ":" + minute + " " + Config.c1() + from + " " + Config.c4() + (b ? "" : ChatColor.BOLD) + truncMessage);
                    } catch(ParseException ignored) {

                    }
                }
                return;
            case "read":
                if(args.length != 2) {
                    Command.sendSyntaxError(sender, this);
                    return;
                }

                int g;
                try {
                    g = Integer.parseInt(args[1]);
                } catch(NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                    return;
                }
                g--;
                List<Map<String,String>> mails = Mail.fetch(Person.get((Player)sender));
                if(g < 0 || g >= mails.size()) {
                    Message.Mail.Error.sendMailNotFoundMessage(sender);
                    return;
                }
                Map<String, String> mel = mails.get(g);
                try {
                    DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                    Date a = df.parse(mel.get("sent"));
                    Calendar c = Calendar.getInstance();
                    c.setTime(a);
                    String day = Integer.toString(c.get(Calendar.DATE));
                    String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                    String month = months[c.get(Calendar.MONTH)];
                    String hour = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
                    String minute = c.get(Calendar.MINUTE) < 10 ? "0" + Integer.toString(c.get(Calendar.MINUTE)) : Integer.toString(c.get(Calendar.MINUTE));
                    String year = Integer.toString(c.get(Calendar.YEAR));
                    String mostRecentName = Person.getMostRecentName(UUID.fromString(mel.get("from"))) == null ? mel.get("currname") : Person.getMostRecentName(UUID.fromString(mel.get("from")));
                    sender.sendMessage(Config.c1() + " * ----------------------------- * ");
                    sender.sendMessage(Config.c1() + " * Number: " + Config.c2() + Integer.toString(g + 1));
                    sender.sendMessage(Config.c1() + " * From: " + Config.c2() + mostRecentName);
                    sender.sendMessage(Config.c1() + " * Sent: " + Config.c2() + day + " " + month + " " + year + " " + hour + ":" + minute);
                    sender.sendMessage(Config.c1() + " * Message: " + Config.c3() + mel.get("message"));
                    sender.sendMessage(Config.c1() + " * ----------------------------- * ");
                    Mail.setRead(mel);
                }catch(ParseException e) {
                    sender.sendMessage(Config.err() + "There was a problem fetching your mail.  Try again later.");
                    return;
                }
                return;
            case "delete":
                if(args.length != 2) {
                    Command.sendSyntaxError(sender, this);
                    return;
                }

                int g2;
                try {
                    g2 = Integer.parseInt(args[1]);
                } catch(NumberFormatException e) {
                    Command.sendSyntaxError(sender, this);
                    return;
                }
                g2--;
                List<Map<String,String>> mails2 = Mail.fetch(Person.get((Player)sender));
                if(g2 < 0 || g2 >= mails2.size()) {
                    Message.Mail.Error.sendMailNotFoundMessage(sender);
                    return;
                }
                Map<String,String> m1 = mails2.get(g2);
                Mail.delete(m1);
                Message.Mail.sendDeletedMessage(sender);
                break;
            default:
                Command.sendSyntaxError(sender, this);
        }
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
